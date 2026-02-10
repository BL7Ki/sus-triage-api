package com.tech.sus_triage_api.controller.paciente;

import com.tech.sus_triage_api.controller.paciente.doc.PacienteControllerDoc;
import com.tech.sus_triage_api.dto.paciente.PacienteAtualizacaoDTO;
import com.tech.sus_triage_api.dto.paciente.PacienteRequestDTO;
import com.tech.sus_triage_api.dto.paciente.PacienteResponseDTO;
import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.service.paciente.PacienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/pacientes")
@Tag(name = "Paciente", description = "APIs para gerenciamento de pacientes")
public class PacienteController implements PacienteControllerDoc {

    private Logger logger = LoggerFactory.getLogger(PacienteController.class);

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Override
    @PostMapping
    public ResponseEntity<PacienteResponseDTO> criarPaciente(
            @Valid @RequestBody PacienteRequestDTO pacienteRequestDTO
    ) {

        logger.info("Criando paciente request: {}", pacienteRequestDTO);

        PacienteEntity pacienteEntity = pacienteRequestDTO.toEntity();


        PacienteEntity pacienteCriado = pacienteService.criarPaciente(pacienteEntity);

        PacienteResponseDTO pacienteResponseDTO = pacienteCriado.toResponseDTO();

        logger.info("Paciente criado com sucesso response: {}", pacienteResponseDTO);

        URI location = URI.create("/pacientes/" + pacienteResponseDTO.id());

        return ResponseEntity.created(location).body(pacienteResponseDTO);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity<PacienteResponseDTO> obterPacientePorId(@PathVariable Long id) {
        // Implementar lógica para obter paciente por ID

        logger.info("Obtendo paciente com ID: {}", id);

        PacienteEntity pacienteEntity = pacienteService.obterPacientePorId(id);

        PacienteResponseDTO pacienteResponseDTO = pacienteEntity.toResponseDTO();

        logger.info("Paciente obtido com sucesso response: {}", pacienteResponseDTO);

        return ResponseEntity.ok(pacienteResponseDTO);

    }

    @Override
    @PutMapping
    public ResponseEntity<PacienteResponseDTO> atualizarCoordenadas(
            @Valid @RequestBody PacienteAtualizacaoDTO pacienteAtualizacaoDTO
    ) {
        // Implementar lógica para atualizar coordenadas do paciente

        logger.info("Atualizando coordenadas do paciente com ID: {}, latitude: {}, longitude: {}", pacienteAtualizacaoDTO.id(), pacienteAtualizacaoDTO.latitude(), pacienteAtualizacaoDTO.longitude());

        PacienteEntity pacienteEntity = pacienteService.atualizarCoordenadas(pacienteAtualizacaoDTO.id(), pacienteAtualizacaoDTO.latitude(), pacienteAtualizacaoDTO.longitude());

        PacienteResponseDTO pacienteResponseDTO = pacienteEntity.toResponseDTO();

        logger.info("Coordenadas do paciente atualizadas com sucesso response: {}", pacienteResponseDTO);

        return ResponseEntity.ok(pacienteResponseDTO);
    }

    @Override
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<PacienteResponseDTO>> buscarPacientesPorNome(@PathVariable String nome) {

        logger.info("Buscando pacientes com nome contendo: {}", nome);

        List<PacienteEntity> pacientes = pacienteService.buscarPacientesPorNome(nome);

        List<PacienteResponseDTO> pacientesResponseDTO = pacientes.stream()
                .map(PacienteEntity::toResponseDTO)
                .toList();

        logger.info("Pacientes encontrados: {}", pacientesResponseDTO.size());

        return ResponseEntity.ok(pacientesResponseDTO);
    }

}
