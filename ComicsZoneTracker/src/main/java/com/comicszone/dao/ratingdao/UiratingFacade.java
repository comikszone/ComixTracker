/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.ratingdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entitynetbeans.Uirating;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ajpyatakov
 */

@Stateless
public class UiratingFacade extends AbstractFacade<Uirating> {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public UiratingFacade() {
        super(Uirating.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public Long getCountByIssue(Integer issueId) {
        TypedQuery<Long> query = em.createNamedQuery("Uirating.countByIssue", Long.class);
        query.setParameter("issueId", issueId);
        return query.getResultList().get(0);
    }
    
    public Uirating findByUserAndIssue(Integer userId, Integer issueId) {
        TypedQuery<Uirating> query =em.createNamedQuery("Uirating.findByUserAndIssue", Uirating.class);
        query.setParameter("userId", userId);
        query.setParameter("issueId", issueId);
        if (!query.getResultList().isEmpty())
            return query.getResultList().get(0);
        else return null;
    }
}
