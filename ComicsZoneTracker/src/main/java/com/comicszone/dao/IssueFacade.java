/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Volume;
import java.util.List;
import javax.ejb.EJBException;
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
    
    public List<Issue> findMarkedByUserAndComics(Integer comicsId, Integer userId) {
        TypedQuery<Issue> query = em.createNamedQuery("Issue.findMarkedByUserAndComics", Issue.class);
        query.setParameter("comicsId", comicsId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    public List<Issue> findByComics(Integer comicsId) {
        TypedQuery<Issue> query = em.createNamedQuery("Issue.findByComics", Issue.class);
        query.setParameter("comicsId", comicsId);
        return query.getResultList();
    }

    public List<Issue> findByChecking(boolean isChecked) {
        TypedQuery<Issue> query =em.createNamedQuery("Issue.findByChecking", Issue.class);
        query.setParameter("isChecked", isChecked);
        return query.getResultList();
    }
    
    public List<Issue> findByName(String name) {
        TypedQuery<Issue> query = em.createNamedQuery("Issue.findByName", Issue.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    public void createNew(String title, String description, String image, String date, String source, Volume volumeId) {
        if (findByName(title).isEmpty()) {
            Issue issue = new Issue(title, description, image, date, source, volumeId);
            create(issue);
        } else {
            throw new EJBException();
        }
    }
}