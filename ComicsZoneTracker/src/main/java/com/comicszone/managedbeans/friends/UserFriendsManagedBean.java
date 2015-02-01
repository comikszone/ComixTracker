/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Eschenko_DA
 */

@ManagedBean
@ViewScoped
public class UserFriendsManagedBean implements Serializable {
    
    @EJB
    private UserFriendsFacade userFriendsFacade;
    
    
    private List<Users> friends;
    
    private Users currentUser;
    
    private Users selectedUser;
    
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
            
            friends = userFriendsFacade.getFriends(currentUser);
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
        System.err.println("***METHOD RUN*****");
        userFriendsFacade.addToFriends(currentUser, selectedUser);
    }
    
    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    public void setFriends(List<Users> friends) {
        this.friends = friends;
    }

    public List<Users> getFriends() {
        return friends;
    }
    
}
