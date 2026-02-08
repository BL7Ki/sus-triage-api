package com.tech.sus_triage_api.domain.enums;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class StatusTriagemTest {
    @Test
    void shouldContainExpectedValues() {
           assertThat(StatusTriagem.valueOf("PENDENTE_ALOCACAO")).isNotNull();
           assertThat(StatusTriagem.valueOf("ALOCADO")).isNotNull();
           assertThat(StatusTriagem.valueOf("FINALIZADO")).isNotNull();
    }
}
