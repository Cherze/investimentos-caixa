package br.gov.caixa.service;


import br.gov.caixa.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.model.Produto;
import br.gov.caixa.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped

public class ProdutoService {


    public final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> getAll() {
        return produtoRepository.findAll().stream().toList();
    }


    public void isNull(Produto produto){
        if (produto==null){
            throw new ProdutoNaoEncontradoException("Produto não encontrado.");
        }
    }

    public List<Produto> getRecomendados(String perfil) {
        return produtoRepository.findByPerfil(perfil);
    }
}
