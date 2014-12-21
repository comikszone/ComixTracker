package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import java.security.Principal;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class UserManagedBean {

    @EJB
    UserDataFacade userDAO;

    public String getNickname() {
        return userDAO.getNickname();
    }

    public byte[] getAvatar() {
        return userDAO.getAvatar();
    }

    public int getSex() {
        return userDAO.getSex();
    }

    public Date getBirthday() {
        return userDAO.getBirthday();
    }

    public String getEmail() {
        return userDAO.getEmail();
    }
    
    public void setCurrentUser(){
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        userDAO.findUserByNickname(prin.getName());
    }
}
