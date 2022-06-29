package com.ponfilo.apis;


import com.ponfilo.modelos.TestJPA;
import com.ponfilo.repositorio.RepTestJPA;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;


@Path("/inicio")
@Produces("application/json")
@Consumes("application/json")
@Transactional
public class Inicio {
    RepTestJPA testJPA;

    @Inject
    public Inicio(RepTestJPA testJPA){
        this.testJPA = testJPA;
    }

    @GET
    public List<TestJPA> listarTodos(){
        return testJPA.listarRegistros();
    }

    @GET
    @Path("/{numero}")
    public TestJPA listarPorNumero(@PathParam("numero") Long numero){
        return testJPA.buscarPorNumero(numero);
    }

    @POST
    public Response crearNuevo(TestJPA item){
        testJPA.guardar(item);
        return Response.created(URI.create("/inicio/" + item.getNumero())).build();
    }

    @PUT
    @Path("/{numero}")
    public Response actualizarRegistro(@PathParam("numero") Long numero, TestJPA item){
        testJPA.actualizar(item);
        return Response.ok(item).build();
    }

    @DELETE
    @Path("/{numero}")
    public Response eliminarRegistro(@PathParam("numero") Long numero){
        TestJPA item = testJPA.buscarPorNumero(numero);
        if(item == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }else{
            testJPA.eliminarRegistro(item);
            return Response.ok().build();
        }
    }
}
