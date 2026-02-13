package com.tech.sus_triage_api.dto.triagem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tech.sus_triage_api.domain.enums.Risco;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.unidadeSaude.UnidadeSaudeResponseDTO;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TriagemResponseDTO(
        Long id,
        String nomePaciente,
        String cpfPaciente,
        Risco risco,
        StatusTriagem status,
        LocalDateTime dataHora,
        String mensagem,
        String urlConsulta,
        UnidadeSaudeResponseDTO unidadeDestino
) {
    public static TriagemResponseDTO fromTriagem(Triagem triagem) {
        String mensagem = triagem.getStatus() == StatusTriagem.PENDENTE_ALOCACAO
            ? "Triagem registrada com sucesso. A alocação da unidade de saúde está sendo processada"
            : "Triagem processada com sucesso.";

        String urlConsulta = "/api/triagem/" + triagem.getId();

        return new TriagemResponseDTO(
            triagem.getId(),
            triagem.getPaciente().getNome(),
            triagem.getPaciente().getCpf(),
            triagem.getRisco(),
            triagem.getStatus(),
            triagem.getDataHora(),
            mensagem,
            urlConsulta,
            UnidadeSaudeResponseDTO.fromUnidade(triagem.getUnidadeDestino())
        );
    }
}
