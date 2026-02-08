package com.tech.sus_triage_api.controller;

import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.service.UnidadeSaudeCacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/unidades-saude")
public class UnidadeSaudeController {

    private final UnidadeSaudeCacheService unidadeSaudeCacheService;

    public UnidadeSaudeController(UnidadeSaudeCacheService unidadeSaudeCacheService) {
        this.unidadeSaudeCacheService = unidadeSaudeCacheService;
    }

    @GetMapping("/com-vagas")
    public List<UnidadeSaude> listarUnidadesComVagas() {
        return unidadeSaudeCacheService.getUnidadesComVagas();
    }
}
