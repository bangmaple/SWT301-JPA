package com.tientt.demojpa_1m.daos;

import com.tientt.demojpa_1m.entities.BookEntity;

import javax.persistence.*;
import java.util.List;

public class BookDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("DemoJPAPU");

    static public BookDAO instance;

    private BookDAO() {

    }

    public static BookDAO getInstance() {
        if (instance == null) {
            instance = new BookDAO();
        }
        return instance;
    }

    public List<BookEntity> findAll(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT r FROM BookEntity r", BookEntity.class).getResultList();
    }

    public boolean update(BookEntity entity){
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
            BookEntity entity = em.find(BookEntity.class, id);
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


    public boolean add(BookEntity entity) {
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


}
