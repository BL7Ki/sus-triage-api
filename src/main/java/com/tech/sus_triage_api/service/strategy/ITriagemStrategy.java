package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.domain.Triagem;
import com.tech.sus_triage_api.domain.Risco;

public interface ITriagemStrategy {
    Risco classificar(Triagem dadosTriagem);
}
