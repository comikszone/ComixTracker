package com.comicszone.managedbeans.userbeans;

import com.comicszone.managedbeans.util.passwordcreators.UserAuthentification;
import java.io.IOException;
import java.security.Principal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class AuthorisationManagedBean {

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

    public void doLogin() throws IOException {

        FacesContext context = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            Principal principal = request.getUserPrincipal();
            
            if (principal == null || !principal.getName().equals(nickname)) {
                UserAuthentification.authUser(nickname, password, request);
            }
            context.getExternalContext().redirect("/");
            return;
        } catch (Exception e) {
            password = null;

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }

}
