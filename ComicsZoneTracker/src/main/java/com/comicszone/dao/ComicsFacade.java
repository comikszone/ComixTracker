/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Imprint;
import com.comicszone.entitynetbeans.Publisher;
import com.comicszone.entitynetbeans.Volume;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
//import org.json.simple.JSONArray;
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
    
    @Path("/comicsid/{id}")
    @GET
    @Produces("application/json")
    public String findByComicsId(@PathParam("id") String id) {
        try {
            Comics comics=find(Integer.parseInt(id));
            Map map=new HashMap();
            List<Volume> volumes=comics.getVolumeList();
            JSONArray array=new JSONArray();
            for (Volume v:volumes)
            {
                Map mapValume=new HashMap();
                mapValume.put("volumeId", v.getVolumeId());
                mapValume.put("name", v.getName());
                array.add(mapValume);
            }
            Publisher publisher=comics.getPublisherId();
            Map mapPublisher=new HashMap();
            mapPublisher.put("publisherId", publisher.getPublisherId());
            mapPublisher.put("name", publisher.getName());
            Imprint imprint=comics.getImprintId();
            Map mapImprint=new HashMap();
            mapImprint.put("imprintId", imprint.getImprintId());
            mapImprint.put("name", imprint.getName());
            StringWriter out = new StringWriter();
            map.put("id", comics.getId());
            map.put("name", comics.getName());
            map.put("description", comics.getDescription());
            map.put("startDate", comics.getStartDate());
            map.put("endDate", comics.getEndDate());
            map.put("image", comics.getImage());
            map.put("imprintId", mapImprint);
            map.put("inProgress", comics.getInProgress());
            map.put("publisherId", mapPublisher);
            map.put("rating", comics.getRating());
            map.put("votes", comics.getVotes());
//            for (Field field : comics.getClass().getDeclaredFields()) {
//                field.setAccessible(true);
//                Object value = field.get(comics); 
//                map.put(field.getName(), value);
//            }
            map.put("volumeList", array);
            JSONValue.writeJSONString(map, out);
            return out.toString();
        } catch (IOException ex) {
            Logger.getLogger(ComicsFacade.class.getName()).log(Level.SEVERE, null, ex);
        return "";
       }
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
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c ORDER BY " +
            "CASE WHEN c." + sortField + " IS NULL THEN 1 ELSE 0 END, " + 
            "c." + sortField + " " + sortOrderString);
        
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByNameAndRating(Integer maxResult, String name, 
            Double rating, String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE LOWER(c.name) LIKE :name "
                + "AND c.rating BETWEEN :rating AND :rating+1 "
                + "ORDER BY CASE "
                + "WHEN c." + sortField + " IS NULL THEN 1 ELSE 0 END, " 
                + "c." + sortField + " " + sortOrderString);
        
        
        query.setMaxResults(maxResult);
        query.setParameter("name", name.toLowerCase() + "%");
        query.setParameter("rating", rating);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByRating(Integer maxResult, Double rating,
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE c.rating BETWEEN :rating AND :rating+1 "
                + "ORDER BY CASE "
                + "WHEN c." + sortField + " IS NULL THEN 1 ELSE 0 END, " 
                + "c." + sortField + " " + sortOrderString);
        
        query.setParameter("rating", rating);
        query.setMaxResults(maxResult);
        return query.getResultList();
    }

    @Override
    public List<Comics> findByName(Integer maxResult, String name,
            String sortField, SortOrder sortOrder) {
        
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        
        Query query = em.createQuery("SELECT c FROM Comics c "
                + "WHERE LOWER(c.name) LIKE :name "
                + "ORDER BY CASE "
                + "WHEN c." + sortField + " IS NULL THEN 1 ELSE 0 END, " 
                + "c." + sortField + " " + sortOrderString);
        
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
