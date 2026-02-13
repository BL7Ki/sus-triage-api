package com.tech.sus_triage_api.dto.paciente;


public record PacienteResponseDTO(
    String id,
    String nome,
    String cpf,
    Double latitude,
    Double longitude
) {
}
