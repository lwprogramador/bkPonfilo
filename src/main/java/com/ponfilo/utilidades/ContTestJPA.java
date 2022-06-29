package com.ponfilo.utilidades;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Disposes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ContTestJPA {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    public void init(){
        if(entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("ContTestJPA");
        }
    }

    @Produces
    @Default
    public EntityManagerFactory createEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Produces
    @Default
    @Dependent
    private EntityManager createEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager entityManager){
        if(entityManager.isOpen()){
            entityManager.close();
        }
    }
}
