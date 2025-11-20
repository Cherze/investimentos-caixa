package br.gov.caixa.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SimulacaoProdutoDiaResponse {

    public String produto;
    public LocalDate data;
    public Long quantidadeSimulacoes;
    public BigDecimal mediaValorFinal;
    public BigDecimal totalValorInvestido;

    // Construtores
    public SimulacaoProdutoDiaResponse() {}

    public SimulacaoProdutoDiaResponse(String produto, LocalDate data, Long quantidadeSimulacoes,
                                       BigDecimal mediaValorFinal, BigDecimal totalValorInvestido) {
        this.produto = produto;
        this.data = data;
        this.quantidadeSimulacoes = quantidadeSimulacoes;
        this.mediaValorFinal = mediaValorFinal;
        this.totalValorInvestido = totalValorInvestido;
    }

    // Getters e Setters
    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Long getQuantidadeSimulacoes() { return quantidadeSimulacoes; }
    public void setQuantidadeSimulacoes(Long quantidadeSimulacoes) { this.quantidadeSimulacoes = quantidadeSimulacoes; }

    public BigDecimal getMediaValorFinal() { return mediaValorFinal; }
    public void setMediaValorFinal(BigDecimal mediaValorFinal) { this.mediaValorFinal = mediaValorFinal; }

    public BigDecimal getTotalValorInvestido() { return totalValorInvestido; }
    public void setTotalValorInvestido(BigDecimal totalValorInvestido) { this.totalValorInvestido = totalValorInvestido; }
}
