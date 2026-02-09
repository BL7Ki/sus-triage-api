package com.tech.sus_triage_api.controller.triagem;

import com.tech.sus_triage_api.controller.triagem.doc.TriagemControllerDoc;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.dto.TriagemDTO;
import com.tech.sus_triage_api.service.TriagemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/triagem")
public class TriagemController implements TriagemControllerDoc {

    private final TriagemService triagemService;

    public TriagemController(TriagemService triagemService) {
        this.triagemService = triagemService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Triagem> criarTriagem(@RequestBody TriagemDTO dto) {
        Triagem triagemRealizada = triagemService.realizarTriagem(dto);
        return ResponseEntity.ok(triagemRealizada);
    }
}