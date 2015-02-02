/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.userdao;

import com.comicszone.entitynetbeans.Friends;
import com.comicszone.entitynetbeans.Users;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
        List<Friends> friends = currentUser.getFriendsList1();
        List<Users> userFriends = new ArrayList<Users>();
        
        for(Friends currentFriend : friends) {
            userFriends.add(currentFriend.getUsers());
        }
        //List<Users> friends = currentUser.getUsersList1();
        return userFriends;
    }
    
    public void addToFriends(Users currentUser, Users friendUser) {
        System.err.println("********TRANSACTION");
        Friends friend = new Friends();
        friend.setUsers(currentUser);
        friend.setUsers1(friendUser);
        friend.setIsConfirmed(false);
        
        currentUser.addFriendToFriendsList(friend);
        edit(currentUser);
    }
    
    public List<Users> getFriendsWithNicknameStartsWith(Users currentUser) {
            return null;
    }
    
    public long getUsersCountFilteredByNickname(String nickName) {
        return 1;
    }
}
