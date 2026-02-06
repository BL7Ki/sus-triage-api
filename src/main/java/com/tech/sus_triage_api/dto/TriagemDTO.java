package com.tech.sus_triage_api.dto;

import lombok.Data;

@Data
public class TriagemDTO {
    private String nomePaciente;
    private String cpfPaciente;
    private Double latitude;
    private Double longitude;

    private String sintomas;
    private Integer pressaoSistolica;
    private Integer pressaoDiastolica;
    private Double temperatura;
    private Integer batimentos;
    private Integer saturacao;
}
