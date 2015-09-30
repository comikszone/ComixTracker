package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Users;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;

@Named
@SessionScoped
public class CurrentUserController implements Serializable {

    private Users currentUser;
    @EJB
    UserDataFacade userDAO;

    public String getRealNickname(){
        return currentUser.getRealNickname();
    }
       
    public String getName() {
        return currentUser.getName();
    }

    public String getNickname() {
        return currentUser.getNickname();
    }

    public Object getAvatar() {
        if (currentUser.getAvatar() == null) {
            if (currentUser.getAvatarUrl() == null || currentUser.getAvatarUrl().equals("")) {
                System.out.println(currentUser.getAvatarUrl());
                return "/resources/images/default_user_photo.png";
            }
            System.out.println(currentUser.getAvatarUrl());
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
//        if (getName() != null && !getName().trim().equals("")){
//            return getName();
//        }
//        return ();
        return getRealNickname();
    }

    public Users getCurrentUser() throws CloneNotSupportedException {
        return (Users) currentUser.clone();
    }

    @PostConstruct
    public void setCurrentUser() {
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        currentUser = userDAO.getUserWithNickname(prin.getName());
        System.out.println("123");
    }
}
