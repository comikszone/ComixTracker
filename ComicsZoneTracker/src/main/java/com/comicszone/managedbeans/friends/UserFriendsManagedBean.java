/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

import com.comicszone.dao.FriendsFacade;
import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.managedbeans.message.MessagesManagedBean;
import com.comicszone.managedbeans.userbeans.CurrentUserManagedBean;
import com.comicszone.managedbeans.userbeans.ProfileUserManagedBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
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

    private List<Users> unconfirmedFriends;
    
    private Users currentUser;
    
    private Users selectedUser;
    
    private Users selectedFriend;

    private Users selectedFollower;
    
    private Users selectedUnconfirmedFriend;
    
    private MessagesManagedBean mmb;
    
    private boolean showMessages;
    
    private boolean showMessagesAdder;
    
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
            followers = friendsFacade.getFollowers(currentUser);
            unconfirmedFriends = friendsFacade.getUnconfirmedFriends(currentUser);
            
            if (!friends.isEmpty())
            {
                setSelectedFriend(friends.get(0));
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ProfileUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public List<Users> completeUser(String query) {
        
        List<Users> users = userDataFacade.getUsersWithRealNicknameStartsWith(query);
        
        users.removeAll(friendsFacade.getFriends(currentUser));
        users.removeAll(friendsFacade.getUnconfirmedFriends(currentUser));
        users.remove(currentUser);
        
        return users;
    }
    
    public void addToFriends(Users unconfirmedUser) {
        mmb = (MessagesManagedBean) FacesContext
                .getCurrentInstance()
                .getViewRoot()
                .getViewMap()
                .get("messagesManagedBean");
        
        mmb.setActiveIndex(2);
        friendsFacade.addToFriends(currentUser, unconfirmedUser);
        setFriends(friendsFacade.getFriends(currentUser));
        setFollowers(friendsFacade.getFollowers(currentUser));
        setUnconfirmedFriends(friendsFacade.getUnconfirmedFriends(currentUser));
        setSelectedFriend(unconfirmedUser);
        setSelectedUnconfirmedFriend(unconfirmedUser);
        mmb.setFriendId(unconfirmedUser.getUserId());
    }
    
    public void removeFromFrieds(Users friend) {
        
        friendsFacade.removeFromFriends(currentUser, friend);
        setFriends(friendsFacade.getFriends(currentUser));
        setFollowers(friendsFacade.getFollowers(currentUser));
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
        selectedFollower=null;
        selectedUnconfirmedFriend=null;
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

    public Users getSelectedFollower() {
        return selectedFollower;
    }

    public void setSelectedFollower(Users selectedFollower) {
        this.selectedFollower = selectedFollower;
        selectedFriend = null;
        selectedUnconfirmedFriend = null;
    }

    public boolean isShowMessages() {
        return showMessages;
    }

    public void setShowMessages(boolean showMessages) {
        this.showMessages = showMessages;
    }

    public boolean isShowMessagesAdder() {
        return showMessagesAdder;
    }

    public void setShowMessagesAdder(boolean showMessagesAdder) {
        this.showMessagesAdder = showMessagesAdder;
    }

    public Users getSelectedUnconfirmedFriend() {
        return selectedUnconfirmedFriend;
    }

    public void setSelectedUnconfirmedFriend(Users selectedUnconfirmedFriend) {
        this.selectedUnconfirmedFriend = selectedUnconfirmedFriend;
        selectedFriend = null;
        selectedFollower = null;
    }
}
