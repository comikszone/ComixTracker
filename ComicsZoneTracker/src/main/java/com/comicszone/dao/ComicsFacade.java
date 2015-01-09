/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Content;
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

/**
 *
 * @author ArsenyPC
 */
@Stateless
@LocalBean
@Path("/comics")
@Produces({"text/xml", "application/json"})
public class ComicsFacade extends AbstractFacade<Comics> implements Finder,SlideshowFacade{
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
        query.setMaxResults(12);
        List<Comics> results = query.getResultList();
        return results;
    }
}
