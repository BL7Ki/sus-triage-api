package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.Triagem;
import com.tech.sus_triage_api.repository.TriagemRepository;
import com.tech.sus_triage_api.service.strategy.ITriagemStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    @Autowired
    private TriagemRepository triagemRepository;

    @Autowired
    private ITriagemStrategy triagemStrategy;

    public Triagem realizarTriagem(Triagem triagem) {
        var riscoCalculado = triagemStrategy.classificar(triagem);
        triagem.setRisco(riscoCalculado);

        // Salva no banco (Status PENDENTE_ALOCACAO vem do @PrePersist da entidade)
        return triagemRepository.save(triagem);

        // TODO: Próximo passo -> Enviar para RabbitMQ
    }
}
