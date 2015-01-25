package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import java.security.Principal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class CurrentUserManagedBean {

    Users currentUser;
    @EJB
    UserDataFacade userDAO;

    public String getNickname() {
        return currentUser.getNickname();
    }

    public byte[] getAvatar() {
        return currentUser.getAvatar();
    }

    public int getSex() {
        return currentUser.getSex();
    }

    public Date getBirthday() {
        return currentUser.getBirthday();
    }

    public String getEmail() {
        return currentUser.getEmail();
    }

    @PostConstruct
    public void setCurrentUser() {
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        currentUser = userDAO.getUserWithNickname(prin.getName());
    }
}
