package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.managedbeans.util.passwordcreators.UserAuthentification;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class AuthorisationManagedBean implements Serializable {

    @EJB
    UserDataFacade userDAO;

    String nickname;
    String password;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void doLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        Users user = userDAO.getUserWithNickname(nickname);
        if (user != null && user.getBanned()) {
            context.addMessage(null, new FacesMessage("Error", "User with this nickname is banned!"));
            return;
        }
        loginWithoutBan();
    }

    public void loginWithoutBan() {
        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            Principal principal = request.getUserPrincipal();

            if (principal == null || !principal.getName().equals(nickname)) {
                UserAuthentification.authUser(nickname, password, request);
            }
            context.getExternalContext().redirect("/");
            return;
        } catch (ServletException | IOException | IllegalStateException e) {
            password = null;

            context.addMessage(null, new FacesMessage("Error", e.getMessage()));
        }
    }

}
