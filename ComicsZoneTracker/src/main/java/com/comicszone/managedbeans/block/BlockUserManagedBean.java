/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.block;

import com.comicszone.dao.userdao.UserBlockFacade;
import com.comicszone.entitynetbeans.Users;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class BlockUserManagedBean implements Serializable {
    
    @EJB
    UserBlockFacade userBlockDao;
    
    private Users selectedUser;
    
    public void blockUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userNickName = selectedUser != null ? selectedUser.getNickname() : null;
        String blockResult = "";
        
        switch (userBlockDao.blockUser(userNickName)) {
            case DONE:
                blockResult = "User \"" + userNickName + "\" successfully blocked.";
                break;
            case NOT_FOUND:
                blockResult = "User \"" + userNickName + "\" doesn't exist."; 
                break;
            case CANCELED:
                blockResult = "User \"" + userNickName + "\" has been banned yet.";
        }
        selectedUser = null;
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Block result", blockResult);
        facesContext.addMessage(null, facesMessage);
    }
    
    public void unblockUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userNickName = selectedUser.getNickname();
        String unblockResult = "";
        
        switch (userBlockDao.unblockUser(userNickName)) {
            case DONE:
                unblockResult = "User \"" + userNickName + "\" successfully unblocked.";
                break;
            case NOT_FOUND:
                unblockResult = "User \"" + userNickName + "\" doesn't exist."; 
                break;
            case CANCELED:
                unblockResult = "User \"" + userNickName + "\" hasn't been banned.";
        }
        selectedUser = null;
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Unblock result", unblockResult);
        facesContext.addMessage(null, facesMessage);
    }
    
    public List<Users> complete(String query) {
        List<Users> users = userBlockDao.getUsersWithNicknameStartsWith(query);
        FacesContext.getCurrentInstance().getAttributes().put("blockUser:userToBlock", users);
        return users;
    }
    
    public void setSelectedUser(Users user) {
        this.selectedUser = user;
    }
    
    public Users getSelectedUser() {
        return selectedUser;
    }
}
