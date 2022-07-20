/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ponfilo.modelos.elementos;

import com.ponfilo.modelos.response.RespCliente;
import com.ponfilo.utilidades.Propiedades;

/**
 *
 * @author Leudis Wan Der Biest
 */
public class TipoDocumento {

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @return the extranjero
     */
    public boolean isExtranjero() {
        return extranjero;
    }

    /**
     * @param extranjero the extranjero to set
     */
    public void setExtranjero(boolean extranjero) {
        this.extranjero = extranjero;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the abreviacion
     */
    public String getAbreviacion() {
        return abreviacion;
    }

    /**
     * @param abreviacion the abreviacion to set
     */
    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public RespCliente validarDatosLogin() throws Exception {
        RespCliente respCLiente = new RespCliente();
        String camposRequeridos = "";
        if (codigo == null || codigo.isEmpty() || codigo.isBlank()) {
            if (descripcion == null || descripcion.isEmpty() || descripcion.isBlank()) {
                camposRequeridos += "descripcion";
            }
            if (abreviacion == null || abreviacion.isEmpty() || abreviacion.isBlank()) {                
                camposRequeridos += (camposRequeridos.equals("") ? "" : "; ") + "abreviacion";
            }
        } else {
            if ((descripcion == null || descripcion.isEmpty() || descripcion.isBlank()) && (abreviacion == null || abreviacion.isEmpty() || abreviacion.isBlank())) {
                camposRequeridos += "descripcion; abreviacion";
            }
        }
        if (!camposRequeridos.equals("")) {
            respCLiente.setMensaje(String.format(Propiedades.getMsjProp("api.validarlogin"), camposRequeridos));
            respCLiente.setConsultaEstado(false);
            respCLiente.setCodigoEstado(-1);
        }
        return respCLiente;
    }

    private String codigo;
    private String detalle;
    private String descripcion;
    private String abreviacion;
    private boolean extranjero = false;
    private int estado;//0: ACTIVO; -1: ELIMINADO - INACTIVO
}
