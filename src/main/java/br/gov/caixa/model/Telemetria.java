package br.gov.caixa.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "telemetria")
public class Telemetria extends PanacheEntity {

    @Column(name = "servico") // Nome da coluna no banco
    public String servico;

    @Column(name = "tempo_resposta_ms") // Nome correto da coluna
    public Long tempoRespostaMs;

    @Column(name = "data_hora")
    public LocalDateTime dataHora;

    //@Column(name = "sucesso")
    //public Boolean sucesso;

    // Construtor padrão
    public Telemetria() {}

    // Construtor para facilitar criação
    public Telemetria(String servico, Long tempoRespostaMs) {
        this.servico = servico;
        this.tempoRespostaMs = tempoRespostaMs;
        //this.sucesso = sucesso;
        this.dataHora = LocalDateTime.now();
    }
}
