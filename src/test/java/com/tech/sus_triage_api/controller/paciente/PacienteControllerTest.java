package com.tech.sus_triage_api.controller.paciente;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.service.paciente.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    @BeforeEach
    void setUp() {
        // Não é necessário inicializar mocks manualmente com @ExtendWith(MockitoExtension.class)
    }

    @Test
    void criarPaciente_deveRetornarCreated() {
        PacienteRequestDTO requestDTO = mock(PacienteRequestDTO.class);
        PacienteEntity entity = mock(PacienteEntity.class);
        PacienteResponseDTO responseDTO = mock(PacienteResponseDTO.class);
        when(requestDTO.toEntity()).thenReturn(entity);
        when(entity.toResponseDTO()).thenReturn(responseDTO);
        when(pacienteService.criarPaciente(entity)).thenReturn(entity);

        ResponseEntity<PacienteResponseDTO> response = pacienteController.criarPaciente(requestDTO);

        assertEquals(org.springframework.http.HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void obterPacientePorId_deveRetornarOk() {
        PacienteEntity entity = mock(PacienteEntity.class);
        PacienteResponseDTO responseDTO = mock(PacienteResponseDTO.class);
        when(pacienteService.obterPacientePorId(1L)).thenReturn(entity);
        when(entity.toResponseDTO()).thenReturn(responseDTO);

        ResponseEntity<PacienteResponseDTO> response = pacienteController.obterPacientePorId(1L);

        assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void atualizarCoordenadas_deveRetornarOk() {
        PacienteAtualizacaoDTO atualizacaoDTO = mock(PacienteAtualizacaoDTO.class);
        PacienteEntity entity = mock(PacienteEntity.class);
        PacienteResponseDTO responseDTO = mock(PacienteResponseDTO.class);
        when(atualizacaoDTO.id()).thenReturn(1L);
        when(atualizacaoDTO.latitude()).thenReturn(10.0);
        when(atualizacaoDTO.longitude()).thenReturn(20.0);
        when(pacienteService.atualizarCoordenadas(1L, 10.0, 20.0)).thenReturn(entity);
        when(entity.toResponseDTO()).thenReturn(responseDTO);

        ResponseEntity<PacienteResponseDTO> response = pacienteController.atualizarCoordenadas(atualizacaoDTO);

        assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void buscarPacientesPorNome_deveRetornarListaOk() {
        PacienteEntity entity = mock(PacienteEntity.class);
        PacienteResponseDTO responseDTO = mock(PacienteResponseDTO.class);
        when(entity.toResponseDTO()).thenReturn(responseDTO);
        when(pacienteService.buscarPacientesPorNome("Ana")).thenReturn(List.of(entity));

        ResponseEntity<List<PacienteResponseDTO>> response = pacienteController.buscarPacientesPorNome("Ana");

        assertEquals(org.springframework.http.HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(responseDTO), response.getBody());
    }
}