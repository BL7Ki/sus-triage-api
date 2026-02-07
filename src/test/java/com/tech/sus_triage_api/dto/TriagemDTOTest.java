package com.tech.sus_triage_api.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriagemDTOTest {
    @Test
    void testTriagemDTOFields() {
        // Dados de entrada
        TriagemDTO dto = new TriagemDTO("João", 
        "12345678900", 
        -23.55052, 
        -46.633308, 
        "febre, tosse", 
        120, 
        80, 
        37.5, 
        80, 
        98);
        
        assertEquals("João", dto.nomePaciente());
        assertEquals("12345678900", dto.cpfPaciente());     
        assertEquals(-23.55052, dto.latitude());
        assertEquals(-46.633308, dto.longitude());
        assertEquals("febre, tosse", dto.sintomas());
        assertEquals(120, dto.pressaoSistolica());
        assertEquals(80, dto.pressaoDiastolica());
        assertEquals(37.5, dto.temperatura());
        assertEquals(80, dto.batimentos());
        assertEquals(98, dto.saturacao());
    }
}
