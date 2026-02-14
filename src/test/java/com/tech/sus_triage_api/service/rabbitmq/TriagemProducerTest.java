package com.tech.sus_triage_api.service.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemEventoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class TriagemProducerTest {
    @Test
    void enviarParaFila_comTransacaoAtivaEnviaAposCommit() throws Exception {
        Triagem triagem = mock(Triagem.class);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getRisco()).thenReturn(null);
        TriagemEventoDTO evento = new TriagemEventoDTO(1L, null);
        when(objectMapper.writeValueAsString(any())).thenReturn("payload");
        try (var tm = mockStatic(TransactionSynchronizationManager.class)) {
            tm.when(TransactionSynchronizationManager::isActualTransactionActive).thenReturn(true);
            tm.when(() -> TransactionSynchronizationManager.registerSynchronization(any())).then(invocation -> {
                var sync = invocation.getArgument(0, org.springframework.transaction.support.TransactionSynchronization.class);
                sync.afterCommit();
                return null;
            });
            triagemProducer.enviarParaFila(triagem);
            verify(rabbitTemplate).convertAndSend(RabbitMQConfig.EXCHANGE_TRIAGEM, RabbitMQConfig.ROUTING_KEY_TRIAGEM, "payload");
        }
    }

    @Test
    void enviarParaFila_quandoErroSerializacaoLancaIllegalStateException() throws Exception {
        Triagem triagem = mock(Triagem.class);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getRisco()).thenReturn(null);
        when(objectMapper.writeValueAsString(any())).thenThrow(new JsonProcessingException("erro"){});
        try {
            triagemProducer.enviarParaFila(triagem);
            fail("Deveria lançar IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("Erro ao serializar evento de triagem"));
        }
    }

    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TriagemProducer triagemProducer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void enviarParaFila_semTransacaoEnviaMensagem() throws JsonProcessingException {
        Triagem triagem = mock(Triagem.class);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getRisco()).thenReturn(null);
        TriagemEventoDTO evento = new TriagemEventoDTO(1L, null);
        when(objectMapper.writeValueAsString(any())).thenReturn("payload");
        mockStatic(TransactionSynchronizationManager.class);
        when(TransactionSynchronizationManager.isActualTransactionActive()).thenReturn(false);

        triagemProducer.enviarParaFila(triagem);

        verify(rabbitTemplate).convertAndSend(RabbitMQConfig.EXCHANGE_TRIAGEM, RabbitMQConfig.ROUTING_KEY_TRIAGEM, "payload");
    }
}
