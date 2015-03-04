/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.mainsearch.autocomplete;

/**
 *
 * @author ArsenyPC
 */

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.Finder;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.NamedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
 
 
@ManagedBean
@ViewScoped
public class AutoCompleteController implements Serializable {
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private CharacterFacade characterFacade;
    private NamedImage content;
    private String category;
    private Finder finder;

    @PostConstruct
    public void init() {
        finder=comicsFacade;
    }
    
    public NamedImage getContent() {
        return content;
    }

    public void setContent(NamedImage content) {
        this.content = content;
    }
   
    public void onCategoryChange()
    {
        if (category.equals("Comics"))
            finder=comicsFacade;
        else
            finder=characterFacade;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void handleSelect() throws IOException
    {
        String url=redirect();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(url);
    }
     
    public List<Content> completeComics(String query) {
        List<Content> contents=(List<Content>) finder.findByNameStartsWith(query.toLowerCase());
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("finder", finder);
        return contents;
    }
    public String redirect()
    {
        if (content.getContentType().equals(ContentType.Comics))
            return "/resources/pages/comicsPage.jsf?faces-redirect=true&id=" + content.getId();
        else
            return "/resources/pages/characterPage.jsf?faces-redirect=true&id=" + content.getId();
    }
}