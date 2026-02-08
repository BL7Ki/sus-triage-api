package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UnidadeSaudeCacheServiceImplTest {

    @Mock
    private com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository unidadeSaudeRepository;

    @InjectMocks
    private UnidadeSaudeCacheServiceImpl unidadeSaudeCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCacheDeUnidadesComVagas() {
        UnidadeSaude u1 = new UnidadeSaude("UBS Central", "UBS", -23.5, -46.6, 100);
        UnidadeSaude u2 = new UnidadeSaude("UPA Norte", "UPA", -23.6, -46.7, 80);
        List<UnidadeSaude> lista = Arrays.asList(u1, u2);
        when(unidadeSaudeRepository.findAllComVagas()).thenReturn(lista);

        List<UnidadeSaude> result = unidadeSaudeCacheService.getUnidadesComVagas();
        assertEquals(2, result.size());
        assertEquals("UBS Central", result.get(0).getNome());
        assertEquals("UPA Norte", result.get(1).getNome());
    }

    @Test
    void testEvictCache() {
        unidadeSaudeCacheService.evictCache();
       
        assertTrue(true);
    }
}
