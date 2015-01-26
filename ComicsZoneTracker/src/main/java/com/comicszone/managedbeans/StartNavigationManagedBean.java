package com.comicszone.managedbeans;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class StartNavigationManagedBean {
    public void returnStartPage() throws IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (context.isUserInRole("user")){
            context.redirect("/resources/templates/authorized/authorized.jsf");
            return;
        }
        context.redirect("/resources/templates/unauthorized/unauthorized.jsf");
    }
}
