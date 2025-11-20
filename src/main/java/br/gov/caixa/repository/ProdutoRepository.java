package br.gov.caixa.repository;

import br.gov.caixa.model.Produto;
import br.gov.caixa.model.Simulacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public Produto findByNome(String nome){
        return find("nome", nome).firstResult();
    }
    public List<Produto> findByPreco(BigDecimal rentabilidade){
        return find("rentabilidade", rentabilidade).stream().toList();
    }
    public Produto findByTipo(String tipo){
        return find("tipo", tipo).firstResult();
    }
}
