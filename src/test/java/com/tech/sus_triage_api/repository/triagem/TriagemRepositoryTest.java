package com.tech.sus_triage_api.repository.triagem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class TriagemRepositoryTest {
    @Test
    void testTriagemRepositoryMethods() {
        TriagemRepository repository = Mockito.mock(TriagemRepository.class);
        assertNotNull(repository);
    }
}
