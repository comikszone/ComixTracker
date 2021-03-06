/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.dao.news.FriendsNewsFacade;
import com.comicszone.entity.Friends;
import com.comicszone.entity.FriendshipStatus;
import com.comicszone.entity.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
    
    @EJB
    private FriendsNewsFacade newsFacade;

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
    
    public List<Users> getFollowers(Users currentUser) {
        
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
        //query.setParameter(1, currentUser.getUserId());
        //query.setParameter(2, currentUser.getUserId());
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
            friend.setStatus(FriendshipStatus.user1_subscribed);
            
            create(friend);
            newsFacade.createNews(friend, friendUser, Boolean.FALSE);
            newsFacade.createNews(friend, currentUser, Boolean.TRUE);
            return;
        }
        
        Friends friend = friends.get(0);
        boolean isCurrentUserUser1 = friend.getUser1().equals(currentUser);
        newsFacade.setViewed(friend, currentUser, Boolean.TRUE);
        
        if (friend.getStatus().equals(FriendshipStatus.friends)) {
            return;
        }
        
        if (isCurrentUserUser1) {
            if (friend.getStatus().equals(FriendshipStatus.user2_subscribed) ||
                    friend.getStatus().equals(FriendshipStatus.user1_deleted_user2))
            {
                friend.setStatus(FriendshipStatus.friends);
                newsFacade.setViewed(friend, friendUser, Boolean.TRUE);
            }
            else if (friend.getStatus().equals(FriendshipStatus.nobody_subscribed))
            {
                friend.setStatus(FriendshipStatus.user1_subscribed);
                newsFacade.setViewed(friend, friendUser, Boolean.FALSE);
            }
        }
        else {
            if (friend.getStatus().equals(FriendshipStatus.user1_subscribed) ||
                    friend.getStatus().equals(FriendshipStatus.user2_deleted_user1))
            {
                friend.setStatus(FriendshipStatus.friends);
                newsFacade.setViewed(friend, friendUser, Boolean.TRUE);
            }
            else if (friend.getStatus().equals(FriendshipStatus.nobody_subscribed)) 
            {
                friend.setStatus(FriendshipStatus.user2_subscribed);
                newsFacade.setViewed(friend, friendUser, Boolean.FALSE);
            }
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
        newsFacade.setViewed(friend, currentUser, Boolean.TRUE);
        
        if (isCurrentUserUser1) {
            if (friend.getStatus().equals(FriendshipStatus.friends)) {
                friend.setStatus(FriendshipStatus.user1_deleted_user2);
                newsFacade.setViewed(friend, friendUser, Boolean.FALSE);
            }
            else if (friend.getStatus().equals(FriendshipStatus.user2_deleted_user1)
                    || friend.getStatus().equals(FriendshipStatus.user1_subscribed)) 
            {
                friend.setStatus(FriendshipStatus.nobody_subscribed);
                newsFacade.setViewed(friend, friendUser, Boolean.TRUE);
            }
        }
        else {
            if (friend.getStatus().equals(FriendshipStatus.friends)) {
                friend.setStatus(FriendshipStatus.user2_deleted_user1);
                newsFacade.setViewed(friend, friendUser, Boolean.FALSE);
            }
            else if (friend.getStatus().equals(FriendshipStatus.user1_deleted_user2)
                    || friend.getStatus().equals(FriendshipStatus.user2_subscribed)) 
            {
                friend.setStatus(FriendshipStatus.nobody_subscribed);
                newsFacade.setViewed(friend, friendUser, Boolean.TRUE);
            }
        }
        edit(friend);
    }
    
    private List<Friends> findFriendsByUsers(Users currentUser, Users friendUser) {
        TypedQuery<Friends> query = em.createNamedQuery("Friends.findByUsers", Friends.class);
        query.setParameter("user1", currentUser);
        query.setParameter("user2", friendUser);
        return query.getResultList();
    }

    
}
