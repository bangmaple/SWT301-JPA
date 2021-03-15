/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bangmaple.onion_architecture_demo.repositories.helper;

import javax.persistence.*;

/**
 *
 * @author bangmaple
 */

public class PersistenceManager {

    public static final boolean DEBUG = false;
    private static PersistenceManager INSTANCE = null;
    private final EntityManagerFactory emf;
    public static String PERSISTENCE_UNIT_NAME = "OnionArchPU";

    private PersistenceManager() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public static PersistenceManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersistenceManager();
        }
        return INSTANCE;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return getInstance().emf;
    }

    public void loseEntityManagerFactory() {
        if (this.emf != null) {
            this.emf.close();
        }
    }

}
