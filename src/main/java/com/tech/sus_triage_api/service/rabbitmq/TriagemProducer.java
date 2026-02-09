package com.tech.sus_triage_api.service.rabbitmq;

import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.TriagemEventoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class TriagemProducer {

    private final RabbitTemplate rabbitTemplate = new RabbitTemplate();

    public TriagemProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarParaFila(Triagem triagem) {
        TriagemEventoDTO evento = new TriagemEventoDTO(
                triagem.getId(),
                triagem.getRisco()
        );

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TRIAGEM, evento);
                    System.out.println("Mensagem enviada com sucesso após COMMIT: ID " + evento.triagemId() + "Risco " +  evento.risco());
                }
            });
        } else {
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TRIAGEM, evento);
            System.out.println("Mensagem enviada (sem transação ativa): ID " + evento.triagemId() + "Risco " +  evento.risco());
        }
    }
}