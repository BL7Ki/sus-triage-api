package com.tech.sus_triage_api.repository.unidadesaude;

import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UnidadeSaudeRepository extends JpaRepository<UnidadeSaude, Long> {
    
    List<UnidadeSaude> findByOcupacaoAtualLessThanCapacidadeTotalAndTipoIn(List<TipoUnidade> tipos);

    List<UnidadeSaude> findByOcupacaoAtualLessThanCapacidadeTotal();
}
