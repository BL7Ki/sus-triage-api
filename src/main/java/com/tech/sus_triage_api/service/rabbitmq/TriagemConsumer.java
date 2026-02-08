package com.tech.sus_triage_api.service.rabbitmq;

import com.tech.sus_triage_api.config.RabbitMQConfig;
import com.tech.sus_triage_api.domain.enums.StatusTriagem;
import com.tech.sus_triage_api.domain.triagem.Triagem;
import com.tech.sus_triage_api.domain.unidadesaude.UnidadeSaude;
import com.tech.sus_triage_api.dto.TriagemEventoDTO;
import com.tech.sus_triage_api.repository.triagem.TriagemRepository;
import com.tech.sus_triage_api.repository.unidadesaude.UnidadeSaudeRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class TriagemConsumer {

    private final TriagemRepository triagemRepository;
    private final UnidadeSaudeRepository unidadeSaudeRepository;

    public TriagemConsumer(TriagemRepository triagemRepository,
                           UnidadeSaudeRepository unidadeSaudeRepository) {
        this.triagemRepository = triagemRepository;
        this.unidadeSaudeRepository = unidadeSaudeRepository;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRIAGEM)
    @Transactional
    public void processarAlocacao(TriagemEventoDTO evento) {
        System.out.println(">>> Listener recebendo Triagem ID: " + evento.triagemId() + " com Risco: " + evento.risco());

        Triagem triagem = triagemRepository.findById(evento.triagemId())
                .orElseThrow(() -> new RuntimeException("Triagem não encontrada: " + evento.triagemId()));

        List<UnidadeSaude> unidadesDisponiveis = unidadeSaudeRepository.findByOcupacaoAtualLessThanCapacidadeTotal();

        if (unidadesDisponiveis.isEmpty()) {
            System.err.println("ALERTA: Nenhuma unidade disponível para a Triagem ID: " + triagem.getId());
            // Aqui você poderia enviar para uma fila de "Espera Crítica"
            return;
        }

        // Algoritmo de Distância
        UnidadeSaude unidadeDestino = unidadesDisponiveis.stream()
                .min(Comparator.comparingDouble(u -> calcularDistancia(
                        triagem.getPaciente().getLatitude(), triagem.getPaciente().getLongitude(),
                        u.getLatitude(), u.getLongitude()
                )))
                .orElseThrow(); // Garantido que existe pela checagem anterior

        triagem.marcarComoAlocada(unidadeDestino);

        unidadeDestino.adicionarPaciente();

        unidadeSaudeRepository.save(unidadeDestino);
        triagemRepository.save(triagem);

        System.out.println("SUCESSO: Paciente " + triagem.getPaciente().getNome() +
                " (Risco " + triagem.getRisco() + ") alocado para " + unidadeDestino.getNome());
    }

    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        return Math.sqrt(Math.pow(lat2 - lat1, 2) + Math.pow(lon2 - lon1, 2));
    }
}