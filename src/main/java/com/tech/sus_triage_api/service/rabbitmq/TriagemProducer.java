package com.tech.sus_triage_api.service.rabbitmq;

import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemEventoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TriagemProducer {

    private final RabbitTemplate rabbitTemplate;

    private final Logger logger = LoggerFactory.getLogger(TriagemProducer.class);

    public TriagemProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarParaFila(Triagem triagem) {

        logger.info("Preparando evento de triagem para envio: ID {}, Risco {}", triagem.getId(), triagem.getRisco());

        TriagemEventoDTO evento = new TriagemEventoDTO(
                triagem.getId(),
                triagem.getRisco()
        );

        if (TransactionSynchronizationManager.isActualTransactionActive()) {

            logger.info("Transação ativa detectada, registrando sincronização para envio após COMMIT: ID {}, Risco {}", evento.triagemId(), evento.risco());
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TRIAGEM, evento);

                    logger.info("Mensagem enviada com sucesso após COMMIT: ID {}, Risco {}", evento.triagemId(), evento.risco());
                }
            });
        } else {
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TRIAGEM, evento);

            logger.warn("Nenhuma transação ativa detectada, enviando mensagem imediatamente: ID {}, Risco {}", evento.triagemId(), evento.risco());
        }
    }
}