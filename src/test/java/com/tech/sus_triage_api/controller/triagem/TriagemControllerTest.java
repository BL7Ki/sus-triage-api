package com.tech.sus_triage_api.controller.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemDTO;
import com.tech.sus_triage_api.dto.triagem.TriagemResponseDTO;
import com.tech.sus_triage_api.service.TriagemService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriagemControllerTest {
    @Mock
    private TriagemService triagemService;
    @InjectMocks
    private TriagemController triagemController;

    @Test
    void criarTriagem_deveRetornarOk() {
        TriagemDTO dto = new TriagemDTO("Paciente Teste", "123.456.789-00", 0.0, 0.0, "Dor", 120, 80, 36.5, 70, 98);
        Triagem triagem = mock(Triagem.class);
        Paciente paciente = mock(Paciente.class);
        when(paciente.getNome()).thenReturn("Paciente Teste");
        when(triagem.getPaciente()).thenReturn(paciente);
        when(triagemService.realizarTriagem(dto)).thenReturn(triagem);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getRisco()).thenReturn(null);
        when(triagem.getStatus()).thenReturn(null);
        when(triagem.getDataHora()).thenReturn(null);
        when(triagem.getUnidadeDestino()).thenReturn(null);
        ResponseEntity<TriagemResponseDTO> response = triagemController.criarTriagem(dto);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void buscarTriagem_deveRetornarOk() {
        Triagem triagem = mock(Triagem.class);
        Paciente paciente = mock(Paciente.class);
        when(paciente.getNome()).thenReturn("Paciente Teste");
        when(triagem.getPaciente()).thenReturn(paciente);
        when(triagemService.buscarTriagemPorId(1L)).thenReturn(triagem);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getRisco()).thenReturn(null);
        when(triagem.getStatus()).thenReturn(null);
        when(triagem.getDataHora()).thenReturn(null);
        when(triagem.getUnidadeDestino()).thenReturn(null);
        ResponseEntity<TriagemResponseDTO> response = triagemController.buscarTriagem(1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void buscarTriagem_quandoNaoEncontrado_lancaNullPointerException() {
        when(triagemService.buscarTriagemPorId(99L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> triagemController.buscarTriagem(99L));
    }

    @Test
    void criarTriagem_deveRetornarResponseDTO() {
        TriagemDTO dto = new TriagemDTO("Paciente Teste", "123.456.789-00", 0.0, 0.0, "Dor", 120, 80, 36.5, 70, 98);
        Paciente paciente = new Paciente(1L, "Paciente Teste", "123.456.789-00", 0.0, 0.0);
        Triagem triagem = mock(Triagem.class);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getPaciente()).thenReturn(paciente);
        when(triagem.getRisco()).thenReturn(Risco.VERDE);
        when(triagem.getStatus()).thenReturn(StatusTriagem.PENDENTE_ALOCACAO);
        when(triagem.getDataHora()).thenReturn(LocalDateTime.now());
        when(triagem.getUnidadeDestino()).thenReturn(null);
        when(triagemService.realizarTriagem(dto)).thenReturn(triagem);

        ResponseEntity<TriagemResponseDTO> response = triagemController.criarTriagem(dto);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Paciente Teste", response.getBody().nomePaciente());
        assertEquals(Risco.VERDE, response.getBody().risco());
    }

    @Test
    void buscarTriagem_deveRetornarResponseDTO() {
        Paciente paciente = new Paciente(1L, "Paciente Teste", "123.456.789-00", 0.0, 0.0);
        Triagem triagem = mock(Triagem.class);
        when(triagem.getId()).thenReturn(1L);
        when(triagem.getPaciente()).thenReturn(paciente);
        when(triagem.getRisco()).thenReturn(Risco.VERDE);
        when(triagem.getStatus()).thenReturn(StatusTriagem.ALOCADO);
        when(triagem.getDataHora()).thenReturn(LocalDateTime.now());
        when(triagem.getUnidadeDestino()).thenReturn(null);
        when(triagemService.buscarTriagemPorId(1L)).thenReturn(triagem);

        ResponseEntity<TriagemResponseDTO> response = triagemController.buscarTriagem(1L);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("Paciente Teste", response.getBody().nomePaciente());
        assertEquals(Risco.VERDE, response.getBody().risco());
    }
}
