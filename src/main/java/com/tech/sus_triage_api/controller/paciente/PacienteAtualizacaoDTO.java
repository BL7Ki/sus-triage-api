package com.tech.sus_triage_api.controller.paciente;

import jakarta.validation.constraints.NotNull;

public record PacienteAtualizacaoDTO(
    @NotNull
    Long id,
    @NotNull
    Double latitude,
    @NotNull
    Double longitude
) {
}

