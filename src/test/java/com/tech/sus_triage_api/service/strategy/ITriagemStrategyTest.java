package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.domain.enums.Risco;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ITriagemStrategyTest {
    @Test
    void testInterfaceMethods() {
        ITriagemStrategy strategy = triagem -> Risco.AMARELO;
        // Dados de entrada
        TriagemDTO triagem = new TriagemDTO(
             "João",
        "12345678900",
        -23.55052,
        -46.633308,
        "febre, tosse",
        120,
        80,
        37.5,
        80,
        98
        );
        assertEquals(Risco.AMARELO, strategy.classificar(triagem));
    }
}
