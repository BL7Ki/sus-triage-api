package com.tech.sus_triage_api.domain.triagem;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.TriagemDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    void construtorCompletoFunciona() {
        Paciente paciente = mock(Paciente.class);
        TriagemDTO dto = mock(TriagemDTO.class);
        when(dto.sintomas()).thenReturn("Dor de cabeça");
        when(dto.pressaoSistolica()).thenReturn(120);
        when(dto.pressaoDiastolica()).thenReturn(80);
        when(dto.temperatura()).thenReturn(36.5);
        when(dto.batimentos()).thenReturn(70);
        when(dto.saturacao()).thenReturn(98);
        Triagem triagem = new Triagem(paciente, dto, com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        assertEquals(paciente, triagem.getPaciente());
        assertEquals("Dor de cabeça", triagem.getSintomas());
        assertEquals(120, triagem.getPressaoArterialSistolica());
        assertEquals(80, triagem.getPressaoArterialDiastolica());
        assertEquals(36.5, triagem.getTemperatura());
        assertEquals(70, triagem.getBatimentosPorMinuto());
        assertEquals(98, triagem.getSaturacaoOxigenio());
        assertEquals(com.tech.sus_triage_api.domain.enums.Risco.VERDE, triagem.getRisco());
        assertEquals(com.tech.sus_triage_api.domain.enums.StatusTriagem.PENDENTE_ALOCACAO, triagem.getStatus());
        assertNotNull(triagem.getDataHora());
    }

    @Test
    void marcarComoAlocadaAtualizaUnidadeEStatus() {
        Paciente paciente = mock(Paciente.class);
        TriagemDTO dto = mock(TriagemDTO.class);
        when(dto.sintomas()).thenReturn("Dor");
        when(dto.pressaoSistolica()).thenReturn(120);
        when(dto.pressaoDiastolica()).thenReturn(80);
        when(dto.temperatura()).thenReturn(36.5);
        when(dto.batimentos()).thenReturn(70);
        when(dto.saturacao()).thenReturn(98);
        Triagem triagem = new Triagem(paciente, dto, com.tech.sus_triage_api.domain.enums.Risco.VERDE);
        UnidadeSaude unidade = mock(UnidadeSaude.class);
        triagem.marcarComoAlocada(unidade);
        assertEquals(unidade, triagem.getUnidadeDestino());
        assertEquals(com.tech.sus_triage_api.domain.enums.StatusTriagem.ALOCADO, triagem.getStatus());
    }
}
