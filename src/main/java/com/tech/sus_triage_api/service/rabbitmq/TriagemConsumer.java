package com.tech.sus_triage_api.service.rabbitmq;

import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.TriagemEventoDTO;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TriagemConsumer {

    private final TriagemRepository triagemRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(TriagemConsumer.class);

    public TriagemConsumer(TriagemRepository triagemRepository,
                           UnidadeSaudeRepository unidadeSaudeRepository,
                           RabbitTemplate rabbitTemplate) {
        this.triagemRepository = triagemRepository;
        this.unidadeSaudeRepository = unidadeSaudeRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRIAGEM)
    @Transactional
    public void processarAlocacao(TriagemEventoDTO evento) {
        logger.info(">>> [EVENTO RECEBIDO] Iniciando Alocação Inteligente para Triagem ID: {} | Risco: {}",
                evento.triagemId(), evento.risco());

        Triagem triagem = triagemRepository.findById(evento.triagemId())
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada: " + evento.triagemId()));

        // Idempotência: Evita reprocessamento se a mensagem for duplicada
        if (triagem.getStatus() != StatusTriagem.PENDENTE_ALOCACAO) {
            logger.info(">>> Triagem {} já processada anteriormente (Status: {}). Ignorando...",
                    evento.triagemId(), triagem.getStatus());
            return;
        }

        List<TipoUnidade> tiposAdequados = determinarTiposPorRisco(triagem.getRisco());

        // Esta query será beneficiada pelo Cache Redis que configuramos no Repository
        List<UnidadeSaude> unidadesDisponiveis = unidadeSaudeRepository.findDisponiveisPorTipos(tiposAdequados);

        if (unidadesDisponiveis.isEmpty()) {
            logger.error("!!! [ALERTA] Vagas esgotadas para risco {}. Movendo ID {} para Espera Crítica.",
                    triagem.getRisco(), evento.triagemId());
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ESPERA_CRITICA, evento);
            return;
        }

        // Algoritmo de Distância entre as unidades filtradas
        UnidadeSaude unidadeDestino = unidadesDisponiveis.stream()
                .min(Comparator.comparingDouble(u -> GeoUtils.haversine(
                        triagem.getPaciente().getLatitude(), triagem.getPaciente().getLongitude(),
                        u.getLatitude(), u.getLongitude()
                )))
                .orElseThrow();

        // Atualização de estado
        triagem.marcarComoAlocada(unidadeDestino);
        unidadeDestino.adicionarPaciente();

        unidadeSaudeRepository.save(unidadeDestino);
        triagemRepository.save(triagem);

        logger.info("+++ [SUCESSO] Paciente {} alocado na Unidade: {}",
                triagem.getPaciente().getNome(), unidadeDestino.getNome());
    }

    private List<TipoUnidade> determinarTiposPorRisco(Risco risco) {
        return switch (risco) {
            case VERMELHO, LARANJA -> List.of(TipoUnidade.HOSPITAL);
            case AMARELO -> List.of(TipoUnidade.UPA, TipoUnidade.HOSPITAL);
            case VERDE -> List.of(TipoUnidade.UBS, TipoUnidade.UPA);
            case AZUL -> List.of(TipoUnidade.UBS);
        };
    }
}