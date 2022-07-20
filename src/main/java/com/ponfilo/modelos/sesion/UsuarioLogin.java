package com.ponfilo.modelos.sesion;

import com.ponfilo.modelos.response.RespCliente;
import com.ponfilo.utilidades.Propiedades;

public class UsuarioLogin {

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public RespCliente validarDatosLogin() throws Exception {
        RespCliente respCLiente = new RespCliente();
        if (this.usuario == null || this.usuario.isEmpty() || this.usuario.isBlank()
                || this.clave == null || this.clave.isEmpty() || this.clave.isBlank()) {
            respCLiente.setMensaje(Propiedades.getMsjProp("api.validarlogin"));
            respCLiente.setConsultaEstado(false);
            respCLiente.setCodigoEstado(-1);
        }
        return respCLiente;
    }

    private String usuario;
    private String clave;
}
