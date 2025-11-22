package br.gov.caixa.resource;

import br.gov.caixa.dto.ProdutoDto;
import br.gov.caixa.model.Produto;
import br.gov.caixa.model.Simulacao;
import br.gov.caixa.service.ProdutoService;
import br.gov.caixa.service.TelemetriaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
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

    @POST
    @Transactional
    //@RolesAllowed({"admin"})
    public Response addProduto(ProdutoDto produtoDto){
        Produto produto = produtoService.create(produtoDto);
        return Response.status(Response.Status.CREATED).entity(produto).build();
    }

    @GET
    //@RolesAllowed({"user"})
    public Response getProduto(){
        List<ProdutoDto> produtos = produtoService.getAll();
        return Response.status(Response.Status.OK).entity(produtos).build();

    }

    @GET
    @Path("/{id}")
    //@RolesAllowed({"user"})
    public Response getById(@PathParam("id") long id){
        ProdutoDto produto = produtoService.getById(id);
        return Response.status(Response.Status.OK).entity(produto).build();

    }

    @GET
    @Path("/produtos-recomendados/{perfil}")
    //@RolesAllowed({"user"})
    public Response getRecomendadosPerfil(@PathParam("perfil") String perfil){
        long inicio = System.currentTimeMillis();
        try {
            List<ProdutoDto> produtos = produtoService.getRecomendados(perfil);
            return Response.status(Response.Status.OK).entity(produtos).build();
        }finally{
            long fim = System.currentTimeMillis();
            telemetriaService.registrarMetrica("produtos-recomendados/perfil", fim - inicio);
        }


    }

    @PUT
    @Path("/{id}")
    @Transactional
    //@RolesAllowed({"admin"})
    public Response updateProduto(@PathParam("id") long id, ProdutoDto produtoDto){
        Produto produtoAtualizado = produtoService.update(id,produtoDto);
        return Response.status(Response.Status.OK).entity(produtoAtualizado).build();

    }

    @DELETE
    @Path("/{id}")
    @Transactional
    //@RolesAllowed({"admin"})
    public Response deleteProduto(@PathParam("id") long id){
         produtoService.delete(id);
         return Response.status(Response.Status.NO_CONTENT).build();

    }

}
