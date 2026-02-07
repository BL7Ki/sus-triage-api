package com.tech.sus_triage_api.controller.paciente.doc;

import com.tech.sus_triage_api.controller.paciente.PacienteRequestDTO;
import com.tech.sus_triage_api.controller.paciente.PacienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PacienteControllerDoc {

    @Operation(summary = "Criar um novo paciente", description = "Cria um novo paciente com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PacienteResponseDTO> criarPaciente(@Valid @RequestBody PacienteRequestDTO pacienteRequestDTO);

    @Operation(summary = "Obter paciente por ID", description = "Obtém os detalhes de um paciente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PacienteResponseDTO> obterPacientePorId(@PathVariable Long id);

}
