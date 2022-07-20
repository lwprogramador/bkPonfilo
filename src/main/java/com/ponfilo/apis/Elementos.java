/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ponfilo.apis;

import com.google.gson.Gson;
import com.ponfilo.controladores.CElementos;
import com.ponfilo.modelos.elementos.TipoDocumento;
import com.ponfilo.modelos.response.RespCliente;
import com.ponfilo.utilidades.Propiedades;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

/**
 *
 * @author Leudis Wan Der Biest
 */
@Path("/datos")
public class Elementos {

    CElementos cElementos;
    private Gson GSON;

    public Elementos() {
        GSON = new Gson();
        cElementos = new CElementos();
    }

    @GET
    @Path("/configuracion_aplicacion")
    @Produces("application/json")
    @Consumes("application/json")
    public Response listarConfiguracionAplicacion(@Context HttpServletRequest httpRequest) {

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(GSON.toJson(cElementos.listarConfiguracionAplicacion()))
                .build();
    }

    @POST
    @Path("/tipos_documentos")
    @Produces("application/json")
    @Consumes("application/json")
    public Response guardarTiposDocumentos(@RequestBody String body, @Context HttpServletRequest httpRequest) {
        RespCliente respCliente = null;
        TipoDocumento item = null;
        try {
            item = GSON.fromJson(body, TipoDocumento.class);
            respCliente = item.validarDatosLogin();
            if (respCliente != null && respCliente.getConsultaEstado() == false) {
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
        }

        item = cElementos.guardarTipoDocumento(item);
        if (item != null) {
            respCliente.setCodigoEstado(0);
            respCliente.setConsultaEstado(true);
            respCliente.setContenido(item);
            respCliente.setMensaje(String.format(Propiedades.getMsjProp("api.guardarinformacion"), "TIPO DE DOCUMENTO"));

            return Response.status(Response.Status.OK)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(GSON.toJson(respCliente))
                    .build();
        }
        respCliente.setCodigoEstado(-1);
        respCliente.setConsultaEstado(false);        
        respCliente.setMensaje(String.format(Propiedades.getMsjProp("api.errorguardarinformacion"), "TIPO DE DOCUMENTO"));
        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(GSON.toJson(respCliente))
                .build();
    }

    @GET
    @Path("/tipos_documentos")
    @Produces("application/json")
    @Consumes("application/json")
    public Response listarTiposDocumentos(@Context HttpServletRequest httpRequest) {

        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(GSON.toJson(cElementos.listarTiposDocumentos()))
                .build();
    }
}
