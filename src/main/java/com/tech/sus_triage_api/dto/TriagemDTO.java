package com.tech.sus_triage_api.dto;

public record TriagemDTO(
        String nomePaciente,
        String cpfPaciente,
        Double latitude,
        Double longitude,
        String sintomas,
        Integer pressaoSistolica,
        Integer pressaoDiastolica,
        Double temperatura,
        Integer batimentos,
        Integer saturacao
) {}
