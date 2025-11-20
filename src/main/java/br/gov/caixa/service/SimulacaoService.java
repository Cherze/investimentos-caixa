package br.gov.caixa.service;

import br.gov.caixa.dto.SimulacaoProdutoDiaResponse;
import br.gov.caixa.model.Produto;
import br.gov.caixa.model.Simulacao;
import br.gov.caixa.repository.SimulacaoRepository;
import br.gov.caixa.repository.ProdutoRepository;
import br.gov.caixa.dto.SimulacaoRequest;
import br.gov.caixa.dto.SimulacaoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SimulacaoService {

    //@Inject
    //PerfilRiscoService perfilRiscoService;
    @Inject
    SimulacaoRepository simulacaoRepository;
    @Inject
    ProdutoRepository produtoRepository;

    @Transactional
    public SimulacaoResponse simularInvestimento(@Valid SimulacaoRequest request) {
        // Validar produto
        Optional<Produto> produtoOpt = validarProduto(request);

        if (produtoOpt.isEmpty()) {
            throw new IllegalArgumentException("Produto não encontrado ou não atende aos critérios");
        }

        Produto produto = produtoOpt.get();

        // Calcular simulação
        BigDecimal valorFinal = calcularValorFinal(request.valor, produto.rentabilidade, request.prazoMeses);

        // Persistir simulação
        Simulacao simulacao = new Simulacao();
        simulacao.clienteId = request.clienteId;
        simulacao.valor = request.valor;
        simulacao.prazoMeses = request.prazoMeses;
        simulacao.tipoProduto = request.tipoProduto;
        simulacao.produtoValidado = produto.nome;
        simulacao.valorFinal = valorFinal;
        simulacao.rentabilidadeEfetiva = produto.rentabilidade;
        simulacaoRepository.persist(simulacao);

        // Construir resposta
        return construirResponse(produto, valorFinal, request.prazoMeses);
    }

    private Optional<Produto> validarProduto(SimulacaoRequest request) {
        return produtoRepository.find(
                        "tipo = ?1 and ativo = true and (?2 >= valorMinimo or valorMinimo is null) and (?3 >= prazoMinimoMeses or prazoMinimoMeses is null)",
                        request.tipoProduto, request.valor, request.prazoMeses)
                .firstResultOptional();
    }

    private BigDecimal calcularValorFinal(BigDecimal valorInicial, BigDecimal rentabilidade, Integer prazoMeses) {
        // Fórmula: VF = VP * (1 + rentabilidade)^(prazo/12)
        double taxaMensal = rentabilidade.doubleValue() / 12;
        double fator = Math.pow(1 + taxaMensal, prazoMeses);
        BigDecimal valorFinal = valorInicial.multiply(BigDecimal.valueOf(fator));

        return valorFinal.setScale(2, RoundingMode.HALF_UP);
    }

    private SimulacaoResponse construirResponse(Produto produto, BigDecimal valorFinal, Integer prazoMeses) {
        SimulacaoResponse response = new SimulacaoResponse();

        response.produtoValidado = new SimulacaoResponse.ProdutoValidado(
                produto.id,
                produto.nome,
                produto.tipo,
                produto.rentabilidade,
                produto.risco
        );

        response.resultadoSimulacao = new SimulacaoResponse.ResultadoSimulacao(
                valorFinal,
                produto.rentabilidade,
                prazoMeses
        );

        response.dataSimulacao = java.time.LocalDateTime.now();

        return response;
    }

    public List<Simulacao> listarSimulacoes() {
        return simulacaoRepository.listAll();
    }

    public List<SimulacaoProdutoDiaResponse> listarSimulacoesPorDia() {
        // Buscar todas as simulações e agrupar programaticamente
        List<Simulacao> todasSimulacoes = this.listarSimulacoes();

        // Agrupar por produto e data
        Map<String, Map<LocalDate, List<Simulacao>>> agrupado =
                todasSimulacoes.stream()
                        .filter(s -> s.produtoValidado != null && s.dataSimulacao != null)
                        .collect(Collectors.groupingBy(
                                s -> s.produtoValidado,
                                Collectors.groupingBy(s -> s.dataSimulacao.toLocalDate())
                        ));

        List<SimulacaoProdutoDiaResponse> resultado = new ArrayList<>();

        for (Map.Entry<String, Map<LocalDate, List<Simulacao>>> produtoEntry : agrupado.entrySet()) {
            String produto = produtoEntry.getKey();

            for (Map.Entry<LocalDate, List<Simulacao>> dataEntry : produtoEntry.getValue().entrySet()) {
                LocalDate data = dataEntry.getKey();
                List<Simulacao> simulacoes = dataEntry.getValue();

                long quantidade = simulacoes.size();

                // Calcular média do valor final
                BigDecimal somaValorFinal = simulacoes.stream()
                        .map(s -> s.valorFinal != null ? s.valorFinal : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal mediaValorFinal = quantidade > 0 ?
                        somaValorFinal.divide(BigDecimal.valueOf(quantidade), 2, java.math.RoundingMode.HALF_UP) :
                        BigDecimal.ZERO;

                // Calcular total investido
                BigDecimal totalInvestido = simulacoes.stream()
                        .map(s -> s.valor != null ? s.valor : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                resultado.add(new SimulacaoProdutoDiaResponse(
                        produto, data, quantidade, mediaValorFinal, totalInvestido
                ));
            }
        }

        // Ordenar por data (mais recente primeiro) e depois por produto
        resultado.sort((a, b) -> {
            int compareData = b.getData().compareTo(a.getData());
            return compareData != 0 ? compareData : a.getProduto().compareTo(b.getProduto());
        });

        return resultado;
    }

    private SimulacaoProdutoDiaResponse mapToSimulacaoProdutoDiaResponse(Object[] result) {
        String produto = (String) result[0];
        LocalDate data = ((java.sql.Date) result[1]).toLocalDate();
        Long quantidade = ((Number) result[2]).longValue();
        BigDecimal mediaValorFinal = result[3] != null ?
                new BigDecimal(result[3].toString()).setScale(2, java.math.RoundingMode.HALF_UP) :
                BigDecimal.ZERO;
        BigDecimal totalInvestido = result[4] != null ?
                new BigDecimal(result[4].toString()).setScale(2, java.math.RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        return new SimulacaoProdutoDiaResponse(produto, data, quantidade, mediaValorFinal, totalInvestido);
    }
}