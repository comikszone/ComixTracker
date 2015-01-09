/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.userdao;

import com.comicszone.entitynetbeans.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alexander
 */
@Stateless
public class UserBlockDao extends AbstractUserFacade {

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
    
    public enum Result {
        DONE, CANCELED, NOT_FOUND
    }
}
