/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

import com.comicszone.dao.FriendsFacade;
import com.comicszone.dao.userdao.UserFriendsFacade;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.managedbeans.userbeans.CurrentUserManagedBean;
import com.comicszone.managedbeans.userbeans.ProfileUserManagedBean;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.LazyDataModel;

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
    private UserFriendsFacade userFriendsFacade;
    
    private List<Users> friendsLazyModel;

    

    private Users currentUser;
    
    private Users selectedUser;
    
    private Users selectedFriend;

    
    @PostConstruct
    public void init() {
        
        //friendsLazyModel = new LazyFriendsDataModel(userFriendsFacade);
       try {
            currentUser = (Users) ((CurrentUserManagedBean) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .get("currentUserManagedBean"))
                    .getCurrentUser()
                    .clone();
            
            friendsLazyModel = friendsFacade.getFriends(currentUser);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ProfileUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public List<Users> completeUser(String query) {
        List<Users> users = userFriendsFacade.getUsersWithNicknameStartsWith(query);
        //FacesContext.getCurrentInstance().getAttributes().put("users", users);
        return users;
    }
    
    public void addToFriends() {
        friendsFacade.addToFriends(currentUser, selectedUser);
        setFriendsLazyModel(friendsFacade.getFriends(currentUser));
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
    
    /*public LazyDataModel<Users> getFriendsLazyModel() {
        return friendsLazyModel;
    }

    public void setFriendsLazyModel(LazyDataModel<Users> friendsLazyModel) {
        this.friendsLazyModel = friendsLazyModel;
    }*/
    public List<Users> getFriendsLazyModel() {
        return friendsLazyModel;
    }

    public void setFriendsLazyModel(List<Users> friendsLazyModel) {
        this.friendsLazyModel = friendsLazyModel;
    }
}
