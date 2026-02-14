package com.tech.sus_triage_api.service.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.triagem.TriagemEventoDTO;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TriagemConsumerTest {
    @Test
    void processarAlocacao_quandoPayloadInvalidoLancaIllegalStateException() throws JsonMappingException, JsonProcessingException {
        String payload = "invalido";
        when(objectMapper.readValue(payload, TriagemEventoDTO.class)).thenThrow(new com.fasterxml.jackson.core.JsonProcessingException("erro"){});
        assertThrows(IllegalStateException.class, () -> triagemConsumer.processarAlocacao(payload));
    }

    @Test
    void processarAlocacao_quandoTriagemNaoEncontradaLancaRuntimeException() throws Exception {
        TriagemEventoDTO evento = new TriagemEventoDTO(99L, com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        String payload = "payload";
        when(objectMapper.readValue(payload, TriagemEventoDTO.class)).thenReturn(evento);
        when(triagemRepository.findById(99L)).thenReturn(java.util.Optional.empty());
        assertThrows(RuntimeException.class, () -> triagemConsumer.processarAlocacao(payload));
    }

    @Test
    void processarAlocacao_quandoNenhumaUnidadeDisponivelEnviaParaEsperaCritica() throws Exception {
        TriagemEventoDTO evento = new TriagemEventoDTO(1L, com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        String payload = "payload";
        Triagem triagem = mock(Triagem.class);
        when(objectMapper.readValue(payload, TriagemEventoDTO.class)).thenReturn(evento);
        when(triagemRepository.findById(1L)).thenReturn(java.util.Optional.of(triagem));
        when(triagem.getStatus()).thenReturn(null);
        when(triagem.getUnidadeDestino()).thenReturn(null);
        when(triagem.getRisco()).thenReturn(com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        when(triagem.getPaciente()).thenReturn(mock(com.tech.sus_triage_api.domain.paciente.Paciente.class));
        when(unidadeSaudeRepository.findDisponiveisPorTipos(any())).thenReturn(java.util.Collections.emptyList());
        triagemConsumer.processarAlocacao(payload);
        verify(rabbitTemplate).convertAndSend(eq(com.tech.sus_triage_api.config.RabbitMQConfig.QUEUE_ESPERA_CRITICA), any(com.tech.sus_triage_api.dto.triagem.TriagemEventoDTO.class));
    }

    @Test
    void processarAlocacao_quandoEventoDuplicadoNaoAlocaNovamente() throws Exception {
        TriagemEventoDTO evento = new TriagemEventoDTO(1L, com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        String payload = "payload";
        Triagem triagem = mock(Triagem.class);
        UnidadeSaude unidade = mock(UnidadeSaude.class);
        when(objectMapper.readValue(payload, TriagemEventoDTO.class)).thenReturn(evento);
        when(triagemRepository.findById(1L)).thenReturn(java.util.Optional.of(triagem));
        when(triagem.getStatus()).thenReturn(com.tech.sus_triage_api.domain.enums.StatusTriagem.ALOCADO);
        when(triagem.getUnidadeDestino()).thenReturn(unidade);
        triagemConsumer.processarAlocacao(payload);
        verify(unidadeSaudeRepository, never()).save(any());
        verify(triagemRepository, never()).save(any());
    }

    @Test
    void processarAlocacao_quandoErroGenericoLancaExcecao() throws Exception {
        TriagemEventoDTO evento = new TriagemEventoDTO(1L, com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        String payload = "payload";
        Triagem triagem = mock(Triagem.class);
        when(objectMapper.readValue(payload, TriagemEventoDTO.class)).thenReturn(evento);
        when(triagemRepository.findById(1L)).thenReturn(java.util.Optional.of(triagem));
        when(triagem.getStatus()).thenReturn(null);
        when(triagem.getUnidadeDestino()).thenReturn(null);
        when(triagem.getRisco()).thenReturn(com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        when(triagem.getPaciente()).thenThrow(new RuntimeException("erro inesperado"));
        assertThrows(RuntimeException.class, () -> triagemConsumer.processarAlocacao(payload));
    }

    @Mock
    private TriagemRepository triagemRepository;
    @Mock
    private UnidadeSaudeRepository unidadeSaudeRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TriagemConsumer triagemConsumer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void processarAlocacao_deveAlocarPacienteQuandoUnidadeDisponivel() throws Exception {
        TriagemEventoDTO evento = new TriagemEventoDTO(1L, Risco.VERDE);
        String payload = "payload";
        when(objectMapper.readValue(payload, TriagemEventoDTO.class)).thenReturn(evento);
        Triagem triagem = mock(Triagem.class);
        when(triagemRepository.findById(1L)).thenReturn(Optional.of(triagem));
        when(triagem.getRisco()).thenReturn(Risco.VERDE);
        when(triagem.getPaciente()).thenReturn(mock(com.tech.sus_triage_api.domain.paciente.Paciente.class));
        UnidadeSaude unidade = mock(UnidadeSaude.class);
        when(unidadeSaudeRepository.findDisponiveisPorTipos(any())).thenReturn(List.of(unidade));
        when(unidade.getLatitude()).thenReturn(0.0);
        when(unidade.getLongitude()).thenReturn(0.0);
        when(triagem.getPaciente().getLatitude()).thenReturn(0.0);
        when(triagem.getPaciente().getLongitude()).thenReturn(0.0);
        when(unidade.getTipo()).thenReturn(TipoUnidade.UBS);
        when(unidade.getNome()).thenReturn("UBS Teste");
        when(triagem.getPaciente().getNome()).thenReturn("Paciente Teste");

        triagemConsumer.processarAlocacao(payload);

        verify(unidadeSaudeRepository).save(unidade);
        verify(triagemRepository).save(triagem);
    }
}
