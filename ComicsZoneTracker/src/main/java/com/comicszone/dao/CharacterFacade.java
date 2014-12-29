/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.AjaxComicsCharacter;
import com.comicszone.entitynetbeans.Character;
import com.comicszone.entitynetbeans.Comics;
import java.util.List;
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
@Path("/characters")
@Produces({"text/xml", "application/json"})
public class CharacterFacade extends AbstractFacade<Character> implements Finder{
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CharacterFacade() {
        super(Character.class);
    }

    @Override
//    @Path("/characters")
    @GET
    @Produces("application/json")
    public List<Character> findAll() {
        return super.findAll();
    }

    
    @Override
    @Path("/{name}")
    @GET
    @Produces("application/json")
    public List<Character> findByNameStartsWith(@PathParam("name") String name) {
            TypedQuery<Character> query =em.createNamedQuery("Character.findByNameStartsWith", Character.class);
            query.setParameter("name", name.toLowerCase()+"%");
            query.setMaxResults(5);
            return query.getResultList();
        }
    
}
