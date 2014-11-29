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

import com.comicszone.dao.AjaxComicsCharacter;
import com.comicszone.dao.CharacterDaoImpl;
import com.comicszone.dao.ComicsDaoImpl;
import com.comicszone.dao.ComicsDaoImpl;
import com.comicszone.dao.Finder;
import com.comicszone.entitynetbeans.Comics;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
//import javax.inject.Scope;
import org.primefaces.event.SelectEvent;
 
 
@ManagedBean
@SessionScoped
public class AutoCompleteView {

    private AjaxComicsCharacter ajaxComicsCharacter;

    public AjaxComicsCharacter getAjaxComicsCharacter() {
        return ajaxComicsCharacter;
    }

    public void setAjaxComicsCharacter(AjaxComicsCharacter ajaxComicsCharacter) {
        this.ajaxComicsCharacter = ajaxComicsCharacter;
    }
   
    private ComicsDaoImpl comicsDaoImpl;
    private String category;
    public void onCategoryChange()
    {
        if (category.equals("Comics"))
            finder=new ComicsDaoImpl();
        else
            finder=new CharacterDaoImpl();
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public AutoCompleteView() {
        comicsDaoImpl=new ComicsDaoImpl();
    }
     
    private Finder finder;
     
    public List<AjaxComicsCharacter> completeComics(String query) {
//        List<Comics> filteredComics = new ArrayList<Comics>();
        List<AjaxComicsCharacter> ajaxComicsCharacters=(List<AjaxComicsCharacter>) finder.findByNameStartsWith(query.toLowerCase());
        FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("finder", finder);
        return ajaxComicsCharacters;
    }
}