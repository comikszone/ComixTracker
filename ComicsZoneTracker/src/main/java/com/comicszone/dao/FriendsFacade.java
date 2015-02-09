/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Friends;
import com.comicszone.entitynetbeans.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Eschenko_DA
 */
@Stateless
public class FriendsFacade extends AbstractFacade<Friends> {
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public FriendsFacade() {
        super(Friends.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    private boolean areFriends(Users currentUser, Users friendUser) {
        
        return getFriends(currentUser).contains(friendUser);
    }
    
    private boolean wereFriends(Users currentUser, Users friendUser) {
        
        List<Friends> myFriends = currentUser.getFriendsList();
        List<Friends> myFriends1 = currentUser.getFriendsList1();
        
        List<Users> userFriends = new ArrayList<Users>();
        
        for(Friends currentFriend : myFriends) {
            userFriends.add(currentFriend.getUsers1());
        }
        
        for(Friends currentFriend : myFriends1) {
            userFriends.add(currentFriend.getUsers());
        }
        
        return userFriends.contains(friendUser);
    }
    
    public List<Users> getFriends(Users currentUser) {
        
        List<Friends> myFriends = currentUser.getFriendsList();
        List<Friends> myFriends1 = currentUser.getFriendsList1();
        
        List<Users> userFriends = new ArrayList<Users>();
        
        for(Friends currentFriend : myFriends) {
            if (currentFriend.areFriends()) {
                userFriends.add(currentFriend.getUsers1());
            }
        }
        
        for(Friends currentFriend : myFriends1) {
            if (currentFriend.areFriends()) {
                userFriends.add(currentFriend.getUsers());
            }
        }
        
        return userFriends;
    }
    
    public void addToFriends(Users currentUser, Users friendUser) {
        
        Friends friend = new Friends();
        friend.setUsers(currentUser);
        friend.setUsers1(friendUser);
        friend.setIsConfirmed(true);
        friend.setAreFriends(true);
        
        if (!areFriends(currentUser, friendUser) && !currentUser.equals(friendUser)) {
            
            currentUser.addFriendToFriendsList(friend);
            friendUser.addFriendToFriendsList1(friend);
        
            create(friend);  
        }
    }
    
    public void removeFromFriends(Users currentUser, Users friendUser) {
        
        if (areFriends(currentUser, friendUser)) {
            TypedQuery<Friends> query =em.createNamedQuery("Friends.findByUserIds", Friends.class);
            query.setParameter("user_id", currentUser.getUserId());
            query.setParameter("user1_id", friendUser.getUserId());
            Friends friend = query.getResultList().get(0);
            friend.setAreFriends(false);
            
            currentUser.removeFriendFromFriendsList(friend);
            friendUser.removeFriendFromFriendsList1(friend);
        }
    }
}
