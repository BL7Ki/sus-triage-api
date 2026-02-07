package com.tech.sus_triage_api.service.rabbitmq;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

class TriagemProducerTest {
    @Test
    void testEnviarParaFila() throws Exception {

        RabbitTemplate rabbitTemplate = Mockito.mock(RabbitTemplate.class);

        TriagemProducer producer = new TriagemProducer();
        java.lang.reflect.Field field = TriagemProducer.class.getDeclaredField("rabbitTemplate");
        field.setAccessible(true);
        field.set(producer, rabbitTemplate);

        com.tech.sus_triage_api.domain.triagem.Triagem triagem = Mockito.mock(com.tech.sus_triage_api.domain.triagem.Triagem.class);
        Mockito.when(triagem.getId()).thenReturn(1L);

        producer.enviarParaFila(triagem);

        Mockito.verify(rabbitTemplate).convertAndSend(com.tech.sus_triage_api.config.RabbitMQConfig.QUEUE_TRIAGEM, triagem);
    }
}
