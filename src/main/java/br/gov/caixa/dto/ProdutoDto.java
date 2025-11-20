package br.gov.caixa.dto;

import java.math.BigDecimal;

public class ProdutoDto {

    public String nome;
    public String tipo;
    public BigDecimal rentabilidade;
    public String risco;
    public Integer prazoMinimoMeses;
    public BigDecimal valorMinimo;
    public String perfilRecomendado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getRentabilidade() {
        return rentabilidade;
    }

    public void setRentabilidade(BigDecimal rentabilidade) {
        this.rentabilidade = rentabilidade;
    }

    public String getRisco() {
        return risco;
    }

    public void setRisco(String risco) {
        this.risco = risco;
    }

    public Integer getPrazoMinimoMeses() {
        return prazoMinimoMeses;
    }

    public void setPrazoMinimoMeses(Integer prazoMinimoMeses) {
        this.prazoMinimoMeses = prazoMinimoMeses;
    }

    public BigDecimal getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getPerfilRecomendado() {
        return perfilRecomendado;
    }

    public void setPerfilRecomendado(String perfilRecomendado) {
        this.perfilRecomendado = perfilRecomendado;
    }
}
