package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.dto.TriagemDTO;
import org.springframework.stereotype.Component;

@Component
public class ProtocoloManchesterStrategy implements ITriagemStrategy {
    @Override
    public Risco classificar(TriagemDTO d) {
        if (d.saturacao() < 90 || d.batimentos() > 130) return Risco.VERMELHO;
        if (d.saturacao() < 95 || d.temperatura() >= 39.0) return Risco.LARANJA;
        if (d.pressaoSistolica() > 160 || d.temperatura() >= 37.8) return Risco.AMARELO;
        if (d.sintomas() != null && !d.sintomas().isBlank()) {
            return Risco.VERDE;
        }
        return Risco.AZUL;
    }
}
