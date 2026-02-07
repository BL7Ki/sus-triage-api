package com.tech.sus_triage_api.service.rabbitmq;

import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriagemProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void enviarParaFila(Triagem triagem) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_TRIAGEM, triagem);
        System.out.println("Mensagem enviada para RabbitMQ: Triagem ID " + triagem.getId());
    }
}