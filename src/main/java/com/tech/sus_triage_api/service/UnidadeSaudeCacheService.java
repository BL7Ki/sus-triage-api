package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import java.util.List;

public interface UnidadeSaudeCacheService {
    List<UnidadeSaude> getUnidadesComVagas();
    void evictCache();
}
