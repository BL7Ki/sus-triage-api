package com.tech.sus_triage_api.repository.triagem;

import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TriagemRepositoryTest {

    @Test
    void findByStatus_deveRetornarListaDeTriagem() {
        TriagemRepository repository = mock(TriagemRepository.class);
        Triagem triagem = mock(Triagem.class);
        when(repository.findByStatus(StatusTriagem.PENDENTE_ALOCACAO)).thenReturn(List.of(triagem));
        List<Triagem> resultado = repository.findByStatus(StatusTriagem.PENDENTE_ALOCACAO);
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(triagem, resultado.get(0));
    }
}
