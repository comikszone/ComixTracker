/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.ratingdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entitynetbeans.Uvrating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ajpyatakov
 */

@Stateless
public class UvratingFacade extends AbstractFacade<Uvrating> {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public UvratingFacade() {
        super(Uvrating.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Uvrating findByUserAndVolume(Integer userId, Integer volumeId) {
        TypedQuery<Uvrating> query =em.createNamedQuery("Uvrating.findByUserAndVolume", Uvrating.class);
        query.setParameter("userId", userId);
        query.setParameter("volumeId", volumeId);
        if (!query.getResultList().isEmpty())
            return query.getResultList().get(0);
        else return null;
    }
    
    public Double getAverageRating(Integer comicsId) {
        TypedQuery<Double> query = em.createNamedQuery("Ucrating.getAverageRating", Double.class);
        query.setParameter("comicsId", comicsId);
        return query.getResultList().get(0);
    }
    
}
