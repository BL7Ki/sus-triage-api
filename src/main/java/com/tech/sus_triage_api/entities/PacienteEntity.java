package com.tech.sus_triage_api.entities;

import com.tech.sus_triage_api.dto.paciente.PacienteResponseDTO;
import com.tech.sus_triage_api.domain.paciente.Paciente;

public class PacienteEntity {

    private Long id;
    private String nome;
    private String cpf;
    private Double latitude;
    private Double longitude;

    public PacienteEntity(Long id, String nome, String cpf, Double latitude, Double longitude) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Paciente toDomain() {

        return new Paciente(
            this.id,
            this.nome,
            this.cpf,
            this.latitude,
            this.longitude
        );
    }

    public PacienteResponseDTO toResponseDTO() {

        return new PacienteResponseDTO(
            this.id.toString(),
            this.nome,
            this.cpf,
            this.latitude,
            this.longitude
        );
    }
}
