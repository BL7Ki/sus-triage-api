package com.tech.sus_triage_api.domain.paciente;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteTest {

    @Test
    void construtorNomeCpfFunciona() {
        Paciente paciente = new Paciente("Maria", "12345678900");
        assertNull(paciente.getId());
        assertEquals("Maria", paciente.getNome());
        assertEquals("12345678900", paciente.getCpf());
        assertNull(paciente.getLatitude());
        assertNull(paciente.getLongitude());
    }

    @Test
    void construtorCompletoFunciona() {
        Paciente paciente = new Paciente(1L, "Joao", "98765432100", 10.0, 20.0);
        assertEquals(1L, paciente.getId());
        assertEquals("Joao", paciente.getNome());
        assertEquals("98765432100", paciente.getCpf());
        assertEquals(10.0, paciente.getLatitude());
        assertEquals(20.0, paciente.getLongitude());
    }

    @Test
    void atualizarCoordenadasFunciona() {
        Paciente paciente = new Paciente("Ana", "11122233344");
        paciente.atualizarCoordenadas(30.0, 40.0);
        assertEquals(30.0, paciente.getLatitude());
        assertEquals(40.0, paciente.getLongitude());
    }
}
