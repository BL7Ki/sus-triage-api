package com.tech.sus_triage_api.domain.unidadesaude;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeTest {

    @Test
    void construtorEGettersFuncionam() {
        UnidadeSaude unidade = new UnidadeSaude("UBS Central", TipoUnidade.UBS, 10.0, 20.0, 5);
        assertEquals("UBS Central", unidade.getNome());
        assertEquals(TipoUnidade.UBS, unidade.getTipo());
        assertEquals(10.0, unidade.getLatitude());
        assertEquals(20.0, unidade.getLongitude());
        assertEquals(5, unidade.getCapacidadeTotal());
    }

    @Test
    void temVagaRetornaTrueQuandoOcupacaoMenorQueCapacidade() {
        UnidadeSaude unidade = new UnidadeSaude("UBS Central", TipoUnidade.UBS, 10.0, 20.0, 5);
        for (int i = 0; i < 3; i++) {
            unidade.adicionarPaciente(); // Ocupação = 3
        }
     
        assertTrue(unidade.temVaga());
    }

    @Test
    void temVagaRetornaFalseQuandoOcupacaoIgualCapacidade() {
        UnidadeSaude unidade = new UnidadeSaude("UBS Central", TipoUnidade.UBS, 10.0, 20.0, 5);
        for (int i = 0; i < 5; i++) {
            unidade.adicionarPaciente(); // Ocupação = 5
        }
        assertFalse(unidade.temVaga());
    }

    @Test
    void adicionarPacienteIncrementaOcupacao() {
        UnidadeSaude unidade = new UnidadeSaude("UBS Central", TipoUnidade.UBS, 10.0, 20.0, 5);
        for (int i = 0; i < 2; i++) {
            unidade.adicionarPaciente(); // Ocupação = 2
        }
        unidade.adicionarPaciente();
        assertEquals(3, unidade.getOcupacaoAtual());
    }

    @Test
    void adicionarPacienteLancaExcecaoQuandoSemVaga() {
        UnidadeSaude unidade = new UnidadeSaude("UBS Central", TipoUnidade.UBS, 10.0, 20.0, 2);
        for (int i = 0; i < 2; i++) {
            unidade.adicionarPaciente(); // Ocupação = 2
        }
        assertThrows(IllegalStateException.class, unidade::adicionarPaciente);
    }
}
