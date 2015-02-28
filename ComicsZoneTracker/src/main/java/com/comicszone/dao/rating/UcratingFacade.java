/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.rating;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entity.Ucrating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ajpyatakov
 */

@Stateless
public class UcratingFacade extends AbstractFacade<Ucrating> {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public UcratingFacade() {
        super(Ucrating.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
        
    public Ucrating findByUserAndComics(Integer userId, Integer comicsId) {
        TypedQuery<Ucrating> query =em.createNamedQuery("Ucrating.findByUserAndComics", Ucrating.class);
        query.setParameter("userId", userId);
        query.setParameter("comicsId", comicsId);
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
