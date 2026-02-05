package com.tech.sus_triage_api.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    // Simplificação: Latitude/Longitude simulada do endereço do paciente
    private Double latitude;
    private Double longitude;
}
