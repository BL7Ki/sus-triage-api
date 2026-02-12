package com.tech.sus_triage_api.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {
    private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void triagemExchangeBeanRetornaExchange() {
        DirectExchange exchange = config.triagemExchange();
        assertEquals(RabbitMQConfig.EXCHANGE_TRIAGEM, exchange.getName());
        assertTrue(exchange.isDurable());
    }

    @Test
    void triagemDeadLetterExchangeBeanRetornaExchange() {
        DirectExchange exchange = config.triagemDeadLetterExchange();
        assertEquals(RabbitMQConfig.EXCHANGE_TRIAGEM_DLX, exchange.getName());
        assertTrue(exchange.isDurable());
    }
}
