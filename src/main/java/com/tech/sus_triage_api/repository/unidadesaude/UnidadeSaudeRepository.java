package com.tech.sus_triage_api.repository.unidadesaude;

import com.tech.sus_triage_api.domain.enums.TipoUnidade;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Long> {

    @Query("SELECT u FROM UnidadeSaude u WHERE u.ocupacaoAtual < u.capacidadeTotal AND u.tipo IN :tipos")
    List<UnidadeSaude> findDisponiveisPorTipos(@Param("tipos") List<TipoUnidade> tipos);

    @Query("SELECT u FROM UnidadeSaude u WHERE u.ocupacaoAtual < u.capacidadeTotal")
    List<UnidadeSaude> findComVagasGerais();
}