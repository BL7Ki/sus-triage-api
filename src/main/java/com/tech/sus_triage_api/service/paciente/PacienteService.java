package com.tech.sus_triage_api.service.paciente;

import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.errors.SummerException;
import com.tech.sus_triage_api.errors.SummerNotFoundException;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteEntity criarPaciente(PacienteEntity pacienteEntity) {

        logger.info("Criando paciente: {}", pacienteEntity);

        if (pacienteRepository.findByCpf(pacienteEntity.getCpf()).isPresent()) {
            logger.warn("CPF já existente: {}", pacienteEntity.getCpf());
            throw new SummerException("Já existe um paciente com o CPF: " + pacienteEntity.getCpf());
        }

        Paciente paciente = pacienteEntity.toDomain();

        Paciente pacienteCriado = pacienteRepository.save(paciente);

        logger.info("Paciente criado com sucesso: {}", pacienteCriado);

        return pacienteCriado.toEntity();
    }

    public PacienteEntity atualizarCoordenadas(Long id, Double latitude, Double longitude) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Paciente com ID " + id + " não encontrado."));

        logger.info("Paciente encontrado para atualização de coordenadas: {}", paciente);

        paciente.atualizarCoordenadas(latitude, longitude);

        Paciente pacienteAtualizado = pacienteRepository.save(paciente);

        logger.info("Paciente atualizado com sucesso: {}", pacienteAtualizado);

        return pacienteAtualizado.toEntity();
    }

    public PacienteEntity obterPacientePorId(Long id) {

        logger.info("Buscando paciente por ID: {}", id);

        return pacienteRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Paciente com ID " + id + " não encontrado."))
                .toEntity();
    }

    public List<PacienteEntity> buscarPacientesPorNome(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            logger.warn("Nome vazio fornecido para busca");
            throw new SummerException("O nome do paciente não pode ser vazio.");
        }

        logger.info("Buscando pacientes com nome contendo: {}", nome);

        List<Paciente> pacientes = pacienteRepository.buscarPorNome(nome);

        if (pacientes.isEmpty()) {
            logger.info("Nenhum paciente encontrado com nome: {}", nome);
            throw new SummerNotFoundException("Nenhum paciente encontrado com o nome: " + nome);
        }

        logger.info("Pacientes encontrados: {}", pacientes.size());

        return pacientes.stream()
                .map(Paciente::toEntity)
                .toList();
    }
}
