/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Users;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.json.simple.JSONArray;

/**
 *
 * @author ajpyatakov
 */

@Stateless
@LocalBean
@Path("/progress")
public class ProgressFacade extends AbstractFacade<Users> implements ProgressInterface {
    
    @EJB
    private UserDataFacade usersFacade;
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public ProgressFacade() {
        super(Users.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("/{userId}/")
    public String getReadComics(@PathParam("userId") String Id) {
        Integer userId = Integer.parseInt(Id);
        List<Comics> readComics = findByUserInProgress(userId);
        JSONArray json = new JSONArray();
        for (Comics comics: readComics) {
            Map map = new HashMap();
            map.put("id", comics.getId());
            map.put("name", comics.getName());
            map.put("marked", getMarkedIssueCount(comics.getId(), userId));
            map.put("total", getTotalIssueCount(comics.getId()));
            json.add(map);
        }
        return json.toJSONString();
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
    
}
