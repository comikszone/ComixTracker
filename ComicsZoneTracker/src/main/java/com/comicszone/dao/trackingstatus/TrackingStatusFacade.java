/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.trackingstatus;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entity.UserTrackingStatus;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author GuronPavorro
 */
@Stateless
@LocalBean
public class TrackingStatusFacade extends AbstractFacade<UserTrackingStatus> {
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    private UserTrackingStatus record;

    public TrackingStatusFacade() {
        super(UserTrackingStatus.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public void updateStatus(UserTrackingStatus record, Integer status) {
        record.setStatus(status);
        edit(record);
    }
    
    public void init(Integer userId, Integer comicsId) {
        record = findByUserAndComics(userId, comicsId);
                if (record == null) {
                    record = new UserTrackingStatus(userId, comicsId);
                    record.setStatus(0);
                    create(record);
                }
    }
    
    public Integer getStatus() {
        return record.getStatus();
    }
    
    public UserTrackingStatus findByUserAndComics(Integer userId, Integer comicsId) {
        TypedQuery<UserTrackingStatus> query =em.createNamedQuery("UserTrackingStatus.findByUserAndComics", UserTrackingStatus.class);
        query.setParameter("userId", userId);
        query.setParameter("comicsId", comicsId);
        if (!query.getResultList().isEmpty())
            return query.getResultList().get(0);
        else return null;
    }

    /**
     * @return the record
     */
    public UserTrackingStatus getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(UserTrackingStatus record) {
        this.record = record;
    }
    
}
