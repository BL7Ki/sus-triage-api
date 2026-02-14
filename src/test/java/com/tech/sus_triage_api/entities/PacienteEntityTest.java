package com.tech.sus_triage_api.entities;

import com.tech.sus_triage_api.domain.paciente.Paciente;
import com.tech.sus_triage_api.dto.paciente.PacienteResponseDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PacienteEntityTest {

    @Test
    void construtorEGettersFuncionam() {
        PacienteEntity entity = new PacienteEntity(1L, "Maria", "123.456.789-00", 10.0, 20.0);
        assertEquals(1L, entity.getId());
        assertEquals("Maria", entity.getNome());
        assertEquals("123.456.789-00", entity.getCpf());
        assertEquals(10.0, entity.getLatitude());
        assertEquals(20.0, entity.getLongitude());
    }

    @Test
    void toDomainRetornaPacienteCorreto() {
        PacienteEntity entity = new PacienteEntity(2L, "Joao", "987.654.321-00", 30.0, 40.0);
        Paciente paciente = entity.toDomain();
        assertEquals(2L, paciente.getId());
        assertEquals("Joao", paciente.getNome());
        assertEquals("987.654.321-00", paciente.getCpf());
        assertEquals(30.0, paciente.getLatitude());
        assertEquals(40.0, paciente.getLongitude());
    }

    @Test
    void toResponseDTORetornaDTOCorreto() {
        PacienteEntity entity = new PacienteEntity(3L, "Ana", "111.222.333-44", 50.0, 60.0);
        PacienteResponseDTO dto = entity.toResponseDTO();
        assertEquals("3", dto.id());
        assertEquals("Ana", dto.nome());
        assertEquals("111.222.333-44", dto.cpf());
        assertEquals(50.0, dto.latitude());
        assertEquals(60.0, dto.longitude());
    }
}
