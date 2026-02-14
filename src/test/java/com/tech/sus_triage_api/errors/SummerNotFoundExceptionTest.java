package com.tech.sus_triage_api.errors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SummerNotFoundExceptionTest {
    @Test
    void construtorEGetMessageFuncionam() {
        SummerNotFoundException ex = new SummerNotFoundException("Não encontrado");
        assertEquals("Não encontrado", ex.getMessage());
    }
}
