package com.tech.sus_triage_api.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_TRIAGEM = "triagem.pendente";
    public static final String QUEUE_DLQ = "triagem.pendente.dlq";
    public static final String EXCHANGE_DLX = "triagem.dlx";
    public static final String QUEUE_ESPERA_CRITICA = "triagem.espera.critica";

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(EXCHANGE_DLX);
    }

    @Bean
    public Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", EXCHANGE_DLX);
        args.put("x-dead-letter-routing-key", "deadLetter");
        return new Queue(QUEUE_TRIAGEM, true, false, false, args);
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(QUEUE_DLQ, true);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with("deadLetter");
    }

    @Bean
    public Queue queueEsperaCritica() {
        return new Queue(QUEUE_ESPERA_CRITICA, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}