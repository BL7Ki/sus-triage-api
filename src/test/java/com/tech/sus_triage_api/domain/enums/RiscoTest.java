package com.tech.sus_triage_api.domain.enums;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RiscoTest {
    @Test
    void shouldContainExpectedValues() {
        assertThat(Risco.valueOf("VERMELHO")).isNotNull();
        assertThat(Risco.valueOf("AMARELO")).isNotNull();
        assertThat(Risco.valueOf("VERDE")).isNotNull();
        assertThat(Risco.valueOf("AZUL")).isNotNull();
    }
}
