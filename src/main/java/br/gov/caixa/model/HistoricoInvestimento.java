package br.gov.caixa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "historico_investimentos")
public class HistoricoInvestimento{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotNull
    @Column(name = "cliente_id")
    public Long clienteId;

    @NotNull
    public String tipo;

    @NotNull
    public BigDecimal valor;

    @NotNull
    public BigDecimal rentabilidade;

    @NotNull
    public LocalDate data;
}
