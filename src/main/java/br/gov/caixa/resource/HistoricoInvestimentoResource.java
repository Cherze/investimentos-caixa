package br.gov.caixa.resource;

import br.gov.caixa.dto.ProdutoDto;
import br.gov.caixa.model.HistoricoInvestimento;
import br.gov.caixa.repository.HistoricoInvestimentoRepository;
import br.gov.caixa.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import java.util.List;

@Path("/investimentos")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "Keycloak")
public class HistoricoInvestimentoResource {

    @Inject
    HistoricoInvestimentoRepository historicoInvestimentoRepository;
    @Inject
    TelemetriaService telemetriaService;

    @GET
    @Path("/{clienteId}")
    @RolesAllowed("user")
    @Operation(summary = "Histórico de investimentos", description = "Retorna o histórico de investimentos do cliente")
    public Response obterHistoricoInvestimentos(@PathParam("clienteId") Long clienteId) {
        long inicio = System.currentTimeMillis();
        try {
            List<HistoricoInvestimento> historico = historicoInvestimentoRepository.listaHistoricoInvestimentos(clienteId);
            if(historico.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado: " + clienteId).build();
            }else{
                return Response.ok(historico).build();
            }
        }finally{
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("investimentos/cliente", fim - inicio);
        }

    }
}
