package com.ponfilo.controladores;

import com.google.gson.Gson;
import com.ponfilo.modelos.sesion.UsuarioConfiguracion;
import com.ponfilo.modelos.sesion.Usuario;
import com.ponfilo.modelos.sesion.UsuarioLogin;
import com.ponfilo.utilidades.ConexionBD;
import com.ponfilo.utilidades.LogsAplicacion;
import com.ponfilo.utilidades.Propiedades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CInicio extends ConexionBD {

    Gson GSON = new Gson();

    public void configuracionAplicacion() {
        System.out.println(Propiedades.getAppProp("app.nombre") + "APP");
    }

    public UsuarioConfiguracion iniciarSesionWeb(UsuarioLogin usrLogin) {
        //Datos generales para BD
        ConexionBD conBD = new ConexionBD();
        PreparedStatement stQuery = null;
        ResultSet resQuery = null;
        //Datos de la funcion
        UsuarioConfiguracion usrConf = new UsuarioConfiguracion();
        Usuario usr = null;
        try {
            stQuery = conBD.prepararQuery(Propiedades.getBDProp("sql.vstusuario.loginusuario"));
            stQuery.setString(1, usrLogin.getUsuario());
            stQuery.setString(2, usrLogin.getClave());
            resQuery = stQuery.executeQuery();
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".iniciarSesionWeb()", "[executeQuery - " + stQuery.toString() + "]", "", ex);
            return usrConf;
        }

        try {
            if (resQuery != null && resQuery.next()) {
                usr = GSON.fromJson(resQuery.getString(1), Usuario.class);
                if (usr != null && usr.getUsrUsuario().equals(usrLogin.getUsuario())) {
                    usrConf = new UsuarioConfiguracion(usr);
                    LogsAplicacion.imprimirLogInf(this.getClass() + ".iniciarSesionWeb()", "SE HA INICIADO UNA NUEVA SESION PARA [" + usr.getUsrUsuario() + "]", usr.getUsrUsuario());
                }
            }
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".iniciarSesionWeb()", "[fromJson]", "", ex);
        }
        return usrConf;
    }
}
