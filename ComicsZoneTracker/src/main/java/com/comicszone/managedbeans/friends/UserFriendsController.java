/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

import com.comicszone.dao.FriendsFacade;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Users;
import com.comicszone.managedbeans.message.MessagesController;
import com.comicszone.managedbeans.userbeans.CurrentUserController;
import com.comicszone.managedbeans.userbeans.ProfileUserController;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Eschenko_DA
 */

@Named
@ViewScoped
public class UserFriendsController implements Serializable {
    
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

    private boolean showMessages;
    
    private boolean showMessagesAdder;

    @PostConstruct
    public void init() {
       try {
            currentUser = (Users) ((CurrentUserController) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .get("currentUserController"))
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
            Logger.getLogger(ProfileUserController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    public void isUpdateDatatable(ActionEvent event) {
//		System.out.println("Message received at " + new Date());
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String msgFromAdmin = requestParameterMap.get("msgData");
//                System.err.println("RECEIVED"+msgFromAdmin + " currentUser" + currentUser.getUserId());
                Integer id=Integer.parseInt(msgFromAdmin);
                if (id.equals(currentUser.getUserId()))
                {
                    RequestContext.getCurrentInstance().update("form:messages");
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
       
        friendsFacade.addToFriends(currentUser, unconfirmedUser);
        
        setFriends(friendsFacade.getFriends(currentUser));
        setFollowers(friendsFacade.getFollowers(currentUser));
        setUnconfirmedFriends(friendsFacade.getUnconfirmedFriends(currentUser));
        setSelectedFriend(unconfirmedUser);
        setSelectedUnconfirmedFriend(unconfirmedUser);
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
        selectedFollower = null;
        selectedUnconfirmedFriend = null;
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
