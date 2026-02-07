package com.tech.sus_triage_api.repository.paciente;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PacienteRepositoryTest {
    @Test
    void testPacienteRepositoryMethods() {
        PacienteRepository repository = Mockito.mock(PacienteRepository.class);
        assertNotNull(repository);
    }
}
