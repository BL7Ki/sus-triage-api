package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemDTO;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.service.rabbitmq.TriagemProducer;
import com.tech.sus_triage_api.service.strategy.ITriagemStrategy;
import com.tech.sus_triage_api.errors.SummerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TriagemServiceTest {

    @Mock
    private TriagemRepository triagemRepository;
    @Mock
    private PacienteRepository pacienteRepository;
    @Mock
    private ITriagemStrategy triagemStrategy;
    @Mock
    private TriagemProducer triagemProducer;

    @InjectMocks
    private TriagemService triagemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarTriagem_deveSalvarTriagemENovoPacienteQuandoNaoExiste() {
        TriagemDTO dto = new TriagemDTO(
            "Joao",
            "123.456.789-00",
            10.0,
            20.0,
            "Dor de cabeça",
            120,
            80,
            36.5,
            70,
            98
        );

        when(pacienteRepository.findByCpf("123.456.789-00")).thenReturn(Optional.empty());
        Paciente novoPaciente = mock(Paciente.class);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(novoPaciente);
        when(triagemStrategy.classificar(dto)).thenReturn(Risco.VERDE);
        Triagem triagem = mock(Triagem.class);
        when(triagemRepository.save(any(Triagem.class))).thenReturn(triagem);

        Triagem resultado = triagemService.realizarTriagem(dto);

        assertNotNull(resultado);
        verify(pacienteRepository).save(any(Paciente.class));
        verify(triagemRepository).save(any(Triagem.class));
        verify(triagemProducer).enviarParaFila(triagem);
    }

    @Test
    void buscarTriagemPorId_deveRetornarTriagemQuandoExiste() {
        Triagem triagem = mock(Triagem.class);
        when(triagemRepository.findById(1L)).thenReturn(Optional.of(triagem));

        Triagem resultado = triagemService.buscarTriagemPorId(1L);

        assertNotNull(resultado);
        assertEquals(triagem, resultado);
    }

    @Test
    void buscarTriagemPorId_deveLancarExcecaoQuandoNaoExiste() {
        when(triagemRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(SummerNotFoundException.class, () -> triagemService.buscarTriagemPorId(2L));
    }
}
