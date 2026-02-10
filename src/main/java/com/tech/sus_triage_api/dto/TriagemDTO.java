package com.tech.sus_triage_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record TriagemDTO(

        @NotBlank
        @Size(max = 50)
        String nomePaciente,
        @CPF
        String cpfPaciente,
        Double latitude,
        Double longitude,

        @NotBlank
        String sintomas,
        Integer pressaoSistolica,
        Integer pressaoDiastolica,
        Double temperatura,
        Integer batimentos,
        Integer saturacao
) {}
