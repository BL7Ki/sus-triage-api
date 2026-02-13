package com.tech.sus_triage_api.service.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem; // Certifique-se de ter esse import
import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.triagem.TriagemEventoDTO;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository;
import com.tech.sus_triage_api.util.GeoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Service
public class TriagemConsumer {

    private final TriagemRepository triagemRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(TriagemConsumer.class);

    public TriagemConsumer(TriagemRepository triagemRepository,
                           UnidadeSaudeRepository unidadeSaudeRepository,
                           RabbitTemplate rabbitTemplate,
                           ObjectMapper objectMapper) {
        this.triagemRepository = triagemRepository;
        this.unidadeSaudeRepository = unidadeSaudeRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        logger.info("🔧 TriagemConsumer construtor chamado!");
        System.out.println("🔧 [INIT] TriagemConsumer sendo criado pelo Spring!");
    }

    @PostConstruct
    public void init() {
        logger.info("✅ TriagemConsumer inicializado e pronto para escutar fila: {}", RabbitMQConfig.QUEUE_TRIAGEM);
        System.out.println("✅ [INIT] TriagemConsumer inicializado! Aguardando mensagens na fila: " + RabbitMQConfig.QUEUE_TRIAGEM);
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRIAGEM)
    @Transactional
    public void processarAlocacao(String payload) {
        TriagemEventoDTO evento;
        try {
            evento = objectMapper.readValue(payload, TriagemEventoDTO.class);
        } catch (IOException e) {
            logger.error("❌ ERRO ao desserializar mensagem da fila: {}", e.getMessage(), e);
            throw new IllegalStateException("Erro ao desserializar mensagem da fila", e);
        }

        try {
            logger.info("========================================");
            logger.info("✓ LISTENER ATIVADO! Mensagem recebida da fila: {}", RabbitMQConfig.QUEUE_TRIAGEM);
            logger.info("Processando Alocação Inteligente. ID: {}, Risco: {}", evento.triagemId(), evento.risco());
            System.out.println(">>> [CONSUMER] Processando Alocação Inteligente. ID: " + evento.triagemId() + " | Risco: " + evento.risco());

            Triagem triagem = triagemRepository.findById(evento.triagemId())
                    .orElseThrow(() -> new RuntimeException("Triagem não encontrada: " + evento.triagemId()));

            if (triagem.getStatus() == StatusTriagem.ALOCADO && triagem.getUnidadeDestino() != null) {
                logger.warn("⚠️ [IDEMPOTÊNCIA] Evento duplicado ignorado! Triagem ID {} já foi alocada na unidade: {}",
                        triagem.getId(), triagem.getUnidadeDestino().getNome());
                System.out.println("⚠️ [IDEMPOTÊNCIA] Ignorando mensagem duplicada para Triagem ID: " + triagem.getId());

                return;
            }

            logger.info("Triagem encontrada no BD: ID={}, Paciente={}", triagem.getId(), triagem.getPaciente().getNome());

            List<TipoUnidade> tiposAdequados = determinarTiposPorRisco(triagem.getRisco());
            logger.info("Tipos de unidade adequados para risco {}: {}", triagem.getRisco(), tiposAdequados);

            List<UnidadeSaude> unidadesDisponiveis = unidadeSaudeRepository.findDisponiveisPorTipos(tiposAdequados);
            logger.info("Unidades disponíveis encontradas: {}", unidadesDisponiveis.size());

            if (unidadesDisponiveis.isEmpty()) {
                logger.error("CRÍTICO: Nenhuma vaga disponível para risco {}. Enviando para Espera Crítica.", triagem.getRisco());
                System.err.println("[CONSUMER] CRÍTICO: Nenhuma vaga disponível para risco " + triagem.getRisco());
                rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ESPERA_CRITICA, evento);
                return;
            }

            UnidadeSaude unidadeDestino = unidadesDisponiveis.stream()
                    .min(Comparator.comparingDouble(u -> GeoUtils.haversine(
                            triagem.getPaciente().getLatitude(), triagem.getPaciente().getLongitude(),
                            u.getLatitude(), u.getLongitude()
                    )))
                    .orElseThrow();

            logger.info("Unidade selecionada: {} - {}", unidadeDestino.getTipo(), unidadeDestino.getNome());

            triagem.marcarComoAlocada(unidadeDestino);
            unidadeDestino.adicionarPaciente();

            unidadeSaudeRepository.save(unidadeDestino);
            triagemRepository.save(triagem);

            logger.info("✓ SUCESSO: Paciente {} alocado em {} - {}",
                    triagem.getPaciente().getNome(),
                    unidadeDestino.getTipo(),
                    unidadeDestino.getNome());
            System.out.println("[CONSUMER] SUCESSO: Paciente " + triagem.getPaciente().getNome() +
                    " encaminhado para " + unidadeDestino.getTipo() + " " + unidadeDestino.getNome());
            logger.info("========================================");

        } catch (Exception e) {
            logger.error("❌ ERRO ao processar alocação: {}", e.getMessage(), e);
            System.err.println("[CONSUMER] ERRO: " + e.getMessage());
            throw e;
        }
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
}