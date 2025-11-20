package br.gov.caixa.exception;

public class ProdutoNaoEncontradoException extends RuntimeException{
    public ProdutoNaoEncontradoException(String msg){
        super(msg);
    }
}
