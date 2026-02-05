package com.tech.sus_triage_api.domain;

public enum StatusTriagem {
    PENDENTE_ALOCACAO, // Triagem feita, aguardando vaga
    ALOCADO,           // Vaga encontrada, paciente encaminhado
    FINALIZADO         // Atendimento concluído
}
