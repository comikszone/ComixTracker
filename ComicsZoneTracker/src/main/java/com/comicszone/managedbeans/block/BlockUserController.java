/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.block;

import com.comicszone.dao.user.UserBlockFacade;
import com.comicszone.entity.Users;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class BlockUserController implements Serializable {
    
    @EJB
    UserBlockFacade userBlockDao;
    
    private Users selectedUser;
    private String searchOption = "1";
    
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
    
    public List<Users> completeToBlock(String query) {
        if (searchOption.equals("2")) {
            return completeToBlockRealNickname(query);
        }
        List<Users> users = userBlockDao.getSomeUnblockedUsersWithNickname(query, 5);
        return users;
    }
    
    public List<Users> completeToUnblock(String query) {
        if (searchOption.equals("2")) {
            return completeToUnblockRealNickname(query);
        }
        List<Users> users = userBlockDao.getSomeBlockedUsersWithNickname(query, 5);
        return users;
    }
    
    public List<Users> completeToBlockRealNickname(String query) {
        List<Users> users = userBlockDao.getSomeUnblockedUsersWithRealNickname(query, 5);
        return users;
    }
    
    public List<Users> completeToUnblockRealNickname(String query) {
        List<Users> users = userBlockDao.getSomeBlockedUsersWithRealNickname(query, 5);
        return users;
    }
    
    public void setSelectedUser(Users user) {
        this.selectedUser = user;
    }
    
    public Users getSelectedUser() {
        return selectedUser;
    }
    
    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }
    
    public String getSearchOption() {
        return searchOption;
    }
}
