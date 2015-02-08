/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.userdao;

import com.comicszone.dao.FriendsFacade;
import com.comicszone.entitynetbeans.Friends;
import com.comicszone.entitynetbeans.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Eschenko_DA
 */
@Stateless
public class UserFriendsFacade extends AbstractUserFacade {

    @EJB
    private FriendsFacade friendsFacade;
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager entityManager;
    
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
    
    
    
   

    
    public List<Users> getFriendsWithNicknameStartsWith(Users currentUser) {
            return null;
    }
    
    public long getUsersCountFilteredByNickname(String nickName) {
        return 1;
    }
}
