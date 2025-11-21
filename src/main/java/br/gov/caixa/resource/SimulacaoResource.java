package br.gov.caixa.resource;

import br.gov.caixa.dto.SimulacaoProdutoDiaResponse;
import br.gov.caixa.dto.SimulacaoRequest;
import br.gov.caixa.dto.SimulacaoResponse;
import br.gov.caixa.interceptor.MonitorarTempo;
import br.gov.caixa.model.Produto;
import br.gov.caixa.model.Simulacao;
import br.gov.caixa.service.ProdutoService;
import br.gov.caixa.service.SimulacaoService;
import br.gov.caixa.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.time.LocalDateTime;
import java.util.List;

@Path("/simulacoes")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimulacaoResource {

    @Inject
    TelemetriaService telemetriaService;

    public final SimulacaoService simulacaoService;

    public SimulacaoResource(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @POST
    @Path("/simular-investimento")

    @Transactional
    @Operation(summary = "Simular investimento", description = "Realiza simulação de investimento baseada nos parâmetros fornecidos")
    @APIResponse(responseCode = "200", description = "Simulação realizada com sucesso")
    @APIResponse(responseCode = "400", description = "Dados de entrada inválidos")
    //@RolesAllowed({"admin"})
    public Response addSimulacao(@Valid SimulacaoRequest request){
        long inicio = System.currentTimeMillis();
        try {
            SimulacaoResponse response = simulacaoService.simularInvestimento(request);
            return Response.status(Response.Status.CREATED).entity(response).build();
        }finally {
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("simular-investimento", fim - inicio);
        }
    }

    @GET
    @Operation(summary = "Listar simulações", description = "Retorna todas as simulações realizadas")
    //@RolesAllowed({"user"})
    public Response getSimulacoes(){
        long inicio = System.currentTimeMillis();
        try {
            List<Simulacao> simulacoes = simulacaoService.listarSimulacoes();
            return Response.status(Response.Status.OK).entity(simulacoes).build();
        }finally{
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("simulacoes", fim - inicio);
        }

    }

    @GET
    @Path("/por-produto-dia")
    //@RolesAllowed("user")
    @Operation(summary = "Simulações por produto e dia", description = "Retorna estatísticas de simulações agrupadas por produto e data")
    public Response getSimulacoesPorProdutoDia() {
        long inicio = System.currentTimeMillis();
        try{
            List<SimulacaoProdutoDiaResponse> simulacoesDia = simulacaoService.listarSimulacoesPorDia();
            return Response.status(Response.Status.OK).entity(simulacoesDia).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro ao obter estatísticas: " + e.getMessage()))
                    .build();
        }finally{
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("por-produto-dia", fim - inicio);
        }
    }

    public static class ErrorResponse {
        public String message;
        public LocalDateTime timestamp;

        public ErrorResponse(String message) {
            this.message = message;
            this.timestamp = java.time.LocalDateTime.now();
        }
    }

}
