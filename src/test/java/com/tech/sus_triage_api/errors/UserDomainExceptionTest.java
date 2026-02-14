package com.tech.sus_triage_api.errors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserDomainExceptionTest {
    @Test
    void construtorEGetMessageFuncionam() {
        UserDomainException ex = new UserDomainException("Erro de domínio");
        assertEquals("Erro de domínio", ex.getMessage());
    }
}
