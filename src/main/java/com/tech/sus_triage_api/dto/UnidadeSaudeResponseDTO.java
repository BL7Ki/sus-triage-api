package com.tech.sus_triage_api.dto;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;

public record UnidadeSaudeResponseDTO(
        Long id,
        String nome,
        TipoUnidade tipo,
        Double latitude,
        Double longitude,
        Integer capacidadeTotal,
        Integer ocupacaoAtual
) {
    public static UnidadeSaudeResponseDTO fromUnidade(UnidadeSaude unidade) {
        if (unidade == null) {
            return null;
        }
        return new UnidadeSaudeResponseDTO(
                unidade.getId(),
                unidade.getNome(),
                unidade.getTipo(),
                unidade.getLatitude(),
                unidade.getLongitude(),
                unidade.getCapacidadeTotal(),
                unidade.getOcupacaoAtual()
        );
    }
}

