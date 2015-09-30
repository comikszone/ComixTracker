package com.comicszone.managedbeans.logout;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Named
@RequestScoped
public class LogoutController implements Serializable {
    public void logout(){
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext ex = facesContext.getExternalContext();
            HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
            httpSession.invalidate();
            ex.redirect("/resources/templates/index.jsf");
        } catch (IOException ex1) {
            Logger.getLogger(LogoutController.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
