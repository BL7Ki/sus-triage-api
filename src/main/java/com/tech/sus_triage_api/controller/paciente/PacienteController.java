package com.tech.sus_triage_api.controller.paciente;

import com.tech.sus_triage_api.controller.paciente.doc.PacienteControllerDoc;
import com.tech.sus_triage_api.entities.PacienteEntity;
import com.tech.sus_triage_api.service.paciente.PacienteService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
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
    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponseDTO> obterPacientePorId(@PathVariable Long id) {
        // Implementar lógica para obter paciente por ID

        logger.info("Obtendo paciente com ID: {}", id);

        PacienteEntity pacienteEntity = pacienteService.obterPacientePorId(id);

        PacienteResponseDTO pacienteResponseDTO = pacienteEntity.toResponseDTO();

        logger.info("Paciente obtido com sucesso response: {}", pacienteResponseDTO);

        return ResponseEntity.ok(pacienteResponseDTO);

    }

}
