/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entity.Realm;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ArsenyPC
 */
@Stateless
public class RealmFacade extends AbstractFacade<Realm> {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RealmFacade() {
        super(Realm.class);
    }
    
    public List<Realm> findByName(String name) {
        TypedQuery<Realm> query = em.createNamedQuery("Realm.findByName", Realm.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
}
