package com.tech.sus_triage_api.domain.enums;

// baseado no protocolo de Manchester
public enum Risco {
    AZUL(1),      // Não Urgente
    VERDE(2),     // Pouco Urgente
    AMARELO(3),   // Urgente
    LARANJA(4),   // Muito Urgente
    VERMELHO(5);  // Emergência

    private final int nivel;

    Risco(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
