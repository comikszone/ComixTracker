package com.comicszone.managedbeans;

import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.NamedImage;
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
    
    public String redirect(NamedImage content)
    {
        if (content.getContentType() == ContentType.Comics)
            return "/resources/pages/comicsPage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Issue)
                return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Volume)
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Character)
            return "/resources/pages/characterPage.jsf?faces-redirect=true&id=" + content.getId();
        else return "/resources/templates/index.jsf";
    }
}
