package com.tech.sus_triage_api.domain.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.TriagemDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TriagemTest {

    @Test
    void construtorEGettersFuncionam() {
        Paciente paciente = mock(Paciente.class);
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "Dor", 120, 80, 36.5, 70, 98);
        Triagem triagem = new Triagem(paciente, dto, Risco.VERDE);
        assertEquals(paciente, triagem.getPaciente());
        assertEquals("Dor", triagem.getSintomas());
        assertEquals(120, triagem.getPressaoArterialSistolica());
        assertEquals(80, triagem.getPressaoArterialDiastolica());
        assertEquals(36.5, triagem.getTemperatura());
        assertEquals(70, triagem.getBatimentosPorMinuto());
        assertEquals(98, triagem.getSaturacaoOxigenio());
        assertEquals(Risco.VERDE, triagem.getRisco());
        assertEquals(StatusTriagem.PENDENTE_ALOCACAO, triagem.getStatus());
        assertNotNull(triagem.getDataHora());
    }

    @Test
    void marcarComoAlocadaAtualizaUnidadeEStatus() {
        Paciente paciente = mock(Paciente.class);
        TriagemDTO dto = new TriagemDTO("Paciente", "123", 0.0, 0.0, "Dor", 120, 80, 36.5, 70, 98);
        Triagem triagem = new Triagem(paciente, dto, Risco.VERDE);
        UnidadeSaude unidade = mock(UnidadeSaude.class);
        triagem.marcarComoAlocada(unidade);
        assertEquals(unidade, triagem.getUnidadeDestino());
        assertEquals(StatusTriagem.ALOCADO, triagem.getStatus());
    }
}
