package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;

@ManagedBean
@SessionScoped
public class CurrentUserManagedBean implements Serializable {

    private Users currentUser;
    @EJB
    UserDataFacade userDAO;

    public String getName() {
        return currentUser.getName();
    }

    public String getNickname() {
        return currentUser.getNickname();
    }

    public Object getAvatar() {
        if (currentUser.getAvatar() == null) {
            return currentUser.getAvatarUrl();
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(currentUser.getAvatar()));
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
    
    public String getNameToStartPage(){
        if (getName() != null && !getName().trim().equals("")){
            return getName();
        }
        return getNickname();
    }

    public Users getCurrentUser() throws CloneNotSupportedException {
        return (Users) currentUser.clone();
    }

    @PostConstruct
    public void setCurrentUser() {
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        currentUser = userDAO.getUserWithNickname(prin.getName());
    }
}
