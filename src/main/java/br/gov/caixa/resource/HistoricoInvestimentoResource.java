package br.gov.caixa.resource;

import br.gov.caixa.model.HistoricoInvestimento;
import br.gov.caixa.repository.HistoricoInvestimentoRepository;
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

    @GET
    @Path("/{clienteId}")
    @RolesAllowed("user")
    @Operation(summary = "Histórico de investimentos", description = "Retorna o histórico de investimentos do cliente")
    public Response obterHistoricoInvestimentos(@PathParam("clienteId") Long clienteId) {
        List<HistoricoInvestimento> historico = historicoInvestimentoRepository.listaHistoricoInvestimentos(clienteId);

        return Response.ok(historico).build();
    }
}
