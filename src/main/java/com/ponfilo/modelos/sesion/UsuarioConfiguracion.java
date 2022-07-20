/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ponfilo.modelos.sesion;

/**
 *
 * @author Leudis Wan Der Biest
 */
public class UsuarioConfiguracion {
    public UsuarioConfiguracion(){
        
    }
    
    public UsuarioConfiguracion(Usuario usr){
        this.datosUsuario = usr;
    }

    /**
     * @return the datosUsuario
     */
    public Usuario getDatosUsuario() {
        return datosUsuario;
    }

    /**
     * @param datosUsuario the datosUsuario to set
     */
    public void setDatosUsuario(Usuario datosUsuario) {
        this.datosUsuario = datosUsuario;
    }
    
    
    
    private Usuario datosUsuario;
    
    
}
