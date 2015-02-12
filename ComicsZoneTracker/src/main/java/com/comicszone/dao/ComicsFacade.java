/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.primefaces.model.SortOrder;

/**
 *
 * @author ArsenyPC
 */
@Stateless
@LocalBean
//@Path("/comics")
//@Produces({"text/xml", "application/json"})
public class ComicsFacade extends AbstractFacade<Comics> implements Finder,SlideshowInterface,CatalogueInterface,ProgressInterface{
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComicsFacade() {
        super(Comics.class);
    }
        
    @Override
    public List<Comics> findByNameStartsWith(String name) {
            TypedQuery<Comics> query =em.createNamedQuery("Comics.findByNameStartsWith", Comics.class);
            query.setParameter("name", name.toLowerCase()+"%");
            query.setMaxResults(5);
            return query.getResultList();
        }

    @Override
    public List<Comics> getBest(Integer quantity) {
        TypedQuery<Comics> query =em.createNamedQuery("Comics.getComicsWithImages", Comics.class);
        query.setMaxResults(quantity);
        List<Comics> results = query.getResultList();
        return results;
    }
    
    @Override
    public List<Comics> findAllForCatalogue(Integer first, Integer pageSize, 
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c ORDER BY c."
                + sortField + " " + sortOrderString);
        
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByNameAndRating(Integer first, Integer pageSize, String name, 
            Double rating, String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE LOWER(c.name) LIKE :name "
                + "AND c.rating BETWEEN :rating AND :rating+1 "
                + "ORDER BY c." + sortField + " " + sortOrderString);
        
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        query.setParameter("name", name.toLowerCase() + "%");
        query.setParameter("rating", rating);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByRating(Integer first, Integer pageSize, Double rating,
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE c.rating BETWEEN :rating AND :rating+1 "
                + "ORDER BY c." + sortField + " " + sortOrderString);
        
        query.setParameter("rating", rating);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByName(Integer first, Integer pageSize, String name,
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE LOWER(c.name) LIKE :name "
                + "ORDER BY c." + sortField + " " + sortOrderString);
        
        query.setParameter("name", name.toLowerCase()+"%");
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    
    @Override
    public long getComicsCount() {
        TypedQuery<Long> query = em.createNamedQuery("Comics.count", Long.class);
        List<Long> queryResult = query.getResultList();
        return queryResult.get(0);
    }

    @Override
    public long getComicsCountFoundByNameAndRating(String name, Double rating) {
        TypedQuery<Long> query = em.createNamedQuery("Comics.countFoundByNameAndRating", Long.class);
        query.setParameter("name", name.toLowerCase() + "%");
        query.setParameter("rating", rating);
        return query.getResultList().get(0);
    }

    @Override
    public long getComicsCountFoundByName(String name) {
        TypedQuery<Long> query =em.createNamedQuery("Comics.countFoundByName", Long.class);
        query.setParameter("name", name.toLowerCase()+"%");
        return query.getResultList().get(0);
    }

    @Override
    public long getComicsCountFoundByRating(Double rating) {
        TypedQuery<Long> query =em.createNamedQuery("Comics.countFoundByRating", Long.class);
        query.setParameter("rating", rating);
        return query.getResultList().get(0);
    }
    
    public List<Comics> findAll(int maxResult)
    {
        TypedQuery<Comics> query=em.createNamedQuery("Comics.findAllAscId",Comics.class);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByUserInProgress(Integer userId) {
        TypedQuery<Comics> query = em.createNamedQuery("Comics.findByUserInProgress", Comics.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Long getTotalIssueCount(Integer comicsId) {
        TypedQuery<Long> query = em.createNamedQuery("Comics.getTotalIssueCount", Long.class);
        query.setParameter("comicsId", comicsId);
        return query.getResultList().get(0);
    }

    @Override
    public Long getMarkedIssueCount(Integer comicsId, Integer userId) {
        TypedQuery<Long> query = em.createNamedQuery("Comics.getMarkedIssueCount", Long.class);
        query.setParameter("comicsId", comicsId);
        query.setParameter("userId", userId);
        return query.getResultList().get(0);
    }
    
    public List<Comics> findByChecking(boolean isChecked) {
        TypedQuery<Comics> query = em.createNamedQuery("Comics.findByChecking", Comics.class);
        query.setParameter("isChecked", isChecked);
        return query.getResultList();
    }
}
