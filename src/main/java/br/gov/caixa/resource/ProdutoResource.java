package br.gov.caixa.resource;


import br.gov.caixa.model.Produto;
import br.gov.caixa.service.ProdutoService;
import br.gov.caixa.service.TelemetriaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/produtos")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ProdutoResource {

    public final ProdutoService produtoService;
    public final TelemetriaService telemetriaService;

    public ProdutoResource(ProdutoService produtoService, TelemetriaService telemetriaService) {
        this.produtoService = produtoService;
        this.telemetriaService = telemetriaService;
    }

    @GET
    @RolesAllowed({"user"})
    public Response getProduto(){
        List<Produto> produtos = produtoService.getAll();
        return Response.status(Response.Status.OK).entity(produtos).build();

    }


    @GET
    @Path("/produtos-recomendados/{perfil}")
    @RolesAllowed({"user"})
    public Response getRecomendadosPerfil(@PathParam("perfil") String perfil){
        long inicio = System.currentTimeMillis();
        try {
            List<Produto> produtos = produtoService.getRecomendados(perfil);
            return Response.status(Response.Status.OK).entity(produtos).build();
        }finally{
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("produtos-recomendados/perfil", fim - inicio);
        }

    }


}
