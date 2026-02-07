package com.tech.sus_triage_api.domain.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.paciente.Paciente;

import com.tech.sus_triage_api.dto.TriagemDTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TriagemTest {
    @Test
    void testTriagemFields() {
        
        Paciente paciente = new Paciente("João", "12345678900");
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
        98);
        Triagem triagem = new Triagem(paciente, dto, Risco.AMARELO);
        
        assertEquals(paciente, triagem.getPaciente());
        assertEquals(Risco.AMARELO, triagem.getRisco());
    }
}
