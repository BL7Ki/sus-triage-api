package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.service.rabbitmq.TriagemProducer;
import com.tech.sus_triage_api.service.strategy.ITriagemStrategy;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    private final TriagemRepository triagemRepository;
    private final PacienteRepository pacienteRepository;
    private final ITriagemStrategy triagemStrategy;
    private final TriagemProducer triagemProducer;

    public TriagemService(TriagemRepository triagemRepository,
                          PacienteRepository pacienteRepository,
                          ITriagemStrategy triagemStrategy,
                          TriagemProducer triagemProducer) {
        this.triagemRepository = triagemRepository;
        this.pacienteRepository = pacienteRepository;
        this.triagemStrategy = triagemStrategy;
        this.triagemProducer = triagemProducer;
    }

    @Transactional
    public Triagem realizarTriagem(TriagemDTO dto) {
        // 1. Lógica do Paciente movida para o Service
        Paciente paciente = pacienteRepository.findByCpf(dto.cpfPaciente())
                .orElseGet(() -> {
                    Paciente novo = new Paciente(dto.nomePaciente(), dto.cpfPaciente());
                    novo.atualizarCoordenadas(dto.latitude(), dto.longitude());
                    return pacienteRepository.save(novo);
                });

        // 2. Calcula o risco usando o DTO
        Risco risco = triagemStrategy.classificar(dto);

        // 3. Cria a entidade com o estado completo
        Triagem triagem = new Triagem(paciente, dto, risco);

        Triagem salva = triagemRepository.save(triagem);
        triagemProducer.enviarParaFila(salva);

        return salva;
    }
}
