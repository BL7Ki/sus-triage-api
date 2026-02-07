package com.tech.sus_triage_api.domain.paciente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {
    @Test
    void testPacienteFields() {
        Paciente paciente = new Paciente("João", "12345678900");
        
        assertEquals("João", paciente.getNome());
        assertEquals("12345678900", paciente.getCpf());
    }
}
