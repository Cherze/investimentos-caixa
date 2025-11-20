package br.gov.caixa.repository;

import br.gov.caixa.model.Produto;
import br.gov.caixa.model.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SimulacaoRepository implements PanacheRepository<Simulacao> {
    public Simulacao findById(long id){
        return find("id", id).firstResult();
    }

}
