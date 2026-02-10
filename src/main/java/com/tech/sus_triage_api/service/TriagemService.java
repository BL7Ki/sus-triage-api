package com.tech.sus_triage_api.service;

import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemDTO;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.service.rabbitmq.TriagemProducer;
import com.tech.sus_triage_api.service.strategy.ITriagemStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    private final TriagemRepository triagemRepository;
    private final PacienteRepository pacienteRepository;
    private final ITriagemStrategy triagemStrategy;
    private final TriagemProducer triagemProducer;

    private final Logger logger = LoggerFactory.getLogger(TriagemService.class);

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

        logger.info("Iniciando triagem para paciente nome: {}", dto.nomePaciente());

        Paciente paciente = pacienteRepository.findByCpf(dto.cpfPaciente())
                .orElseGet(() -> {
                    logger.info("Paciente não encontrado, criando novo paciente com CPF: {}", dto.cpfPaciente());
                    Paciente novo = new Paciente(dto.nomePaciente(), dto.cpfPaciente());
                    novo.atualizarCoordenadas(dto.latitude(), dto.longitude());
                    return pacienteRepository.save(novo);
                });

        Risco risco = triagemStrategy.classificar(dto);

        Triagem triagem = new Triagem(paciente, dto, risco);

        Triagem salva = triagemRepository.save(triagem);

        logger.info("Triagem realizada com sucesso para paciente nome: {}, risco classificado como: {}", dto.nomePaciente(), risco);

        triagemProducer.enviarParaFila(salva);

        logger.info("Evento de triagem enviado para fila RabbitMQ para paciente nome: {}, risco classificado como: {}", dto.nomePaciente(), risco);

        return salva;
    }
}
