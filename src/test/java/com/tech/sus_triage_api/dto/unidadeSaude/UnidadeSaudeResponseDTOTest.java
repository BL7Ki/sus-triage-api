package com.tech.sus_triage_api.dto.unidadeSaude;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeResponseDTOTest {
    @Test
    void fromUnidade_nullInput_returnsNull() {
        assertNull(UnidadeSaudeResponseDTO.fromUnidade(null));
    }

    @Test
    void fromUnidade_validInput_returnsDTOWithSameValues() {
        UnidadeSaude unidade = new UnidadeSaude("Unidade Teste", TipoUnidade.HOSPITAL, -23.5, -46.6, 100);
        try {
            java.lang.reflect.Field field = UnidadeSaude.class.getDeclaredField("ocupacaoAtual");
            field.setAccessible(true);
            field.set(unidade, 50);
        } catch (Exception e) {
            fail("Erro ao setar campo ocupacaoAtual via reflexão: " + e.getMessage());
        }

        UnidadeSaudeResponseDTO dto = UnidadeSaudeResponseDTO.fromUnidade(unidade);
        assertNotNull(dto);
        assertNull(dto.id()); // id não é setado
        assertEquals("Unidade Teste", dto.nome());
        assertEquals(TipoUnidade.HOSPITAL, dto.tipo());
        assertEquals(-23.5, dto.latitude());
        assertEquals(-46.6, dto.longitude());
        assertEquals(100, dto.capacidadeTotal());
        assertEquals(50, dto.ocupacaoAtual());
    }
}
