package br.gov.caixa.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos_investimento")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String tipo;

    @Column(nullable = false)
    public BigDecimal rentabilidade;

    @Column(nullable = false)
    public String risco;

    @Column(name = "prazo_minimo_meses", nullable = false)
    public Integer prazoMinimoMeses;

    @Column(name = "valor_minimo", nullable = false)
    public BigDecimal valorMinimo;

    @Column(name = "perfil_recomendado", nullable = false)
    public String perfil;

    @Column(nullable = false)
    public Boolean ativo;

    //public LocalDateTime criadoEm = LocalDateTime.now();

    public Produto(String nome, String tipo, BigDecimal rentabilidade, String risco, Integer prazoMinimoMeses,
                   BigDecimal valorMinimo, String perfil, Boolean ativo) {
        this.nome = nome;
        this.tipo = tipo;
        this.rentabilidade = rentabilidade;
        this.risco = risco;
        this.prazoMinimoMeses = prazoMinimoMeses;
        this.valorMinimo = valorMinimo;
        this.perfil = perfil;
        this.ativo = ativo;
    }

    public Produto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getRisco() {return risco;}

    public void setRisco(String risco) {this.risco = risco;}

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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    //public void setCriadoEm(LocalDateTime criadoEm) {
       // this.criadoEm = criadoEm;
   // }
    //public LocalDateTime getCriadoEm() {
        //return criadoEm;
    //}
}
