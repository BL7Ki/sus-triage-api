package com.tech.sus_triage_api.domain.unidadesaude;

import jakarta.persistence.*;

@Entity
@Table(name = "unidades_saude")
public class UnidadeSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // Ex: UPA Zona Sul

    private String tipo; // Hospital, UPA, UBS

    // Localização da Unidade (para o cálculo de distância)
    private Double latitude;
    private Double longitude;

    // Gestão de Recursos
    private Integer capacidadeTotal;
    private Integer ocupacaoAtual;

    public boolean temVaga() {
        return ocupacaoAtual < capacidadeTotal;
    }

    public UnidadeSaude() {}

    public UnidadeSaude(String nome, String tipo, Double latitude, Double longitude, Integer capacidadeTotal) {
        this.nome = nome;
        this.tipo = tipo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacidadeTotal = capacidadeTotal;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getCapacidadeTotal() { return capacidadeTotal; }
    public Integer getOcupacaoAtual() { return ocupacaoAtual; }
}
