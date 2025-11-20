package br.gov.caixa.dto.mapper;

import br.gov.caixa.dto.ProdutoDto;
import br.gov.caixa.model.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoDto produtoDto){
        Produto produto = new Produto();
        produto.setNome(produtoDto.getNome());
        produto.setTipo(produtoDto.getTipo());
        produto.setRentabilidade(produtoDto.getRentabilidade());
        produto.setRisco(produtoDto.getRisco());
        produto.setPrazoMinimoMeses(produtoDto.getPrazoMinimoMeses());
        produto.setValorMinimo(produtoDto.getValorMinimo());
        produto.setPerfilRecomendado(produtoDto.getPerfilRecomendado());
        return produto;
    }

    public static ProdutoDto toDto(Produto produto){
        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setNome(produto.getNome());
        produtoDto.setTipo(produto.getTipo());
        produtoDto.setRentabilidade(produto.getRentabilidade());
        produtoDto.setRisco(produto.getRisco());
        produtoDto.setPrazoMinimoMeses(produto.getPrazoMinimoMeses());
        produtoDto.setValorMinimo(produto.getValorMinimo());
        produtoDto.setPerfilRecomendado(produto.getPerfilRecomendado());
        return produtoDto;
    }

    public static Produto updateProduto(Produto produto, ProdutoDto produtoDto){
        produto.setNome(produtoDto.getNome());
        produto.setTipo(produtoDto.getTipo());
        produto.setRentabilidade(produtoDto.getRentabilidade());
        produto.setRisco(produtoDto.getRisco());
        produto.setPrazoMinimoMeses(produtoDto.getPrazoMinimoMeses());
        produto.setValorMinimo(produtoDto.getValorMinimo());
        produto.setPerfilRecomendado(produtoDto.getPerfilRecomendado());
        return produto;
    }

}
