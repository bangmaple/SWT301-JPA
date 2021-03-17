package com.tientt.demojpa_1m.daos;

import com.tientt.demojpa_1m.entities.AuthorEntity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.persistence.*;
import java.util.List;

public class AuthorDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("DemoJPAPU");

    static public AuthorDAO instance;

    private AuthorDAO() {

    }

    public static AuthorDAO getInstance() {
        if (instance == null) {
            instance = new AuthorDAO();
        }
        return instance;
    }

    public List<AuthorEntity> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT r FROM AuthorEntity r", AuthorEntity.class).getResultList();
    }

    public boolean update(AuthorEntity entity){
        boolean flag = false;
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.merge(entity);
            et.commit();
            flag = true;
        } catch (Exception e) {
            et.rollback();
        }
        return flag;
    }

    public boolean remove(Integer id) {
        boolean flag = false;
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            AuthorEntity entity = em.find(AuthorEntity.class, id);
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
            et.commit();
            flag = true;
        } catch (Exception e) {
            et.rollback();
        }
        return flag;
    }


    public boolean add(AuthorEntity entity) {
        boolean flag = false;

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
            flag = true;
        } catch (PersistenceException e) {
            et.rollback();
            e.printStackTrace();
        }
        return flag;
    }

    public AuthorEntity getAuthorByName(String name){
        //TODO: implement latter
        throw new NotImplementedException();
    }
}
