package com.tech.sus_triage_api.errors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SummerExceptionTest {
    @Test
    void construtorEGetMessageFuncionam() {
        SummerException ex = new SummerException("Mensagem de erro");
        assertEquals("Mensagem de erro", ex.getMessage());
    }
}
