package br.gov.caixa.resource;

import br.gov.caixa.dto.ClientePerfilRiscoResponse;
import br.gov.caixa.service.ClientePerfilRiscoService;
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

import java.util.Optional;

@Path("/perfil-risco")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "Keycloak")
public class ClientePerfilRiscoResource {

    @Inject
    ClientePerfilRiscoService clientePerfilRiscoService;
    @Inject
    TelemetriaService telemetriaService;

    @GET
    @Path("/{clienteId}")
    @RolesAllowed("user")
    @Operation(summary = "Obter perfil de risco", description = "Retorna o perfil de risco calculado para o cliente")
    public Response obterPerfilRisco(@PathParam("clienteId") Long clienteId) {
        long inicio = System.currentTimeMillis();
        try {
            ClientePerfilRiscoResponse perfil = clientePerfilRiscoService.calcularPerfilRisco(clienteId);
            if (perfil.pontuacao!=0) {
                return Response.ok(perfil).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente " + clienteId + " : " + perfil.descricao).build();
            }
        }finally{
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("perfil-risco/cliente", fim - inicio);
        }
    }
}
