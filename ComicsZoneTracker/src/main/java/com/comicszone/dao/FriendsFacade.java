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
    
    public List<Users> getFriends(Users currentUser) {
        
        TypedQuery<Friends> query =em.createNamedQuery("Friends.findFriends", Friends.class);
        query.setParameter("user", currentUser);
        List<Friends> friends = query.getResultList();
        
        List<Users> userFriends = new ArrayList<Users>();
        for (Friends friend : friends) {
            if (friend.getUsers().equals(currentUser)) {
                userFriends.add(friend.getUsers1());
            }
            else {
                userFriends.add(friend.getUsers());
            }
        }
        
        return userFriends;
    }
    
    public void addToFriends(Users currentUser, Users friendUser) {
        
        List<Friends> friends = findFriendsByUsers(currentUser,friendUser);
        if (friends.isEmpty()) {
            Friends friend = new Friends();
            friend.setUsers(currentUser);
            friend.setUsers1(friendUser);
            friend.setIsConfirmed(true);
            friend.setAreFriends(true);
            
            currentUser.addFriendToFriendsList(friend);
            friendUser.addFriendToFriendsList1(friend);
        
            create(friend); 
        }
        else if (!friends.get(0).areFriends()) {
            Friends friend = friends.get(0);
            friend.setAreFriends(true);
            
            currentUser.addFriendToFriendsList(friend);
            friendUser.addFriendToFriendsList1(friend);
            
            edit(friend);
        }
    }
    
    public void removeFromFriends(Users currentUser, Users friendUser) {
        
        List<Friends> friends = findFriendsByUsers(currentUser,friendUser);
        if (!friends.isEmpty() && friends.get(0).areFriends()) {
            Friends friend = friends.get(0);
            
            currentUser.removeFriendFromFriendsList(friend);
            friendUser.removeFriendFromFriendsList1(friend);
            
            friend.setAreFriends(false);
            
            edit(friend);
        }
    }
    
    private List<Friends> findFriendsByUsers(Users currentUser, Users friendUser) {
        
        TypedQuery<Friends> query =em.createNamedQuery("Friends.findByUsers", Friends.class);
        query.setParameter("user", currentUser);
        query.setParameter("user1", friendUser);
        return query.getResultList();
    }
}
