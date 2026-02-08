package com.tech.sus_triage_api.repository.triagem;

import com.tech.sus_triage_api.domain.triagem.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriagemRepository extends JpaRepository<Triagem, Long> {
    List<Triagem> findByStatus(StatusTriagem status);
}
