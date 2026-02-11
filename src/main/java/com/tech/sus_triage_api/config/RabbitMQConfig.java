package com.tech.sus_triage_api.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;



@Configuration
public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    public static final String EXCHANGE_TRIAGEM = "triagem.exchange";
    public static final String QUEUE_TRIAGEM = "triagem.pendente";
    public static final String ROUTING_KEY_TRIAGEM = "triagem.routing";
    public static final String QUEUE_ESPERA_CRITICA = "triagem.espera.critica";

    @Bean
    public DirectExchange triagemExchange() {
        return new DirectExchange(EXCHANGE_TRIAGEM, true, false);
    }

    @Bean
    public Queue triagemQueue() {
        return new Queue(QUEUE_TRIAGEM, true);
    }

    @Bean
    public Queue queueEsperaCritica() {
        return new Queue(QUEUE_ESPERA_CRITICA, true);
    }

    @Bean
    public Binding triagemBinding(Queue triagemQueue, DirectExchange triagemExchange) {
        System.out.println("🔧 [CONFIG] Criando binding entre fila '" + triagemQueue.getName() + "' e exchange '" + triagemExchange.getName() + "' com routing key '" + ROUTING_KEY_TRIAGEM + "'");
        Binding binding = BindingBuilder.bind(triagemQueue)
                .to(triagemExchange)
                .with(ROUTING_KEY_TRIAGEM);
        System.out.println("✅ [CONFIG] Binding criado com sucesso!");
        return binding;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new SimpleMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setMandatory(true);
        template.setReturnsCallback(returned -> logger.error(
                "RabbitMQ retornou mensagem (não roteada): replyCode={}, replyText={}, exchange={}, routingKey={}",
                returned.getReplyCode(),
                returned.getReplyText(),
                returned.getExchange(),
                returned.getRoutingKey()
        ));
        return template;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

}
