package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.domain.enums.Risco;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProtocoloManchesterStrategyTest {
    @Test
    void testClassifyRiscoVerde() {
        ProtocoloManchesterStrategy strategy = new ProtocoloManchesterStrategy();
        // Dados de entrada para classificar como VERDE
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
            98);
        Risco risco = strategy.classificar(triagem);
        assertEquals(Risco.VERDE, risco);
    }

    @Test
    void testClassifyRiscoVermelho() {
        ProtocoloManchesterStrategy strategy = new ProtocoloManchesterStrategy();
        // Dados de entrada para classificar como VERMELHO    
        TriagemDTO triagem = new TriagemDTO(
            "Maria",
            "98765432100",
            -23.55052,
            -46.633308,
            "sintoma grave",
            120,
            80,
            37.5,
            140, // batimentos > 130
            85   // saturacao < 90
        );
        Risco risco = strategy.classificar(triagem);
        assertEquals(Risco.VERMELHO, risco);
    }

    @Test
    void testClassifyRiscoLaranja() {
        ProtocoloManchesterStrategy strategy = new ProtocoloManchesterStrategy();
        // Dados de entrada para classificar como LARANJA
        TriagemDTO triagem = new TriagemDTO(
            "Carlos",
            "11122233344",
            -23.55052,
            -46.633308,
            "febre alta",
            120,
            80,
            39.5, // temperatura >= 39.0
            80,
            94   // saturacao < 95
        );
        Risco risco = strategy.classificar(triagem);
        assertEquals(Risco.LARANJA, risco);
    }

    @Test
    void testClassifyRiscoAmarelo() {
        ProtocoloManchesterStrategy strategy = new ProtocoloManchesterStrategy();
        // Dados de entrada para classificar como AMARELO
        TriagemDTO triagem = new TriagemDTO(
            "Ana",
            "55566677788",
            -23.55052,
            -46.633308,
            "sintoma moderado",
            170, // pressaoSistolica > 160
            80,
            38.0, // temperatura >= 37.8
            80,
            98
        );
        Risco risco = strategy.classificar(triagem);
        assertEquals(Risco.AMARELO, risco);
    }

    @Test
    void testClassifyRiscoAzul() {
        ProtocoloManchesterStrategy strategy = new ProtocoloManchesterStrategy();
        // Dados de entrada para classificar como AZUL
        TriagemDTO triagem = new TriagemDTO(
            "Lucas",
            "99988877766",
            -23.55052,
            -46.633308,
            "",
            110,
            70,
            36.0,
            70,
            99
        );
        Risco risco = strategy.classificar(triagem);
        assertEquals(Risco.AZUL, risco);
    }
}
