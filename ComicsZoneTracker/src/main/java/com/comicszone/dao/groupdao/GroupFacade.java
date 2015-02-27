package com.comicszone.dao.groupdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entitynetbeans.UserGroup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class GroupFacade extends AbstractFacade<UserGroup> {
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public GroupFacade() {
        super(UserGroup.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
