package br.gov.caixa.dto;

public class ClientePerfilRiscoResponse {

    public Long clienteId;
    public String perfil;
    public Integer pontuacao;
    public String descricao;

    public ClientePerfilRiscoResponse() {}

    public ClientePerfilRiscoResponse(Long clienteId, String perfil, Integer pontuacao, String descricao) {
        this.clienteId = clienteId;
        this.perfil = perfil;
        this.pontuacao = pontuacao;
        this.descricao = descricao;
    }
}
