package com.tech.sus_triage_api.repository.unidadesaude;

import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Long> {

    // Busca unidades onde a ocupação é menor que a capacidade
    @Query("SELECT u FROM UnidadeSaude u WHERE u.ocupacaoAtual < u.capacidadeTotal")
    List<UnidadeSaude> findAllComVagas();
}
