package com.ponfilo.apis;

import com.google.gson.Gson;
import com.ponfilo.controladores.CInicio;
import com.ponfilo.modelos.response.RespCliente;
import com.ponfilo.modelos.sesion.UsuarioConfiguracion;
import com.ponfilo.modelos.sesion.UsuarioLogin;
import com.ponfilo.utilidades.Propiedades;
import com.ponfilo.utilidades.ValidarSesion;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/inicio")
public class Inicio {

    CInicio contInicio;
    private Gson GSON;

    public Inicio() {
        GSON = new Gson();
        this.contInicio = new CInicio();
    }

    @GET
    @Path("/inicio_test")
    @Produces("application/json")
    @Consumes("application/json")
    public Response inicioTest(@Context HttpServletRequest httpRequest) {
        /*      ValidarSesion datosEncabezado = new ValidarSesion(httpRequest);

        String test = datosEncabezado.GenerarToken();
        boolean tokenValido = datosEncabezado.ValidarToken();
         */
        contInicio.configuracionAplicacion();
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                //.entity("OK " + test + "; " + tokenValido)
                .build();
    }

    @POST
    @Path("/web_login")
    @Produces("application/json")
    @Consumes("application/json")
    public Response webLogin(@RequestBody String body, @Context HttpServletResponse httpResponse) {
        RespCliente respCliente = null;
        //VALIDAR DATOS RECIBIDOS
        UsuarioLogin usrLogin = null;
        try {
            usrLogin = GSON.fromJson(body, UsuarioLogin.class);
            respCliente = usrLogin.validarDatosLogin();
            if (respCliente.getConsultaEstado() == false) {
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
        }
        //INICIAR LA SESIÓN DEL USUARIO
        UsuarioConfiguracion usrConf = contInicio.iniciarSesionWeb(usrLogin);
        if (usrConf != null && usrConf.getDatosUsuario()!= null) {
            respCliente.setCodigoEstado(0);
            respCliente.setMensaje(String.format(Propiedades.getMsjProp("api.iniciarsesion"), usrConf.getDatosUsuario().getUsrUsuario()));
            respCliente.setContenido(usrConf);
            //GENERAR TOKEN DE LA SESION
            ValidarSesion sesion = new ValidarSesion();
            String tokenSesion = sesion.GenerarToken(usrConf);
            if (tokenSesion != null && !tokenSesion.isEmpty()) {
                httpResponse.setHeader("-Token", tokenSesion);
                return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
            } else {
                respCliente.setCodigoEstado(-1);
                respCliente.setConsultaEstado(false);
                respCliente.setMensaje(Propiedades.getMsjProp("api.nottokensesion"));
                return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
            }
        } else {
            respCliente.setCodigoEstado(-1);
            respCliente.setConsultaEstado(false);
            respCliente.setMensaje(Propiedades.getMsjProp("api.noiniciarsesion"));
            return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(GSON.toJson(respCliente)).build();
        }
    }

}
