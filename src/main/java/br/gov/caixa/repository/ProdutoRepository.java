package br.gov.caixa.repository;


import br.gov.caixa.model.Produto;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {

    public List<Produto> findByPerfil(String perfil) {
        return find("perfil", perfil).stream().toList();
    }
}
