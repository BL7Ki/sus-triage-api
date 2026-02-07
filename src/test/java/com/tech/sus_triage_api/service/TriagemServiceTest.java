package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TriagemServiceTest {
    @Test
    void testRealizarTriagem() {
        // Mocks
        TriagemRepository triagemRepository = Mockito.mock(TriagemRepository.class);
        com.tech.sus_triage_api.repository.paciente.PacienteRepository pacienteRepository = Mockito.mock(com.tech.sus_triage_api.repository.paciente.PacienteRepository.class);
        com.tech.sus_triage_api.service.strategy.ITriagemStrategy triagemStrategy = Mockito.mock(com.tech.sus_triage_api.service.strategy.ITriagemStrategy.class);
        com.tech.sus_triage_api.service.rabbitmq.TriagemProducer triagemProducer = Mockito.mock(com.tech.sus_triage_api.service.rabbitmq.TriagemProducer.class);

        // Dados de entrada
        TriagemDTO triagemDTO = new TriagemDTO(
                "João",
                "12345678900",
                -23.55052,
                -46.633308,
                "febre, tosse",
                120,
                80,
                37.5,
                80,
                98);

        com.tech.sus_triage_api.domain.paciente.Paciente paciente = new com.tech.sus_triage_api.domain.paciente.Paciente("João", "12345678900");

        Mockito.when(pacienteRepository.findByCpf("12345678900")).thenReturn(java.util.Optional.of(paciente));

        com.tech.sus_triage_api.domain.enums.Risco risco = com.tech.sus_triage_api.domain.enums.Risco.VERDE;
        Mockito.when(triagemStrategy.classificar(triagemDTO)).thenReturn(risco);

        Mockito.when(triagemRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));

        TriagemService service = new TriagemService(triagemRepository, pacienteRepository, triagemStrategy, triagemProducer);
        com.tech.sus_triage_api.domain.triagem.Triagem triagem = service.realizarTriagem(triagemDTO);
        assertNotNull(triagem);
        assertEquals("João", triagem.getPaciente().getNome());
        assertEquals(risco, triagem.getRisco());
        Mockito.verify(triagemProducer).enviarParaFila(Mockito.any());
    }
}
