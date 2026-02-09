package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.dto.TriagemDTO;

public interface ITriagemStrategy {
    Risco classificar(TriagemDTO dados);
}