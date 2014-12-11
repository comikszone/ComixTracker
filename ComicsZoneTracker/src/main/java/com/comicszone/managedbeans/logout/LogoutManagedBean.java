package com.comicszone.managedbeans.logout;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@RequestScoped
public class LogoutManagedBean {
    public String logout(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext == null) return "/resources/templates/index.jsf";
        HttpSession httpSession = (HttpSession)facesContext.getExternalContext().getSession(false);
        if (httpSession == null) return "/resources/templates/index.jsf" ;
        httpSession.invalidate();
        return "/resources/templates/index.jsf";
    }
}
