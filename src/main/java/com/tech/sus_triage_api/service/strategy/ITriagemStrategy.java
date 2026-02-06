package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.domain.enums.Risco;

public interface ITriagemStrategy {
    Risco classificar(Triagem dadosTriagem);
}
