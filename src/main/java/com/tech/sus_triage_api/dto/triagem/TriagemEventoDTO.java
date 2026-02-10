package com.tech.sus_triage_api.dto.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;

public record TriagemEventoDTO(Long triagemId, Risco risco) {
}