/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ArsenyPC
 */
    public class PersistenceUtil {

    private static final EntityManagerFactory entityManagerFactory;
    static {
                try {
                	Class.forName("org.postgresql.Driver");
                     entityManagerFactory =  Persistence.createEntityManagerFactory("ComicsZonePU"); 
                } catch (Throwable ex) {

                    System.err.println("Initial SessionFactory creation failed." + ex);
                    throw new ExceptionInInitializerError(ex);

                  }
    }

public static EntityManagerFactory getEntityManagerFactory() {
         return entityManagerFactory;
    }

}

