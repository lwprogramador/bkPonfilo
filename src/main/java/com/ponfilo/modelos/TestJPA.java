package com.ponfilo.modelos;


import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="public.tbl_test_jpa")
public class TestJPA implements Serializable {

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long numero;

    //Metodo 1 para generar una columna (AGREGAR propiedad name en column para saber a que columna pertence)
    @Column(name="descripcion")
    private String descripcion;

    //Metodo 1 para generar una columna (AGREGAR SIN propiedad name en column se asume el nombre de la varibale)
    @Column
    private String detalle;

}
