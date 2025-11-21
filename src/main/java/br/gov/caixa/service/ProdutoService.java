package br.gov.caixa.service;

import br.gov.caixa.dto.ProdutoDto;
import br.gov.caixa.dto.mapper.ProdutoMapper;
import br.gov.caixa.exception.ValidacaoProdutoException;
import br.gov.caixa.exception.ProdutoNaoEncontradoException;
import br.gov.caixa.model.Produto;
import br.gov.caixa.repository.ProdutoRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped

public class ProdutoService {


    public final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoDto> getAll() {
        return produtoRepository.findAll().list().stream().map(ProdutoMapper::toDto).toList();
    }

    public Produto create(ProdutoDto produtoDto){
        this.validaProdutoDto(produtoDto);
        Produto produto = ProdutoMapper.toEntity(produtoDto);
        produtoRepository.persist(produto);

        return produto;
    }


    public ProdutoDto getById(long id) {
        Produto produto = produtoRepository.findById(id);
        this.isNull(produto);
        return ProdutoMapper.toDto(produto);
    }


    public Produto update(long id, ProdutoDto produtoDto) {
        this.validaProdutoDto(produtoDto);
        Produto produto = produtoRepository.findById(id);
        this.isNull(produto);
        return ProdutoMapper.updateProduto(produto, produtoDto);
    }

    public void delete(long id) {

        if(!produtoRepository.deleteById(id)){
            throw new ProdutoNaoEncontradoException("Produto não encontrado.");
        }
    }

    public void validaProdutoDto(ProdutoDto produtoDto){
        if (produtoDto.getRentabilidade().compareTo(BigDecimal.ZERO)<=0){
            throw new ValidacaoProdutoException("Rentabilidade precisa ser maior que zero.");
        } else if (produtoDto.getNome()==null || produtoDto.getNome().isBlank()) {
            throw new ValidacaoProdutoException("Nome não pode ser vazio.");
        }
    }

    public void isNull(Produto produto){
        if (produto==null){
            throw new ProdutoNaoEncontradoException("Produto não encontrado.");
        }
    }

    public List<ProdutoDto> getRecomendados(String perfil) {
       //List<Produto> produtos = produtoRepository.findByPerfil(perfil);
       //return ProdutoMapper.toDto(produtos);
        return produtoRepository.findByPerfil(perfil).stream().map(ProdutoMapper::toDto).toList();
    }
}
