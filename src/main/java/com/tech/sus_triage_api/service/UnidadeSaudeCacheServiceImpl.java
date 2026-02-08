package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeSaudeCacheServiceImpl implements UnidadeSaudeCacheService {

    private final UnidadeSaudeRepository unidadeSaudeRepository;

    public UnidadeSaudeCacheServiceImpl(UnidadeSaudeRepository unidadeSaudeRepository) {
        this.unidadeSaudeRepository = unidadeSaudeRepository;
    }

    @Override
    @Cacheable(value = "unidadesComVagas")
    public List<UnidadeSaude> getUnidadesComVagas() {
        return unidadeSaudeRepository.findAllComVagas();
    }

    @Override
    @CacheEvict(value = "unidadesComVagas", allEntries = true)
    public void evictCache() {
        // Evict cache
    }
}
