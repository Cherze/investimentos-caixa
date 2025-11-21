package br.gov.caixa.resource;

import br.gov.caixa.dto.ClientePerfilRiscoResponse;
import br.gov.caixa.service.ClientePerfilRiscoService;
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

    @GET
    @Path("/{clienteId}")
    @RolesAllowed("user")
    @Operation(summary = "Obter perfil de risco", description = "Retorna o perfil de risco calculado para o cliente")
    public Response obterPerfilRisco(@PathParam("clienteId") Long clienteId) {
        Optional<ClientePerfilRiscoResponse> perfil = clientePerfilRiscoService.obterPerfilRisco(clienteId);

        if (perfil.isPresent()) {
            return Response.ok(perfil.get()).build();
        } else {
            // Se não existe, calcula um novo perfil
            //ClientePerfilRiscoResponse novoPerfil = clientePerfilRiscoService.calcularPerfilRisco(clienteId);
            //return Response.ok(novoPerfil).build();
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
