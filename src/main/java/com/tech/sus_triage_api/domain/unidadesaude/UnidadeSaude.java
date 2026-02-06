package com.tech.sus_triage_api.domain.unidadesaude;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
}
