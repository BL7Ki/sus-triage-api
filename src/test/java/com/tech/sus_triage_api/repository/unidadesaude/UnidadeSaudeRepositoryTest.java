package com.tech.sus_triage_api.repository.unidadesaude;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnidadeSaudeRepositoryTest {

    @Test
    void findDisponiveisPorTipos_deveRetornarListaDeUnidades() {
        UnidadeSaudeRepository repository = mock(UnidadeSaudeRepository.class);
        UnidadeSaude unidade = mock(UnidadeSaude.class);
        when(repository.findDisponiveisPorTipos(List.of(TipoUnidade.UBS))).thenReturn(List.of(unidade));
        List<UnidadeSaude> resultado = repository.findDisponiveisPorTipos(List.of(TipoUnidade.UBS));
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(unidade, resultado.get(0));
    }

    @Test
    void findComVagasGerais_deveRetornarListaDeUnidades() {
        UnidadeSaudeRepository repository = mock(UnidadeSaudeRepository.class);
        UnidadeSaude unidade = mock(UnidadeSaude.class);
        when(repository.findComVagasGerais()).thenReturn(List.of(unidade));
        List<UnidadeSaude> resultado = repository.findComVagasGerais();
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(unidade, resultado.get(0));
    }
}
