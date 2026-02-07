package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.service.rabbitmq.TriagemProducer;
import com.tech.sus_triage_api.service.strategy.ITriagemStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    @Autowired
    private TriagemRepository triagemRepository;

    @Autowired
    private ITriagemStrategy triagemStrategy;

    @Autowired
    private TriagemProducer triagemProducer;

    public Triagem realizarTriagem(Triagem triagem) {
        var riscoCalculado = triagemStrategy.classificar(triagem);
        triagem.setRisco(riscoCalculado);
        Triagem salva = triagemRepository.save(triagem);
        triagemProducer.enviarParaFila(salva);
        return salva;
    }
}
