package br.gov.caixa.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SimulacaoResponse {

    public ProdutoValidado produtoValidado;
    public ResultadoSimulacao resultadoSimulacao;
    public LocalDateTime dataSimulacao;

    public static class ProdutoValidado {
        public Long id;
        public String nome;
        public String tipo;
        public BigDecimal rentabilidade;
        public String risco;

        public ProdutoValidado() {}

        public ProdutoValidado(Long id, String nome, String tipo, BigDecimal rentabilidade, String risco) {
            this.id = id;
            this.nome = nome;
            this.tipo = tipo;
            this.rentabilidade = rentabilidade;
            this.risco = risco;
        }
    }

    public static class ResultadoSimulacao {
        public BigDecimal valorFinal;
        public BigDecimal rentabilidadeEfetiva;
        public Integer prazoMeses;

        public ResultadoSimulacao() {}

        public ResultadoSimulacao(BigDecimal valorFinal, BigDecimal rentabilidadeEfetiva, Integer prazoMeses) {
            this.valorFinal = valorFinal;
            this.rentabilidadeEfetiva = rentabilidadeEfetiva;
            this.prazoMeses = prazoMeses;
        }
    }
}
