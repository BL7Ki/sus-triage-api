package com.tech.sus_triage_api.repository.unidadesaude;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeRepositoryTest {
    @Test
    void testUnidadeSaudeRepositoryMethods() {
        UnidadeSaudeRepository repository = Mockito.mock(UnidadeSaudeRepository.class);
        assertNotNull(repository);
    }
}
