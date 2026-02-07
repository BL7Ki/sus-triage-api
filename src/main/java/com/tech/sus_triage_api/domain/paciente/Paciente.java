package com.tech.sus_triage_api.domain.paciente;

import com.tech.sus_triage_api.entities.PacienteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pacientes")
@AllArgsConstructor
@NoArgsConstructor
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

    public PacienteEntity toEntity() {

        return new PacienteEntity(
            this.id,
            this.nome,
            this.cpf,
            this.latitude,
            this.longitude
        );
    }
}
