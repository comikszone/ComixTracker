/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.userdao;

import com.comicszone.entitynetbeans.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alexander
 */
@Stateless
public class UserBlockFacade extends AbstractUserFacade {

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public Result blockUser(String userNickName) {
        Users user = getUserWithNickname(userNickName);
        if (user == null) {
            return Result.NOT_FOUND;
        }
        if (user.getBanned()) {
            return Result.CANCELED;
        }
        
        user.setBanned(true);
        return Result.DONE;
    }
    
    public Result unblockUser(String userNickName) {
        Users user = getUserWithNickname(userNickName);
        if (user == null) {
            return Result.NOT_FOUND;
        }
        if (!user.getBanned()) {
            return Result.CANCELED;
        }
        
        user.setBanned(false);
        return Result.DONE;
    }

    public List<Users> getSomeUnblockedUsersWithRealNickname(String realNickname, int quantity) {
        if (quantity < 1) {
            return null;
        }
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findBannedOrUnbannedByRealNicknameStartsWith", Users.class);
        query.setParameter("realNickname", realNickname.toLowerCase() + "%");
        query.setParameter("banned", false);
        query.setMaxResults(quantity);
        return query.getResultList();
    }

    public List<Users> getSomeBlockedUsersWithRealNickname(String realNickname, int quantity) {
        if (quantity < 1) {
            return null;
        }
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findBannedOrUnbannedByRealNicknameStartsWith", Users.class);
        query.setParameter("realNickname", realNickname.toLowerCase() + "%");
        query.setParameter("banned", true);
        query.setMaxResults(quantity);
        return query.getResultList();
    }
    
    public enum Result {
        DONE, CANCELED, NOT_FOUND
    }
    
    public List<Users> getSomeUnblockedUsersWithNickname(String nickname, int quantity) {
        if (quantity < 1) {
            return null;
        }
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findBannedOrUnbannedByNicknameStartsWith", Users.class);
        query.setParameter("nickname", nickname.toLowerCase() + "%");
        query.setParameter("banned", false);
        query.setMaxResults(quantity);
        return query.getResultList();
    }
    
    public List<Users> getSomeBlockedUsersWithNickname(String nickname, int quantity) {
        if (quantity < 1) {
            return null;
        }
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findBannedOrUnbannedByNicknameStartsWith", Users.class);
        query.setParameter("nickname", nickname.toLowerCase() + "%");
        query.setParameter("banned", true);
        query.setMaxResults(quantity);
        return query.getResultList();
    }
}
