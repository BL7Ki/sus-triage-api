package com.tech.sus_triage_api.repository;

import com.tech.sus_triage_api.domain.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriagemRepository extends JpaRepository<Triagem, Long> {
}
