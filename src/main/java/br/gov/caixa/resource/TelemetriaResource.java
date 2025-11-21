package br.gov.caixa.resource;

import br.gov.caixa.dto.TelemetriaResponse;
import br.gov.caixa.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

@Path("/telemetria")
@Produces(MediaType.APPLICATION_JSON)
@SecurityRequirement(name = "Keycloak")
public class TelemetriaResource {

    @Inject
    TelemetriaService telemetriaService;

    @GET
    @RolesAllowed("admin")
    @Operation(summary = "Dados de telemetria", description = "Retorna métricas de uso e desempenho dos serviços")
    public Response obterTelemetria() {
        TelemetriaResponse response = telemetriaService.obterDadosTelemetria();
        return Response.ok(response).build();
    }
}
