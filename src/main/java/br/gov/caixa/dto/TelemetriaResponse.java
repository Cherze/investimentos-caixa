package br.gov.caixa.dto;

import java.time.LocalDate;
import java.util.List;

public class TelemetriaResponse {

    public List<ServicoMetrica> servicos;
    public Periodo periodo;

    public static class ServicoMetrica {
        public String nome;
        public Long quantidadeChamadas;
        public Double mediaTempoRespostaMs;

        public ServicoMetrica() {}

        public ServicoMetrica(String nome, Long quantidadeChamadas, Double mediaTempoRespostaMs) {
            this.nome = nome;
            this.quantidadeChamadas = quantidadeChamadas;
            this.mediaTempoRespostaMs = mediaTempoRespostaMs;
        }
    }

    public static class Periodo {
        public LocalDate inicio;
        public LocalDate fim;

        public Periodo() {}

        public Periodo(LocalDate inicio, LocalDate fim) {
            this.inicio = inicio;
            this.fim = fim;
        }
    }
}
