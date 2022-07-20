/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ponfilo.modelos.elementos;

import java.util.ArrayList;

/**
 *
 * @author Leudis Wan Der Biest
 */
public class ConfiguracionAplicacion {

    /**
     * @return the tposDocumentos
     */
    public ArrayList<TipoDocumento> getTposDocumentos() {
        return tposDocumentos;
    }

    /**
     * @param tposDocumentos the tposDocumentos to set
     */
    public void setTposDocumentos(ArrayList<TipoDocumento> tposDocumentos) {
        this.tposDocumentos = tposDocumentos;
    }
        
    
    private ArrayList<TipoDocumento> tposDocumentos;
}
