/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.primefaces.model.SortOrder;

/**
 *
 * @author ArsenyPC
 */
@Stateless
@LocalBean
@Path("/comics")
@Produces({"text/xml", "application/json"})
public class ComicsFacade extends AbstractFacade<Comics> implements Finder,SlideshowFacade,CatalogueFacade{
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
//    @Path("/comics")
    @GET
    @Produces("application/json")
    public List<Comics> findAll() {
        return super.findAll();
    }
    

    public ComicsFacade() {
        super(Comics.class);
    }
    
    @Override
    @Path("/{name}")
    @GET
    @Produces("application/json")
    public List<Comics> findByNameStartsWith(@PathParam("name") String name) {
            TypedQuery<Comics> query =em.createNamedQuery("Comics.findByNameStartsWith", Comics.class);
            query.setParameter("name", name.toLowerCase()+"%");
            query.setMaxResults(5);
            return query.getResultList();
        }
     @Override
    public List<Comics> get12Best() {
        TypedQuery<Comics> query =em.createNamedQuery("Comics.getComicsWithImages", Comics.class);
        query.setMaxResults(5);
        List<Comics> results = query.getResultList();
        return results;
    }
    
    @Override
    public List<Comics> findAllForCatalogue(Integer maxResult, String sortField, SortOrder sortOrder) {
        TypedQuery<Comics> query;
        if (sortField == null) {
            query = em.createNamedQuery("Comics.findAllForCatalogueSortByRatingDESC", Comics.class);
        }
        else if (sortField.equalsIgnoreCase("rating") && sortOrder == SortOrder.DESCENDING){
            query = em.createNamedQuery("Comics.findAllForCatalogueSortByRatingDESC", Comics.class);
        }
        else if (sortField.equalsIgnoreCase("rating") && sortOrder == SortOrder.ASCENDING){
            query = em.createNamedQuery("Comics.findAllForCatalogueSortByRatingASC", Comics.class);
        }
        else if (sortField.equalsIgnoreCase("name") && sortOrder == SortOrder.DESCENDING){
            query = em.createNamedQuery("Comics.findAllForCatalogueSortByNameDESC", Comics.class);
        }
        else {
            query = em.createNamedQuery("Comics.findAllForCatalogueSortByNameASC", Comics.class);
        }
        
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByNameAndRating(Integer maxResult, String name, Double rating) {
        TypedQuery<Comics> query = 
                em.createNamedQuery("Comics.findByNameAndRatingStartsWith", Comics.class);
        query.setMaxResults(maxResult);
        query.setParameter("name", name.toLowerCase() + "%");
        query.setParameter("rating", rating);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByRating(Integer maxResult, Double rating) {
        TypedQuery<Comics> query = 
                em.createNamedQuery("Comics.findByRatingBetween", Comics.class);
        query.setParameter("rating", rating);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByName(Integer maxResult, String name) {
        TypedQuery<Comics> query =em.createNamedQuery("Comics.findByNameStartsWith", Comics.class);
        query.setParameter("name", name.toLowerCase()+"%");
        query.setMaxResults(maxResult);
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


    
}
