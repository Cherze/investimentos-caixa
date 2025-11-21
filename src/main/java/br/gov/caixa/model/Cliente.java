package br.gov.caixa.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes_investimento")
public class Cliente{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    public Long clienteId;

    @NotNull
    public String perfil;

    @NotNull
    public Integer pontuacao;

    public String descricao;

    @Column(name = "volume_investimentos")
    public BigDecimal volumeInvestimentos;

    @Column(name = "frequencia_movimentacoes")
    public Integer frequenciaMovimentacoes;

    @Column(name = "preferencia_liquidez")
    public Boolean preferenciaLiquidez;


    public LocalDateTime dataAtualizacao = LocalDateTime.now();

    public Cliente(){}

    public Cliente(String perfil, Integer pontuacao, String descricao, BigDecimal volumeInvestimentos,
                   Integer frequenciaMovimentacoes, Boolean preferenciaLiquidez) {
        this.perfil = perfil;
        this.pontuacao = pontuacao;
        this.descricao = descricao;
        this.volumeInvestimentos = volumeInvestimentos;
        this.frequenciaMovimentacoes = frequenciaMovimentacoes;
        this.preferenciaLiquidez = preferenciaLiquidez;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getVolumeInvestimentos() {
        return volumeInvestimentos;
    }

    public void setVolumeInvestimentos(BigDecimal volumeInvestimentos) {
        this.volumeInvestimentos = volumeInvestimentos;
    }

    public Integer getFrequenciaMovimentacoes() {
        return frequenciaMovimentacoes;
    }

    public void setFrequenciaMovimentacoes(Integer frequenciaMovimentacoes) {
        this.frequenciaMovimentacoes = frequenciaMovimentacoes;
    }

    public Boolean getPreferenciaLiquidez() {
        return preferenciaLiquidez;
    }

    public void setPreferenciaLiquidez(Boolean preferenciaLiquidez) {
        this.preferenciaLiquidez = preferenciaLiquidez;
    }
}

