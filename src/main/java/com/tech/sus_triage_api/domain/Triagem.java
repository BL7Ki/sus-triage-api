package com.tech.sus_triage_api.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "triagens")
public class Triagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "unidade_saude_id") // Pode ser null inicialmente (antes da alocação)
    private UnidadeSaude unidadeDestino;

    // Dados de entrada (Sintomas)
    private String sintomas;
    private Integer pressaoArterialSistolica;
    private Integer pressaoArterialDiastolica;
    private Double temperatura;
    private Integer batimentosPorMinuto;
    private Integer saturacaoOxigenio;

    // Resultado do Processamento
    @Enumerated(EnumType.STRING)
    private Risco risco; // Calculado pelo sistema

    @Enumerated(EnumType.STRING)
    private StatusTriagem status;

    private LocalDateTime dataHora;

    @PrePersist
    public void prePersist() {
        this.dataHora = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusTriagem.PENDENTE_ALOCACAO;
        }
    }
}