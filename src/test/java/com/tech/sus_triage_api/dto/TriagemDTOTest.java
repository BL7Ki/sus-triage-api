package com.tech.sus_triage_api.dto;

import com.tech.sus_triage_api.dto.triagem.TriagemDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TriagemDTOTest {

    @Test
    void construtorEGettersFuncionam() {
        TriagemDTO dto = new TriagemDTO(
                "Carlos",
                "12345678900",
                10.0,
                20.0,
                "Dor de cabeça",
                120,
                80,
                36.5,
                70,
                98
        );
        assertEquals("Carlos", dto.nomePaciente());
        assertEquals("12345678900", dto.cpfPaciente());
        assertEquals(10.0, dto.latitude());
        assertEquals(20.0, dto.longitude());
        assertEquals("Dor de cabeça", dto.sintomas());
        assertEquals(120, dto.pressaoSistolica());
        assertEquals(80, dto.pressaoDiastolica());
        assertEquals(36.5, dto.temperatura());
        assertEquals(70, dto.batimentos());
        assertEquals(98, dto.saturacao());
    }
}
