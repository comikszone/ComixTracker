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

/**
 *
 * @author Eschenko_DA
 */
@Stateless
public class UserFriendsFacade extends AbstractUserFacade {

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    public List<Users> getFriends(Users currentUser) {
        List<Users> friends = currentUser.getUsersList1();
        return friends;
    }
    
    public void addToFriends(Users currentUser, Users friendUser) {
        currentUser.getUsersList1().add(friendUser);
        friendUser.getUsersList1().add(currentUser);
    }
}
