/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ponfilo.modelos.response;

/**
 *
 * @author Leudis Wan Der Biest
 */
public class RespCliente {

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the codigoestado
     */
    public int getCodigoEstado() {
        return codigoestado;
    }

    /**
     * @param codigoestado the codigoestado to set
     */
    public void setCodigoEstado(int codigoestado) {
        this.codigoestado = codigoestado;
    }

    /**
     * @return the consultaestado
     */
    public boolean getConsultaEstado() {
        return consultaestado;
    }

    /**
     * @param consultaestado the consultaestado to set
     */
    public void setConsultaEstado(boolean consultaestado) {
        this.consultaestado = consultaestado;
    }

    /**
     * @return the contenido
     */
    public Object getContenido() {
        return contenido;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(Object contenido) {
        this.contenido = contenido;
    }
    
    
    private String mensaje;
    private String url;
    private int codigoestado;
    private boolean consultaestado = true;
    private Object contenido;
}
