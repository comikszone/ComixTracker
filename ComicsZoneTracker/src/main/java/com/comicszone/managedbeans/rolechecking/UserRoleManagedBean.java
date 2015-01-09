/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.rolechecking;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author alexander
 */
@ManagedBean
@RequestScoped
public class UserRoleManagedBean {
    
    public boolean isUserAuthorized() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.isUserInRole("user");
    }
    
    public boolean isUserAdmin() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.isUserInRole("admin");
    }
    
    public void redirectIfNotAdmin() throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (!context.isUserInRole("admin")) {
            context.redirect("/ComicsZoneTracker/resources/templates/authorized/access_forbidden.jsf");
        }
    }
}
