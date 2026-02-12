package com.tech.sus_triage_api.domain.unidadesaude;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import jakarta.persistence.*;

@Entity
@Table(name = "unidades_saude")
public class UnidadeSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome; // Ex: UPA Zona Sul

    @Enumerated(EnumType.STRING)
    private TipoUnidade tipo; // Hospital, UPA, UBS

    private Double latitude;
    private Double longitude;

    // Gestão de Recursos
    private Integer capacidadeTotal;
    private Integer ocupacaoAtual;

    public boolean temVaga() {
        return ocupacaoAtual < capacidadeTotal;
    }

    public void adicionarPaciente() {
        if (this.ocupacaoAtual == null) {
            this.ocupacaoAtual = 0;
        }
        if (this.ocupacaoAtual < this.capacidadeTotal) {
            this.ocupacaoAtual++;
        } else {
            throw new IllegalStateException("Unidade sem capacidade disponível.");
        }
    }

    public UnidadeSaude() {}

    public UnidadeSaude(String nome, TipoUnidade tipo, Double latitude, Double longitude, Integer capacidadeTotal) {
        this.nome = nome;
        this.tipo = tipo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacidadeTotal = capacidadeTotal;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public TipoUnidade getTipo() { return tipo; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getCapacidadeTotal() { return capacidadeTotal; }
    public Integer getOcupacaoAtual() { return ocupacaoAtual; }
}
