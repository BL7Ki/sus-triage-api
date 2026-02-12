package com.tech.sus_triage_api.entities;

import com.tech.sus_triage_api.controller.paciente.PacienteResponseDTO;
import com.tech.sus_triage_api.domain.paciente.Paciente;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteEntityTest {

    @Test
    void construtorEGettersFuncionam() {
        PacienteEntity entity = new PacienteEntity(1L, "Maria", "12345678900", 10.0, 20.0);
        assertEquals(1L, entity.getId());
        assertEquals("Maria", entity.getNome());
        assertEquals("12345678900", entity.getCpf());
        assertEquals(10.0, entity.getLatitude());
        assertEquals(20.0, entity.getLongitude());
    }

    @Test
    void toDomainRetornaPacienteCorreto() {
        PacienteEntity entity = new PacienteEntity(2L, "Joao", "98765432100", 30.0, 40.0);
        Paciente paciente = entity.toDomain();
        assertEquals(2L, paciente.getId());
        assertEquals("Joao", paciente.getNome());
        assertEquals("98765432100", paciente.getCpf());
        assertEquals(30.0, paciente.getLatitude());
        assertEquals(40.0, paciente.getLongitude());
    }

    @Test
    void toResponseDTORetornaDTOCorreto() {
        PacienteEntity entity = new PacienteEntity(3L, "Ana", "11122233344", 50.0, 60.0);
        PacienteResponseDTO dto = entity.toResponseDTO();
        assertEquals("3", dto.id());
        assertEquals("Ana", dto.nome());
        assertEquals("11122233344", dto.cpf());
        assertEquals(50.0, dto.latitude());
        assertEquals(60.0, dto.longitude());
    }
}
