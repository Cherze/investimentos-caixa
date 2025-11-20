package br.gov.caixa.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class SimulacaoRequest {

    @NotNull(message = "clienteId é obrigatório")
    public Long clienteId;

    @NotNull(message = "valor é obrigatório")
    @Positive(message = "valor deve ser positivo")
    public BigDecimal valor;

    @NotNull(message = "prazoMeses é obrigatório")
    @Positive(message = "prazoMeses deve ser positivo")
    public Integer prazoMeses;

    @NotNull(message = "tipoProduto é obrigatório")
    public String tipoProduto;

    // Getters e Setters
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }

    public String getTipoProduto() { return tipoProduto; }
    public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }
}
