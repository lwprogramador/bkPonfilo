/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ponfilo.controladores;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ponfilo.modelos.elementos.ConfiguracionAplicacion;
import com.ponfilo.modelos.elementos.TipoDocumento;
import com.ponfilo.modelos.sesion.Usuario;
import com.ponfilo.modelos.sesion.UsuarioConfiguracion;
import com.ponfilo.utilidades.ConexionBD;
import com.ponfilo.utilidades.LogsAplicacion;
import com.ponfilo.utilidades.Propiedades;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Leudis Wan Der Biest
 */
public class CElementos extends ConexionBD {

    Gson GSON = new Gson();

    public ConfiguracionAplicacion listarConfiguracionAplicacion() {
        ConfiguracionAplicacion confApp = new ConfiguracionAplicacion();
        try {
            confApp.setTposDocumentos(listarTiposDocumentos());
        } catch (Exception ex) {
            LogsAplicacion.imprimirLogError(this.getClass() + ".listarConfiguracionAplicacion()", "[setTposDocumentos - " + ex.getMessage() + "]", "", ex);
        }
        return confApp;
    }

    public TipoDocumento guardarTipoDocumento(TipoDocumento item) {
        //Datos generales para BD        
        PreparedStatement stQuery = null;
        ResultSet resQuery = null;
        //Datos de la funcion
        TipoDocumento tpoDoc = new TipoDocumento();
        try {
            if (item.getCodigo() != null && !item.getCodigo().isBlank() && !item.getCodigo().isEmpty()) {
                stQuery = prepararQuery(Propiedades.getBDProp("sql.actualizar.tpodocumento"), false);
                stQuery.setString(4, item.getCodigo());
            } else {
                stQuery = prepararQuery(Propiedades.getBDProp("sql.insertar.tpodocumento"), false);
            }
            stQuery.setString(1, item.getAbreviacion());
            stQuery.setString(2, item.getDescripcion());
            stQuery.setBoolean(3, item.isExtranjero());
            resQuery = stQuery.executeQuery();
            if (resQuery != null && resQuery.next()) {
                generarCommit();
                try {
                    stQuery = prepararQuery(Propiedades.getBDProp("sql.traer.tpodocumentocodigo"));
                    stQuery.setString(1, resQuery.getString(1));
                    resQuery = stQuery.executeQuery();
                    if (resQuery != null && resQuery.next()) {
                        tpoDoc = GSON.fromJson(resQuery.getString(1), TipoDocumento.class);
                    }
                } catch (Exception ex) {
                    cerrarConexionBD(stQuery);
                    LogsAplicacion.imprimirLogError(this.getClass() + ".guardarTipoDocumento()", "[executeQuery - " + stQuery.toString() + "]", "", ex);
                    return null;
                }
            }
        } catch (Exception ex) {
            generarRollBack();
            cerrarConexionBD(stQuery);
            LogsAplicacion.imprimirLogError(this.getClass() + ".guardarTipoDocumento()", "[executeQuery - " + stQuery.toString() + "]", "", ex);
            return null;
        }

        return tpoDoc;
    }

    public ArrayList<TipoDocumento> listarTiposDocumentos() {
        //Datos generales para BD
        ConexionBD conBD = new ConexionBD();
        PreparedStatement stQuery = null;
        ResultSet resQuery = null;
        //Datos de la funcion
        ArrayList<TipoDocumento> arrTposDoc = new ArrayList<TipoDocumento>();

        try {
            stQuery = conBD.prepararQuery(Propiedades.getBDProp("sql.traer.tpodocumentos"));
            resQuery = stQuery.executeQuery();
        } catch (Exception ex) {
            cerrarConexionBD(stQuery);
            LogsAplicacion.imprimirLogError(this.getClass() + ".listarTiposDocumentos()", "[executeQuery - " + stQuery.toString() + "]", "", ex);
            return arrTposDoc;
        }

        try {
            if (resQuery != null && resQuery.next()) {
                arrTposDoc = GSON.fromJson(resQuery.getString(1), new TypeToken<ArrayList<TipoDocumento>>() {
                }.getType());
            }
        } catch (Exception ex) {
            cerrarConexionBD(stQuery);
            LogsAplicacion.imprimirLogError(this.getClass() + ".listarTiposDocumentos()", "[fromJson]", "", ex);
        }
        cerrarConexionBD(stQuery);
        return arrTposDoc;
    }
}
