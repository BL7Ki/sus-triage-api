package com.tech.sus_triage_api.controller.triagem.doc;

import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Triagem", description = "APIs para gerenciamento de triagens de pacientes")
public interface TriagemControllerDoc {

    @Operation(
            summary = "Realizar triagem de paciente",
            description = "Realiza a triagem de um paciente com base nos sinais vitais e sintomas fornecidos. " +
                    "O sistema classifica automaticamente o nível de risco (Vermelho, Laranja, Amarelo ou Verde) " +
                    "e aloca para a unidade de saúde mais próxima."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Triagem realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Triagem.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida - dados do paciente ou sinais vitais inválidos",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Paciente não encontrado ou nenhuma unidade de saúde disponível",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(mediaType = "application/json")
            )
    })
    ResponseEntity<Triagem> criarTriagem(@RequestBody TriagemDTO dto);
}
