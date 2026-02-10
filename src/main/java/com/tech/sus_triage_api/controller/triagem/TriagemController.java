package com.tech.sus_triage_api.controller.triagem;

import com.tech.sus_triage_api.controller.triagem.doc.TriagemControllerDoc;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.triagem.TriagemDTO;
import com.tech.sus_triage_api.service.TriagemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/triagem")
public class TriagemController implements TriagemControllerDoc {

    private final TriagemService triagemService;

    private final Logger logger = LoggerFactory.getLogger(TriagemController.class);

    public TriagemController(TriagemService triagemService) {
        this.triagemService = triagemService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Triagem> criarTriagem(@Valid @RequestBody TriagemDTO dto) {

        logger.info("Recebendo solicitação de triagem para paciente nome: {}", dto.nomePaciente());
        Triagem triagemRealizada = triagemService.realizarTriagem(dto);

        logger.info("Triagem processada com sucesso para paciente nome: {}, risco classificado como: {}", dto.nomePaciente(), triagemRealizada.getRisco());
        return ResponseEntity.ok(triagemRealizada);
    }
}