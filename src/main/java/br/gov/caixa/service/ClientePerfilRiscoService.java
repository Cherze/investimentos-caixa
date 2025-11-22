package br.gov.caixa.service;

import br.gov.caixa.dto.ClientePerfilRiscoResponse;
import br.gov.caixa.model.Cliente;
import br.gov.caixa.model.HistoricoInvestimento;
import br.gov.caixa.repository.ClienteRepository;
import br.gov.caixa.repository.HistoricoInvestimentoRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientePerfilRiscoService {

    @Inject
    HistoricoInvestimentoRepository historicoInvestimentoRepository;
    @Inject
    ClienteRepository clienteRepository;

    @Transactional
    public ClientePerfilRiscoResponse calcularPerfilRisco(Long clienteId) {
        // Buscar histórico do cliente
        List<HistoricoInvestimento> historico = historicoInvestimentoRepository
                .find("clienteId", clienteId)
                .list();
        if(!historico.isEmpty()) {

            // Calcular métricas
            BigDecimal volumeInvestimentos = this.calcularVolumeInvestimentos(historico);
            Integer frequenciaMovimentacoes = this.calcularFrequenciaMovimentacoes(historico);
            Boolean preferenciaLiquidez = this.determinarPreferenciaLiquidez(historico);

            // Calcular pontuação
            Integer pontuacao = this.calcularPontuacao(volumeInvestimentos, frequenciaMovimentacoes, preferenciaLiquidez);
            String perfil = this.determinarPerfil(pontuacao);
            String descricao = this.gerarDescricaoPerfil(perfil);

            // Salvar ou atualizar perfil
            this.salvarPerfilRisco(clienteId, perfil, pontuacao, descricao, volumeInvestimentos, frequenciaMovimentacoes, preferenciaLiquidez);

            return new ClientePerfilRiscoResponse(clienteId, perfil, pontuacao, descricao);
        }else{
            return new ClientePerfilRiscoResponse(clienteId,"",0,"Perfil sem histórico de investimentos.");
        }
    }

    private BigDecimal calcularVolumeInvestimentos(List<HistoricoInvestimento> historico) {
        return historico.stream()
                .map(h -> h.valor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calcularFrequenciaMovimentacoes(List<HistoricoInvestimento> historico) {
        return historico.size();
    }

    private Boolean determinarPreferenciaLiquidez(List<HistoricoInvestimento> historico) {
        long investimentosLiquidos = historico.stream()
                .filter(h -> "CDB".equals(h.tipo) || "LCI".equals(h.tipo) || "LCA".equals(h.tipo))
                .count();

        return investimentosLiquidos > (historico.size() / 2);
    }

    private Integer calcularPontuacao(BigDecimal volume, Integer frequencia, Boolean preferenciaLiquidez) {
        int pontuacao = 0;

        // Volume (0-40 pontos)
        if (volume.compareTo(BigDecimal.valueOf(100000)) > 0) {
            pontuacao += 40;
        } else if (volume.compareTo(BigDecimal.valueOf(50000)) > 0) {
            pontuacao += 30;
        } else if (volume.compareTo(BigDecimal.valueOf(10000)) > 0) {
            pontuacao += 20;
        } else {
            pontuacao += 10;
        }

        // Frequência (0-30 pontos)
        if (frequencia > 20) {
            pontuacao += 30;
        } else if (frequencia > 10) {
            pontuacao += 20;
        } else if (frequencia > 5) {
            pontuacao += 15;
        } else {
            pontuacao += 10;
        }

        // Preferência Liquidez (0-30 pontos)
        if (preferenciaLiquidez) {
            pontuacao += 10; // Conservador
        } else {
            pontuacao += 30; // Busca rentabilidade
        }

        return pontuacao;
    }

    private String determinarPerfil(Integer pontuacao) {
        if (pontuacao <= 40) {
            return "Conservador";
        } else if (pontuacao <= 70) {
            return "Moderado";
        } else {
            return "Agressivo";
        }
    }

    private String gerarDescricaoPerfil(String perfil) {
        switch (perfil) {
            case "Conservador":
                return "Perfil com baixa tolerância a risco, foco em segurança e liquidez";
            case "Moderado":
                return "Perfil equilibrado entre segurança e rentabilidade";
            case "Agressivo":
                return "Perfil com alta tolerância a risco, busca máxima rentabilidade";
            default:
                return "Perfil em análise";
        }
    }

    @Transactional
    protected void salvarPerfilRisco(Long clienteId, String perfil, Integer pontuacao,
                                     String descricao, BigDecimal volume, Integer frequencia, Boolean preferenciaLiquidez) {

        Optional<Cliente> perfilExistente = clienteRepository.find("clienteId", clienteId).firstResultOptional();
        //Log.info(perfilExistente.get().getPerfil());
        //if (perfilExistente.isPresent() && perfilExistente.equals(perfilRisco)) {
            //perfilRisco = perfilExistente.get();
        //} else {
            //perfilRisco = new Cliente(perfil, pontuacao, descricao, volume, frequencia, preferenciaLiquidez);
            //perfilRisco.clienteId = clienteId;
        //}

        if (perfilExistente.isEmpty()) {
            Cliente perfilRisco = new Cliente(perfil, pontuacao, descricao, volume, frequencia, preferenciaLiquidez);
            clienteRepository.persist(perfilRisco);
        } else if (!perfilExistente.get().getPontuacao().equals(pontuacao)) {
            perfilExistente.get().setPerfil(perfil);
            perfilExistente.get().setPontuacao(pontuacao);
            perfilExistente.get().setDescricao(descricao);
            perfilExistente.get().setVolumeInvestimentos(volume);
            perfilExistente.get().setFrequenciaMovimentacoes(frequencia);
            perfilExistente.get().setPreferenciaLiquidez(preferenciaLiquidez);
        }
    }

    public Optional<ClientePerfilRiscoResponse> obterPerfilRisco(Long clienteId) {
        Optional<Cliente> perfilOpt = clienteRepository.find("clienteId", clienteId)
                .firstResultOptional()
                .map(obj -> (Cliente) obj);

        return perfilOpt.map(perfil -> new ClientePerfilRiscoResponse(
                perfil.clienteId,
                perfil.perfil,
                perfil.pontuacao,
                perfil.descricao
        ));
    }
}
