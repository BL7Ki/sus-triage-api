package com.tech.sus_triage_api.controller;

import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.service.TriagemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class TriagemControllerTest {
    @Test
    void testCriarTriagem() {
        TriagemService triagemService = Mockito.mock(TriagemService.class);
        TriagemController controller = new TriagemController(triagemService);
        // Dados de entrada
        TriagemDTO dto = new TriagemDTO(
            "João",
            "12345678900",
            -23.55052,
            -46.633308,
            "febre, tosse",
            120,
            80,
            37.5,
            80,
            98
        );
        Triagem triagem = Mockito.mock(Triagem.class);
        Mockito.when(triagemService.realizarTriagem(dto)).thenReturn(triagem);
        ResponseEntity<Triagem> response = controller.criarTriagem(dto);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(triagem, response.getBody());
    }
}
