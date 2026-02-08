package com.tech.sus_triage_api.service.rabbitmq;

import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.TriagemEventoDTO;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository;
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
        System.out.println(">>> Processando Alocação Inteligente. ID: " + evento.triagemId() + " | Risco: " + evento.risco());

        Triagem triagem = triagemRepository.findById(evento.triagemId())
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada: " + evento.triagemId()));

        List<TipoUnidade> tiposAdequados = determinarTiposPorRisco(triagem.getRisco());

        List<UnidadeSaude> unidadesDisponiveis = unidadeSaudeRepository.findDisponiveisPorTipos(tiposAdequados);

        if (unidadesDisponiveis.isEmpty()) {
            System.err.println("CRÍTICO: Nenhuma vaga disponível para risco " + triagem.getRisco() + ". Enviando para Espera Crítica.");
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ESPERA_CRITICA, evento);
            return;
        }

        // Algoritmo de Distância entre as unidades filtradas
        UnidadeSaude unidadeDestino = unidadesDisponiveis.stream()
                .min(Comparator.comparingDouble(u -> calcularDistancia(
                        triagem.getPaciente().getLatitude(), triagem.getPaciente().getLongitude(),
                        u.getLatitude(), u.getLongitude()
                )))
                .orElseThrow();

        triagem.marcarComoAlocada(unidadeDestino);
        unidadeDestino.adicionarPaciente();

        unidadeSaudeRepository.save(unidadeDestino);
        triagemRepository.save(triagem);

        System.out.println("SUCESSO: Paciente " + triagem.getPaciente().getNome() +
                " encaminhado para " + unidadeDestino.getTipo() + " " + unidadeDestino.getNome());
    }

    // Mapeamento da Regulação do SUS
    private List<TipoUnidade> determinarTiposPorRisco(Risco risco) {
        return switch (risco) {
            case VERMELHO, LARANJA -> List.of(TipoUnidade.HOSPITAL);
            case AMARELO -> List.of(TipoUnidade.UPA, TipoUnidade.HOSPITAL);
            case VERDE -> List.of(TipoUnidade.UBS, TipoUnidade.UPA);
            case AZUL -> List.of(TipoUnidade.UBS);
        };
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
    }
}