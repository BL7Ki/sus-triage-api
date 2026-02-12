package com.tech.sus_triage_api.service.strategy;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.dto.TriagemDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProtocoloManchesterStrategyTest {

    private final ProtocoloManchesterStrategy strategy = new ProtocoloManchesterStrategy();

    @Test
    void classificar_retornaVermelhoQuandoSaturacaoBaixaOuBatimentosAltos() {
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "", 120, 80, 36.5, 131, 89);
        assertEquals(Risco.VERMELHO, strategy.classificar(dto));
    }

    @Test
    void classificar_retornaLaranjaQuandoSaturacaoMenor95OuTemperaturaAlta() {
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "", 120, 80, 39.0, 70, 94);
        assertEquals(Risco.LARANJA, strategy.classificar(dto));
    }

    @Test
    void classificar_retornaAmareloQuandoPressaoAltaOuTemperaturaElevada() {
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "", 161, 80, 37.8, 70, 98);
        assertEquals(Risco.AMARELO, strategy.classificar(dto));
    }

    @Test
    void classificar_retornaVerdeQuandoSintomasPresentes() {
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "Dor de cabeça", 120, 80, 36.5, 70, 98);
        assertEquals(Risco.VERDE, strategy.classificar(dto));
    }

    @Test
    void classificar_retornaAzulQuandoSemSintomas() {
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "", 120, 80, 36.5, 70, 98);
        assertEquals(Risco.AZUL, strategy.classificar(dto));
    }
}
