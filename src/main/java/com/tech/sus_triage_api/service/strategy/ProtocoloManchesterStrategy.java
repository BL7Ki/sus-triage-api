package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.domain.Risco;
import com.tech.sus_triage_api.domain.Triagem;
import org.springframework.stereotype.Component;

@Component
public class ProtocoloManchesterStrategy implements ITriagemStrategy {

    @Override
    public Risco classificar(Triagem t) {
        if (t.getSaturacaoOxigenio() < 90 || t.getBatimentosPorMinuto() > 130) {
            return Risco.VERMELHO;
        }

        if (t.getSaturacaoOxigenio() < 95 || t.getTemperatura() >= 39.0) {
            return Risco.LARANJA;
        }

        if (t.getPressaoArterialSistolica() > 160 || t.getTemperatura() >= 37.8) {
            return Risco.AMARELO;
        }

        return Risco.VERDE;
    }
}
