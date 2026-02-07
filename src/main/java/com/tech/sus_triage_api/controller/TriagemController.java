package com.tech.sus_triage_api.controller;

import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.repository.paciente.PacienteRepository;
import com.tech.sus_triage_api.service.TriagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/triagem")
public class TriagemController {

    private final TriagemService triagemService;
    private final PacienteRepository pacienteRepository;

    public TriagemController(TriagemService triagemService, PacienteRepository pacienteRepository) {
        this.triagemService = triagemService;
        this.pacienteRepository = pacienteRepository;
    }

    @PostMapping
    public ResponseEntity<Triagem> criarTriagem(@RequestBody TriagemDTO dto) {
        Paciente paciente = pacienteRepository.findByCpf(dto.getCpfPaciente())
                .orElseGet(() -> {
                    Paciente novo = new Paciente();
                    novo.setNome(dto.getNomePaciente());
                    novo.setCpf(dto.getCpfPaciente());
                    novo.setLatitude(dto.getLatitude());
                    novo.setLongitude(dto.getLongitude());
                    return pacienteRepository.save(novo);
                });

        Triagem triagem = new Triagem();
        triagem.setPaciente(paciente);
        triagem.setSintomas(dto.getSintomas());
        triagem.setPressaoArterialSistolica(dto.getPressaoSistolica());
        triagem.setPressaoArterialDiastolica(dto.getPressaoDiastolica());
        triagem.setTemperatura(dto.getTemperatura());
        triagem.setBatimentosPorMinuto(dto.getBatimentos());
        triagem.setSaturacaoOxigenio(dto.getSaturacao());

        Triagem triagemRealizada = triagemService.realizarTriagem(triagem);
        return ResponseEntity.ok(triagemRealizada);
    }
}