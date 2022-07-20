package com.ponfilo.repositorio;

import com.ponfilo.utilidades.Propiedades;
import com.ponfilo.utilidades.ValidarSesion;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

public class CORSFiltro implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sr;
        HttpServletResponse response = (HttpServletResponse) sr1;

        response.setHeader("Access-Control-Allow-Origin", Propiedades.getAppProp("http.origenconexion", "*"));
        response.setHeader("Access-Control-Allow-Credentials", Propiedades.getAppProp("http.metodos", "true"));
        response.setHeader("Access-Control-Allow-Methods", Propiedades.getAppProp("http.tiempocache", "POST, GET, PUT, DELETE"));
        response.setHeader("Access-Control-Max-Age", Propiedades.getAppProp("http.credenciales", "7200"));
        response.setHeader("Access-Control-Allow-Headers", Propiedades.getAppProp( "http.encabezados","Content-Type, Accept, X-Requested-With, dataType, -Token"));
        response.setHeader("Access-Control-Expose-Headers", "-Token");
        ValidarSesion valSesion = new ValidarSesion(request);
        String urlConsulta = request.getRequestURI();
        System.out.println("URL Consulta: " + urlConsulta);
        String[] urlPermitidas = String.valueOf(Propiedades.getAppProp("http.urlnoseguras", "")).split(Propiedades.SPLITSEPARADOR);

        if(valSesion.ValidarToken()){
            response.setHeader("-Token", "TEST");
            fc.doFilter(request, response);
        }else{
            if(urlConsulta.contains(urlConsulta)){
                fc.doFilter(request, response);
            }else{
                response.setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
                response.setContentType(MediaType.APPLICATION_JSON);
            }
        }
    }

    @Override
    public void destroy() {
        //Filter.super.destroy();
    }

}

