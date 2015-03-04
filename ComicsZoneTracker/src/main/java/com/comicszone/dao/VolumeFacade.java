/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entity.Comics;
import com.comicszone.entity.Volume;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ArsenyPC
 */
@Stateless
public class VolumeFacade extends AbstractFacade<Volume> {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VolumeFacade() {
        super(Volume.class);
    }
   
    public List<Volume> findByChecking(boolean isChecked) {
        TypedQuery<Volume> query = em.createNamedQuery("Volume.findByChecking", Volume.class);
        query.setParameter("isChecked", isChecked);
        return query.getResultList();
    }
    
    public List<Volume> findByName(String name) {
        TypedQuery<Volume> query = em.createNamedQuery("Volume.findByName", Volume.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
    
    public void createNew(String title, String description, String image, String source, Comics comicsId) {
        if (findByName(title).isEmpty()) {
            Volume volume = new Volume(title, description, image, source, comicsId);
            create(volume);
        } else {
            throw new EJBException();
        }
    }
    
}
