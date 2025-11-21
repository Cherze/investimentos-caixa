package br.gov.caixa.service;


import br.gov.caixa.model.Telemetria;
import br.gov.caixa.dto.TelemetriaResponse;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class TelemetriaService {

    // Cache em memória para melhor performance
    private final Map<String, List<Long>> metricasTemporarias = new ConcurrentHashMap<>();

    @Transactional
    public void registrarMetrica(String servico, Long tempoRespostaMs) {
        Telemetria metrica = new Telemetria(servico, tempoRespostaMs);
        metrica.persist();
    }

    // Método assíncrono para não bloquear a resposta
    public void registrarMetricaAsync(String servico, Long tempoRespostaMs, Boolean sucesso) {
        // Armazenar temporariamente em memória
        metricasTemporarias
                .computeIfAbsent(servico, k -> new ArrayList<>())
                .add(tempoRespostaMs);

        // Persistir em lote a cada 10 registros para melhor performance
        if (metricasTemporarias.values().stream().mapToInt(List::size).sum() >= 10) {
            persistirMetricasEmLote();
        }
    }

    @Transactional
    protected void persistirMetricasEmLote() {
        metricasTemporarias.forEach((servico, tempos) -> {
            tempos.forEach(tempo -> {
                Telemetria metrica = new Telemetria(servico, tempo);
                metrica.persist();
            });
        });
        metricasTemporarias.clear();
    }

    // Agendado para persistir métricas pendentes a cada minuto
    @Scheduled(every = "1m")
    void persistirMetricasPendentes() {
        persistirMetricasEmLote();
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

    // Método para obter estatísticas de um serviço específico
    public TelemetriaResponse.ServicoMetrica obterMetricasServico(String servico, int dias) {
        LocalDateTime inicio = LocalDateTime.now().minusDays(dias);
        LocalDateTime fim = LocalDateTime.now();

        Object[] resultado = (Object[]) Telemetria.getEntityManager()
                .createNativeQuery(
                        "SELECT COUNT(*) as quantidade_chamadas, " +
                                "       AVG(tempo_resposta_ms) as media_tempo_resposta, " +
                                "       MIN(tempo_resposta_ms) as menor_tempo, " +
                                "       MAX(tempo_resposta_ms) as maior_tempo " +
                                "FROM telemetria " +
                                "WHERE servico = :servico AND data_hora >= :inicio AND data_hora < :fim"
                )
                .setParameter("servico", servico)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim)
                .getSingleResult();

        Long quantidadeChamadas = ((Number) resultado[0]).longValue();
        Double mediaTempoResposta = resultado[1] != null ?
                ((Number) resultado[1]).doubleValue() : 0.0;

        return new TelemetriaResponse.ServicoMetrica(servico, quantidadeChamadas, mediaTempoResposta);
    }
}
