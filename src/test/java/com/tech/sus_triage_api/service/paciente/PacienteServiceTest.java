package com.tech.sus_triage_api.service.paciente;

import static org.junit.jupiter.api.Assertions.*;

import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.errors.SummerException;
import com.tech.sus_triage_api.errors.SummerNotFoundException;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import java.util.List;
import static org.mockito.Mockito.*;

class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarPaciente_deveSalvarQuandoCpfNaoExiste() {
        PacienteEntity entity = mock(PacienteEntity.class);
        when(entity.getCpf()).thenReturn("12345678900");
        Paciente paciente = mock(Paciente.class);
        when(entity.toDomain()).thenReturn(paciente);
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.empty());
        when(pacienteRepository.save(paciente)).thenReturn(paciente);
        when(paciente.toEntity()).thenReturn(entity);

        PacienteEntity resultado = pacienteService.criarPaciente(entity);

        assertNotNull(resultado);
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void criarPaciente_deveLancarExcecaoQuandoCpfExiste() {
        PacienteEntity entity = mock(PacienteEntity.class);
        when(entity.getCpf()).thenReturn("12345678900");
        when(pacienteRepository.findByCpf("12345678900")).thenReturn(Optional.of(mock(Paciente.class)));

        assertThrows(SummerException.class, () -> pacienteService.criarPaciente(entity));
    }

    @Test
    void obterPacientePorId_deveRetornarPacienteQuandoExiste() {
        Paciente paciente = mock(Paciente.class);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
        PacienteEntity entity = mock(PacienteEntity.class);
        when(paciente.toEntity()).thenReturn(entity);

        PacienteEntity resultado = pacienteService.obterPacientePorId(1L);

        assertNotNull(resultado);
        assertEquals(entity, resultado);
    }

    @Test
    void obterPacientePorId_deveLancarExcecaoQuandoNaoExiste() {
        when(pacienteRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(SummerNotFoundException.class, () -> pacienteService.obterPacientePorId(2L));
    }

    @Test
    void buscarPacientesPorNome_deveRetornarListaQuandoExiste() {
        Paciente paciente = mock(Paciente.class);
        when(paciente.toEntity()).thenReturn(mock(PacienteEntity.class));
        when(pacienteRepository.buscarPorNome("Ana")).thenReturn(List.of(paciente));

        List<PacienteEntity> resultado = pacienteService.buscarPacientesPorNome("Ana");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void buscarPacientesPorNome_deveLancarExcecaoQuandoNomeVazio() {
        assertThrows(SummerException.class, () -> pacienteService.buscarPacientesPorNome(" "));
    }

    @Test
    void buscarPacientesPorNome_deveLancarExcecaoQuandoNaoExiste() {
        when(pacienteRepository.buscarPorNome("Carlos")).thenReturn(List.of());

        assertThrows(SummerNotFoundException.class, () -> pacienteService.buscarPacientesPorNome("Carlos"));
    }
}