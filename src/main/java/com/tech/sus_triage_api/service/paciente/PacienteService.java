package com.tech.sus_triage_api.service.paciente;

import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.errors.SummerException;
import com.tech.sus_triage_api.errors.SummerNotFoundException;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteEntity criarPaciente(PacienteEntity pacienteEntity) {

        logger.info("Consulta para saber se o paciente a ser criado já existe: {}", pacienteEntity);

        Paciente pacienteDomain = pacienteEntity.toDomain();

        // Verificar se já existe um paciente com o mesmo CPF
        boolean pacienteExistente = pacienteRepository.findByCpf(pacienteEntity.getCpf()).isPresent();

        if (pacienteExistente) {
            logger.warn("Paciente com CPF {} já existe. Não será criado.", pacienteEntity.getCpf());
            throw new SummerException("Paciente com CPF " + pacienteEntity.getCpf() + " já existe.");
        }

        // Salvar o paciente no banco de dados
        Paciente pacienteSalvo = pacienteRepository.save(pacienteDomain);

        logger.info("Paciente criado com sucesso: {}", pacienteSalvo);

        // Converter o paciente salvo de volta para PacienteEntity
        return pacienteSalvo.toEntity();
    }

    public PacienteEntity obterPacientePorId(Long id) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Paciente com ID " + id + " não encontrado."));

        logger.info("Paciente encontrado: {}", paciente);

        return paciente.toEntity();
    }
}
