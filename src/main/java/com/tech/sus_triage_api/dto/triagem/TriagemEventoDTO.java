package com.tech.sus_triage_api.dto.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;
import java.io.Serializable;

public class TriagemEventoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long triagemId;
    private Risco risco;

    // Construtor padrão necessário para deserialização
    public TriagemEventoDTO() {
    }

    public TriagemEventoDTO(Long triagemId, Risco risco) {
        this.triagemId = triagemId;
        this.risco = risco;
    }

    public Long getTriagemId() {
        return triagemId;
    }

    public void setTriagemId(Long triagemId) {
        this.triagemId = triagemId;
    }

    public Risco getRisco() {
        return risco;
    }

    public void setRisco(Risco risco) {
        this.risco = risco;
    }

    // Métodos do record para manter compatibilidade
    public Long triagemId() {
        return triagemId;
    }

    public Risco risco() {
        return risco;
    }
}