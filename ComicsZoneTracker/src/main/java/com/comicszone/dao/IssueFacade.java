/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Issue;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alexander Pyatakov
 */
@Stateless
@LocalBean
public class IssueFacade extends AbstractFacade<Issue> implements Finder {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IssueFacade() {
        super(Issue.class);
    }

    @Override
    public List<Issue> findByNameStartsWith(String name) {
        TypedQuery<Issue> query =em.createNamedQuery("Issue.findByNameStartsWith", Issue.class);
        query.setParameter("name", name.toLowerCase()+"%");
        query.setMaxResults(5);
        return query.getResultList();
    }

}
