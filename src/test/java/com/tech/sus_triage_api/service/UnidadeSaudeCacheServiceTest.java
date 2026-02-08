package com.tech.sus_triage_api.service;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.lang.reflect.Method;

class UnidadeSaudeCacheServiceTest {
    @Test
    void shouldHaveRequiredMethods() throws Exception {
        Method getUnidadesComVagas = UnidadeSaudeCacheService.class.getMethod("getUnidadesComVagas");
        Method evictCache = UnidadeSaudeCacheService.class.getMethod("evictCache");
        assertThat(getUnidadesComVagas).isNotNull();
        assertThat(evictCache).isNotNull();
    }
}
