package com.tech.sus_triage_api.dto.paciente;

import com.tech.sus_triage_api.entities.PacienteEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PacienteRequestDTO(

    @NotBlank
    @Size(max = 50)
    String nome,
    @CPF
    String cpf,
    Double latitude,
    Double longitude
) {
    public PacienteEntity toEntity() {

        return new PacienteEntity(
            null, // ID será gerado posteriormente
            this.nome,
            this.cpf,
            this.latitude,
            this.longitude
        );
    }
}
