/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

import com.comicszone.dao.FriendsFacade;
import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.managedbeans.userbeans.CurrentUserManagedBean;
import com.comicszone.managedbeans.userbeans.ProfileUserManagedBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Eschenko_DA
 */

@ManagedBean
@ViewScoped
public class UserFriendsManagedBean implements Serializable {
    
    @EJB
    private FriendsFacade friendsFacade;
    
    @EJB 
    private UserDataFacade userDataFacade;
    
    private List<Users> friends;
    
    private List<Users> followers;
    
    private boolean followersIsEmpty;

    private List<Users> unconfirmedFriends;
    
    private Users currentUser;
    
    private Users selectedUser;
    
    private Users selectedFriend;

    @PostConstruct
    public void init() {
       try {
            currentUser = (Users) ((CurrentUserManagedBean) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .get("currentUserManagedBean"))
                    .getCurrentUser()
                    .clone();
            
            friends = friendsFacade.getFriends(currentUser);
            followers = friendsFacade.getFolowers(currentUser);
            unconfirmedFriends = friendsFacade.getUnconfirmedFriends(currentUser);
            followersIsEmpty = followers.isEmpty();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ProfileUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public List<Users> completeUser(String query) {
        
        List<Users> users = userDataFacade.getUsersWithNicknameStartsWith(query);
        
        users.removeAll(friendsFacade.getFriends(currentUser));
        users.removeAll(friendsFacade.getUnconfirmedFriends(currentUser));
        users.remove(currentUser);
        
        return users;
    }
    
    public void addToFriends(Users unconfirmedUser) {
        friendsFacade.addToFriends(currentUser, unconfirmedUser);
        //send add news to unconfirmedUser
        setFriends(friendsFacade.getFriends(currentUser));
        setFollowers(friendsFacade.getFolowers(currentUser));
        setUnconfirmedFriends(friendsFacade.getUnconfirmedFriends(currentUser));
    }
    
    public void removeFromFrieds(Users friend) {
        friendsFacade.removeFromFriends(currentUser, friend);
        //send remove news to friend.
        setFriends(friendsFacade.getFriends(currentUser));
        setFollowers(friendsFacade.getFolowers(currentUser));
        setUnconfirmedFriends(friendsFacade.getUnconfirmedFriends(currentUser));
    }
    
    public String getFormatedData(Users friend) {
        return new SimpleDateFormat("dd-MM-yyyy").format(friend.getBirthday());
    }
    
    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public Users getSelectedFriend() {
        return selectedFriend;
    }

    public void setSelectedFriend(Users selectedFriend) {
        this.selectedFriend = selectedFriend;
    }

    public List<Users> getFriends() {
        return friends;
    }

    public void setFriends(List<Users> friends) {
        this.friends = friends;
    }

    public List<Users> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Users> followers) {
        this.followers = followers;
    }

    public List<Users> getUnconfirmedFriends() {
        return unconfirmedFriends;
    }

    public void setUnconfirmedFriends(List<Users> unconfirmedFriends) {
        this.unconfirmedFriends = unconfirmedFriends;
    }

    public boolean isFollowersIsEmpty() {
        return followersIsEmpty;
    }

    public void setFollowersIsEmpty(boolean followersIsEmpty) {
        this.followersIsEmpty = followersIsEmpty;
    }
}
