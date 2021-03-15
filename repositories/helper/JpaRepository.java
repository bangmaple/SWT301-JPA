package com.bangmaple.onion_architecture_demo.repositories.helper;

import lombok.SneakyThrows;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public abstract class JpaRepository<K, V> implements ICRUDRepository<K, V> {

    private final EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    @SneakyThrows //checked-exception
    @SuppressWarnings("unchecked")
    public CompletableFuture<Optional<K>> find(V v) {
        //Reflection API
        Class<K> clazz = (Class<K>) ((ParameterizedType) getClass()
                .getSuperclass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return CompletableFuture.supplyAsync(() -> Optional
                .ofNullable(getEntityManager().find(clazz, v)));
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public CompletableFuture<Optional<List<K>>> findAll() {
        //Java Persistence Query Language
        //SQL Dialect
        //JPA (ORM)
        Class<K> clazz = (Class<K>) ((ParameterizedType) getClass()
                .getSuperclass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        return CompletableFuture.supplyAsync(() -> Optional.
                ofNullable(emf.createEntityManager()
                        //SELECT * FROM Users
                        .createQuery("select t from "
                                + clazz.getName() + " t", clazz)
                        .getResultList()));
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Void> add(K k) {
        return CompletableFuture.runAsync(() -> {
            EntityManager em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                em.persist(k);
                //INSERT INTO Users VALUES (a,b,c,x,y,z)
                et.commit();
            } catch (PersistenceException e) {
                et.rollback();
                e.printStackTrace();
            }
        });
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Void> update(K k) {
        return CompletableFuture.runAsync(() -> {
            EntityManager em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                em.merge(k);
                et.commit();
            } catch (PersistenceException e) {
                et.rollback();
                e.printStackTrace();
            }
        });
    }

    @Override
    @SneakyThrows
    public CompletableFuture<Void> delete(K k) {
        return CompletableFuture.runAsync(() -> {
            EntityManager em = getEntityManager();
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                if (!em.contains(k)) {
                    em.remove(em.merge(k));
                    et.commit();
                }
                em.remove(k);
                et.commit();
            } catch (PersistenceException e) {
                et.rollback();
                e.printStackTrace();
            }
        });
    }
}
