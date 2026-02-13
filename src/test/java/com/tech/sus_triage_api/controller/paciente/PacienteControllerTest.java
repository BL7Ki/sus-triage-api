package com.tech.sus_triage_api.controller.paciente;

import com.tech.sus_triage_api.dto.paciente.PacienteAtualizacaoDTO;
import com.tech.sus_triage_api.dto.paciente.PacienteRequestDTO;
import com.tech.sus_triage_api.dto.paciente.PacienteResponseDTO;
import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.service.paciente.PacienteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {
    @Mock
    private PacienteService pacienteService;
    @InjectMocks
    private PacienteController pacienteController;

    @Test
    void criarPaciente_deveRetornarCreated() {
        PacienteRequestDTO requestDTO = new PacienteRequestDTO("João", "123.456.789-00", -23.5, -46.6);
        PacienteEntity entity = requestDTO.toEntity();
        PacienteEntity entityCriado = new PacienteEntity(1L, entity.getNome(), entity.getCpf(), entity.getLatitude(), entity.getLongitude());
        PacienteResponseDTO responseDTO = entityCriado.toResponseDTO();
        when(pacienteService.criarPaciente(any(PacienteEntity.class))).thenReturn(entityCriado);

        ResponseEntity<PacienteResponseDTO> response = pacienteController.criarPaciente(requestDTO);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void obterPacientePorId_deveRetornarOk() {
        PacienteEntity entity = new PacienteEntity(1L, "Maria", "987.654.321-00", -22.0, -47.0);
        PacienteResponseDTO responseDTO = entity.toResponseDTO();
        when(pacienteService.obterPacientePorId(1L)).thenReturn(entity);

        ResponseEntity<PacienteResponseDTO> response = pacienteController.obterPacientePorId(1L);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void atualizarCoordenadas_deveRetornarOk() {
        PacienteAtualizacaoDTO atualizacaoDTO = new PacienteAtualizacaoDTO(1L, 10.0, 20.0);
        PacienteEntity entity = new PacienteEntity(1L, "Carlos", "111.222.333-44", 10.0, 20.0);
        PacienteResponseDTO responseDTO = entity.toResponseDTO();
        when(pacienteService.atualizarCoordenadas(1L, 10.0, 20.0)).thenReturn(entity);

        ResponseEntity<PacienteResponseDTO> response = pacienteController.atualizarCoordenadas(atualizacaoDTO);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
    }

    @Test
    void buscarPacientesPorNome_deveRetornarListaOk() {
        PacienteEntity entity = new PacienteEntity(2L, "Ana", "555.666.777-88", -21.0, -48.0);
        PacienteResponseDTO responseDTO = entity.toResponseDTO();
        when(pacienteService.buscarPacientesPorNome("Ana")).thenReturn(List.of(entity));

        ResponseEntity<List<PacienteResponseDTO>> response = pacienteController.buscarPacientesPorNome("Ana");
        assertEquals(200, response.getStatusCode().value());
        assertEquals(List.of(responseDTO), response.getBody());
    }
}
