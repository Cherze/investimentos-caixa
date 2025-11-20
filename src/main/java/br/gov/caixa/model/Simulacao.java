package br.gov.caixa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulacoes_investimento")
public class Simulacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "cliente_id")
    public Long clienteId;

    @NotNull
    public BigDecimal valor;

    @NotNull
    @Column(name = "prazo_meses")
    public Integer prazoMeses;

    @NotNull
    @Column(name = "tipo_produto")
    public String tipoProduto;

    @NotNull
    @Column(name = "produto_validado")
    public String produtoValidado;

    @NotNull
    @Column(name = "valor_final")
    public BigDecimal valorFinal;

    @NotNull
    @Column(name = "rentabilidade_efetiva")
    public BigDecimal rentabilidadeEfetiva;

    @Column(name = "data_simulacao")
    public LocalDateTime dataSimulacao = LocalDateTime.now();

}