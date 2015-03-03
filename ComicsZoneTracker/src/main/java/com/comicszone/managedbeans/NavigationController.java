package com.comicszone.managedbeans;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class NavigationController implements Serializable {
    private String templateName;
    
    public void returnStartPage() throws IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (context.isUserInRole("user")){
            context.redirect("/resources/templates/authorized/authorized.jsf");
            return;
        }
        context.redirect("/resources/templates/unauthorized/unauthorized.jsf");
    }
    
    @PostConstruct
    public void selectTemplate(){
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (context.isUserInRole("user")){
            templateName = "/resources/templates/authorized/authorized.xhtml";
            return;
        }
        templateName = "/resources/templates/unauthorized/unauthorized.xhtml";
    }
    
    public String getTemplateName() {
        return templateName;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    } 
}
