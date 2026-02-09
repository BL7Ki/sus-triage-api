package com.tech.sus_triage_api.domain.unidadesaude;


import org.junit.jupiter.api.Test;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;

import static org.junit.jupiter.api.Assertions.*;

class UnidadeSaudeTest {
    @Test
    void testUnidadeSaudeFields() {

        UnidadeSaude unidade = new UnidadeSaude("UBS Central", TipoUnidade.UBS , -23.55052, -46.633308, 100);
        
        assertEquals("UBS Central", unidade.getNome());
        assertEquals(TipoUnidade.UBS, unidade.getTipo());
        assertEquals(-23.55052, unidade.getLatitude());
        assertEquals(-46.633308, unidade.getLongitude());
        assertEquals(100, unidade.getCapacidadeTotal());
    }
}
