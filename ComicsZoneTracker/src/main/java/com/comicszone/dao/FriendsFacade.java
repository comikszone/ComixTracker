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
            if (friend.getUser1().equals(currentUser)) {
                userFriends.add(friend.getUser2());
            }
            else {
                userFriends.add(friend.getUser1());
            }
        }
        
        return userFriends;
    }
    
    public List<Users> getFolowers(Users currentUser) {
        
        TypedQuery<Friends> query =em.createNamedQuery("Friends.findFollowers", Friends.class);
        query.setParameter("user", currentUser);
        List<Friends> followers = query.getResultList();
        
        List<Users> userFollowers = new ArrayList<Users>();
        for (Friends follower : followers) {
            if (follower.getUser1().equals(currentUser)) {
                userFollowers.add(follower.getUser2());
            }
            else {
                userFollowers.add(follower.getUser1());
            }
        }
        return userFollowers;
    }
    
    public List<Users> getUnconfirmedFriends(Users currentUser) {
        TypedQuery<Friends> query =em.createNamedQuery("Friends.findUnconfirmedFriends", Friends.class);
        query.setParameter("user", currentUser);
        List<Friends> potentialFriends = query.getResultList();
        
        List<Users> userPotentialFriends = new ArrayList<Users>();
        for (Friends potentialFriend : potentialFriends) {
            if (potentialFriend.getUser1().equals(currentUser)) {
                userPotentialFriends.add(potentialFriend.getUser2());
            }
            else {
                userPotentialFriends.add(potentialFriend.getUser1());
            }
        }
        return userPotentialFriends;
    }
    
    public void addToFriends(Users currentUser, Users friendUser) {
        
        if (currentUser.equals(friendUser)) {
            return;
        }
        List<Friends> friends = findFriendsByUsers(currentUser,friendUser);
            
        if (friends.isEmpty()) {
            Friends friend = new Friends();
            friend.setUser1(currentUser);
            friend.setUser2(friendUser);
            friend.setUser1Subscribed(true);
            friend.setUser2Subscribed(false);
            
            create(friend);
            return;
        }
        
        Friends friend = friends.get(0);
        boolean isCurrentUserUser1 = friend.getUser1().equals(currentUser);
        
        if (friend.isUser1Subscribed() && friend.isUser2Subscribed()) {
            return;
        }
        
        if (isCurrentUserUser1) {
            friend.setUser1Subscribed(true);
        }
        else {
            friend.setUser2Subscribed(true);
        }
        edit(friend);
    }
    
    public void removeFromFriends(Users currentUser, Users friendUser) {
        
        List<Friends> friends = findFriendsByUsers(currentUser,friendUser);
        
        if (friends.isEmpty()) {
            return;
        }
        
        Friends friend = friends.get(0);
        boolean isCurrentUserUser1 = friend.getUser1().equals(currentUser);
        
        if (isCurrentUserUser1) {
            friend.setUser1Subscribed(false);
        }
        else {
            friend.setUser2Subscribed(false);
        }
        edit(friend);
    }
    
    private List<Friends> findFriendsByUsers(Users currentUser, Users friendUser) {
        
        TypedQuery<Friends> query =em.createNamedQuery("Friends.findByUsers", Friends.class);
        query.setParameter("user1", currentUser);
        query.setParameter("user2", friendUser);
        return query.getResultList();
    }

    
}
