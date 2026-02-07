package com.tech.sus_triage_api.domain.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.dto.TriagemDTO;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "unidade_saude_id")
    private UnidadeSaude unidadeDestino;

    private String sintomas;
    private Integer pressaoArterialSistolica;
    private Integer pressaoArterialDiastolica;
    private Double temperatura;
    private Integer batimentosPorMinuto;
    private Integer saturacaoOxigenio;

    @Enumerated(EnumType.STRING)
    private Risco risco;

    @Enumerated(EnumType.STRING)
    private StatusTriagem status;

    private LocalDateTime dataHora;

    public Triagem() {}

    public Triagem(Paciente paciente, TriagemDTO dto, Risco risco) {
        this.paciente = paciente;
        this.sintomas = dto.sintomas();
        this.pressaoArterialSistolica = dto.pressaoSistolica();
        this.pressaoArterialDiastolica = dto.pressaoDiastolica();
        this.temperatura = dto.temperatura();
        this.batimentosPorMinuto = dto.batimentos();
        this.saturacaoOxigenio = dto.saturacao();
        this.risco = risco;
        this.status = StatusTriagem.PENDENTE_ALOCACAO;
        this.dataHora = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public UnidadeSaude getUnidadeDestino() { return unidadeDestino; }
    public Risco getRisco() { return risco; }
    public StatusTriagem getStatus() { return status; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getSintomas() { return sintomas; }
    public Integer getPressaoArterialSistolica() { return pressaoArterialSistolica; }
    public Integer getPressaoArterialDiastolica() { return pressaoArterialDiastolica; }
    public Double getTemperatura() { return temperatura; }
    public Integer getBatimentosPorMinuto() { return batimentosPorMinuto; }
    public Integer getSaturacaoOxigenio() { return saturacaoOxigenio; }

    public void marcarComoAlocada(UnidadeSaude unidade) {
        this.unidadeDestino = unidade;
        this.status = StatusTriagem.ALOCADO;
    }
}