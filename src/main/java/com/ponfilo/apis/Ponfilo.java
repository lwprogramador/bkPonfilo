package com.ponfilo.apis;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Ponfilo extends Application {
   private Set<Class<?>> endPoints = new HashSet<>();

    public Ponfilo() {
        endPoints.add(Inicio.class);
        endPoints.add(Elementos.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return endPoints;
    }
}
