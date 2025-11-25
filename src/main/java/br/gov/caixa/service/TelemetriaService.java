package br.gov.caixa.service;


import br.gov.caixa.model.Telemetria;
import br.gov.caixa.dto.TelemetriaResponse;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@ApplicationScoped
public class TelemetriaService {

    //private static final Logger LOG = Logger.getLogger(TelemetriaService.class);

    @Inject
    EntityManager entityManager;
    // Cache em memória para melhor performance
    private final Map<String, List<Long>> metricasTemporarias = new ConcurrentHashMap<>();

    @Transactional
    public void registrarMetrica(String servico, Long tempoRespostaMs) {
        Telemetria metrica = new Telemetria(servico, tempoRespostaMs);
        metrica.persist();
    }

    public TelemetriaResponse obterDadosTelemetria() {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDoDia = hoje.atStartOfDay();
        LocalDateTime fimDoDia = hoje.plusDays(1).atStartOfDay();

        // Consulta para obter métricas do dia atual
        List<Object[]> resultados = Telemetria.getEntityManager()
                .createNativeQuery(
                        "SELECT servico, " +
                                "       COUNT(*) as quantidade_chamadas, " +
                                "       AVG(tempo_resposta_ms) as media_tempo_resposta " +
                                "FROM telemetria " +
                                "WHERE data_hora >= :inicio AND data_hora < :fim " +
                                "GROUP BY servico " +
                                "ORDER BY quantidade_chamadas DESC"
                )
                .setParameter("inicio", inicioDoDia)
                .setParameter("fim", fimDoDia)
                .getResultList();

        List<TelemetriaResponse.ServicoMetrica> servicos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nomeServico = (String) resultado[0];
            Long quantidadeChamadas = ((Number) resultado[1]).longValue();
            Double mediaTempoResposta = resultado[2] != null ?
                    ((Number) resultado[2]).doubleValue() : 0.0;

            servicos.add(new TelemetriaResponse.ServicoMetrica(
                    nomeServico, quantidadeChamadas, mediaTempoResposta
            ));
        }

        // Se não há dados hoje, usar dados dos últimos 7 dias
        if (servicos.isEmpty()) {
            servicos = obterMetricasUltimos30Dias();
        }

        TelemetriaResponse.Periodo periodo = new TelemetriaResponse.Periodo(
                hoje.minusDays(30), // Últimos 30 dias para contexto
                hoje
        );

        TelemetriaResponse response = new TelemetriaResponse();
        response.servicos = servicos;
        response.periodo = periodo;

        return response;
    }

    private List<TelemetriaResponse.ServicoMetrica> obterMetricasUltimos30Dias() {
        LocalDateTime inicio = LocalDateTime.now().minusDays(30);
        LocalDateTime fim = LocalDateTime.now();

        List<Object[]> resultados = Telemetria.getEntityManager()
                .createNativeQuery(
                        "SELECT servico, " +
                                "       COUNT(*) as quantidade_chamadas, " +
                                "       AVG(tempo_resposta_ms) as media_tempo_resposta " +
                                "FROM telemetria " +
                                "WHERE data_hora >= :inicio AND data_hora < :fim " +
                                "GROUP BY servico " +
                                "ORDER BY quantidade_chamadas DESC"
                )
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getResultList();

        List<TelemetriaResponse.ServicoMetrica> servicos = new ArrayList<>();

        for (Object[] resultado : resultados) {
            String nomeServico = (String) resultado[0];
            Long quantidadeChamadas = ((Number) resultado[1]).longValue();
            Double mediaTempoResposta = resultado[2] != null ?
                    ((Number) resultado[2]).doubleValue() : 0.0;

            servicos.add(new TelemetriaResponse.ServicoMetrica(
                    nomeServico, quantidadeChamadas, mediaTempoResposta
            ));
        }

        return servicos;
    }
}
