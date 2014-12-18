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

import com.comicszone.entitynetbeans.AjaxComicsCharacter;
import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.Finder;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
//import javax.inject.Scope;
 
 
@ManagedBean
@SessionScoped
public class AutoCompleteView {
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private CharacterFacade characterFacade;
    private AjaxComicsCharacter ajaxComicsCharacter;
    private String category;
    private Finder finder;

    @PostConstruct
    public void init() {
        finder=comicsFacade;
    }
    
    public AjaxComicsCharacter getAjaxComicsCharacter() {
        return ajaxComicsCharacter;
    }

    public void setAjaxComicsCharacter(AjaxComicsCharacter ajaxComicsCharacter) {
        this.ajaxComicsCharacter = ajaxComicsCharacter;
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
     
    public List<AjaxComicsCharacter> completeComics(String query) {
//        finder=comicsFacade;
//        if (finder==null)
//            finder=comicsFacade;
        List<AjaxComicsCharacter> ajaxComicsCharacters=(List<AjaxComicsCharacter>) finder.findByNameStartsWith(query.toLowerCase());
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("finder", finder);
        return ajaxComicsCharacters;
    }
    public String redirect()
    {
        return "/resources/pages/comicspage.jsf?faces-redirect=true&id=" + ajaxComicsCharacter.getId();
    }
}