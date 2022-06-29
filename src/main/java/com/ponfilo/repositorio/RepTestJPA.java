package com.ponfilo.repositorio;


import com.ponfilo.modelos.TestJPA;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class RepTestJPA {
    @Inject
    @Default
    EntityManager entityManager;

    public TestJPA buscarPorNumero(Long numero){
        return entityManager.find(TestJPA.class, numero);
    }

    public List<TestJPA> listarRegistros(){
        return entityManager.createQuery("SELECT t FROM TestJPA t", TestJPA.class).getResultList();
    }

    public void guardar(TestJPA obj){
        entityManager.persist(obj);
    }

    public void actualizar(TestJPA obj){
        entityManager.merge(obj);
    }

    public void eliminarRegistro(TestJPA obj){
        entityManager.remove(obj);
    }
}
