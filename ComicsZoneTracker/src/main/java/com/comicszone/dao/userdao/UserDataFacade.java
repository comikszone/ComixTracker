package com.comicszone.dao.userdao;

import com.comicszone.entitynetbeans.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserDataFacade extends AbstractUserFacade {

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Users> findUserByEmail(String email)
    {
        TypedQuery<Users> query=(TypedQuery<Users>) em.createNamedQuery("Users.findByEmail");
        query.setParameter("email", email);
        return query.getResultList();
    }
    public Users findUserByUid(String uid)
    {
        TypedQuery<Users> query=(TypedQuery<Users>) em.createNamedQuery("Users.findByRecoveryPasswordId");
        query.setParameter("uid", uid);
        List<Users> users= query.getResultList();
        if (users==null || users.isEmpty())
            return null;
        return users.get(0);
    }
}
