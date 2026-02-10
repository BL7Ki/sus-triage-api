package com.tech.sus_triage_api.controller.paciente.doc;

import com.tech.sus_triage_api.dto.paciente.PacienteAtualizacaoDTO;
import com.tech.sus_triage_api.dto.paciente.PacienteRequestDTO;
import com.tech.sus_triage_api.dto.paciente.PacienteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Paciente", description = "APIs para gerenciamento de pacientes")
public interface PacienteControllerDoc {

    @Operation(summary = "Criar um novo paciente", description = "Cria um novo paciente com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PacienteResponseDTO> criarPaciente(@Valid @org.springframework.web.bind.annotation.RequestBody PacienteRequestDTO pacienteRequestDTO);

    @Operation(summary = "Obter paciente por ID", description = "Obtém os detalhes de um paciente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PacienteResponseDTO> obterPacientePorId(@PathVariable Long id);

    @Operation(summary = "Atualizar coordenadas do paciente", description = "Atualiza a latitude e longitude de um paciente existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Coordenadas atualizadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PacienteResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<PacienteResponseDTO> atualizarCoordenadas(@Valid @org.springframework.web.bind.annotation.RequestBody PacienteAtualizacaoDTO pacienteAtualizacaoDTO);

    @Operation(summary = "Buscar pacientes por nome", description = "Busca pacientes pelo nome (busca parcial, case-insensitive). Retorna uma lista de pacientes encontrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pacientes encontrados",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PacienteResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Nome vazio ou inválido",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Nenhum paciente encontrado com esse nome",
                    content = @Content(mediaType = "application/json"))
    })
    ResponseEntity<List<PacienteResponseDTO>> buscarPacientesPorNome(@PathVariable String nome);

}
