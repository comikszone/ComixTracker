/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entity.Comics;
import com.comicszone.entity.Imprint;
import com.comicszone.entity.Publisher;
import java.util.List;
import javax.ejb.EJBException;
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
public class ComicsFacade extends AbstractFacade<Comics> implements Finder, SlideshowInterface, CatalogueInterface {
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
        TypedQuery<Comics> query =em.createNamedQuery("Comics.getBestComicsWithImages", Comics.class);
        query.setMaxResults(quantity);
        List<Comics> results = query.getResultList();
        return results;
    }
    
    @Override
    public List<Comics> findAllForCatalogue(Integer first, Integer pageSize, 
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        String otherOrderString = sortOrderString.equals("ASC") ? "DESC" : "ASC";
        
        Query query = em.createQuery("SELECT c FROM Comics c WHERE c.isChecked = true ORDER BY c."
                + sortField + " " + sortOrderString 
                + ",c.Id " + otherOrderString);
        
        
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        
        return query.getResultList();
    }

    @Override
    public List<Comics> findByNameAndRating(Integer first, Integer pageSize, String name, 
            Double rating, String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        String otherOrderString = sortOrderString.equals("ASC") ? "DESC" : "ASC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE LOWER(c.name) LIKE :name "
                + "AND c.rating >= :rating "
                + "AND c.isChecked = true "
                + "ORDER BY c." + sortField + " " + sortOrderString 
                + ",c.Id " + otherOrderString);
        
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
        String otherOrderString = sortOrderString.equals("ASC") ? "DESC" : "ASC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE c.rating >= :rating "
                + "AND c.isChecked = true "
                + "ORDER BY c." + sortField + " " + sortOrderString 
                + ",c.Id " + otherOrderString);
        
        query.setParameter("rating", rating);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        
        return query.getResultList();
    }

    @Override
    public List<Comics> findByName(Integer first, Integer pageSize, String name,
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        String otherOrderString = sortOrderString.equals("ASC") ? "DESC" : "ASC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE LOWER(c.name) LIKE :name "
                + "AND c.isChecked = true "
                + "ORDER BY c." + sortField + " " + sortOrderString 
                + ",c.Id " + otherOrderString);
        
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
    
    public List<Comics> findByChecking(boolean isChecked) {
        TypedQuery<Comics> query = em.createNamedQuery("Comics.findByChecking", Comics.class);
        query.setParameter("isChecked", isChecked);
        return query.getResultList();
    }
    
    public List<Comics> findByName(String name) {
        TypedQuery<Comics> query = em.createNamedQuery("Comics.findByName", Comics.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    public void createNew(String title, String description, String image, Publisher publisher, Imprint imprint, String source) {
        if (findByName(title).isEmpty()) {
            Comics comic = new Comics(title, description, image, publisher, imprint, source);
            create(comic);
        } else {
            throw new EJBException();
        }
    }
}
