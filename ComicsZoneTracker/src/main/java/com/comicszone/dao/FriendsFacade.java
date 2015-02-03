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
        
        TypedQuery<Friends> query = 
                em.createNamedQuery("Friends.findByUserIds", Friends.class);
        
        query.setParameter("user_id", currentUser.getUserId());
        query.setParameter("user1_id", friendUser.getUserId());
        
        List<Friends> result = query.getResultList();
        
        for(Friends friend : result) {
            System.err.println("\n" + friend + "\n");
        }
        
        return !query.getResultList().isEmpty();
    }
    
    public List<Users> getFriends(Users currentUser) {
        
        List<Friends> myFriends = currentUser.getFriendsList();
        List<Friends> myFriends1 = currentUser.getFriendsList1();
        
        List<Users> userFriends = new ArrayList<Users>();
        
        for(Friends currentFriend : myFriends) {
            userFriends.add(currentFriend.getUsers1());
        }
        
        for(Friends currentFriend : myFriends1) {
            userFriends.add(currentFriend.getUsers());
        }
        
        return userFriends;
    }
    
    public void addToFriends(Users currentUser, Users friendUser) {
        
        Friends friend = new Friends();
        friend.setUsers(currentUser);
        friend.setUsers1(friendUser);
        friend.setIsConfirmed(false);
        
        if (!areFriends(currentUser, friendUser) && !currentUser.equals(friendUser)) {
            
            currentUser.addFriendToFriendsList(friend);
            friendUser.addFriendToFriendsList1(friend);
        
            create(friend);  
        }
    }
}
