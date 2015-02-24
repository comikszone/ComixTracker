/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.slideshow;

import com.comicszone.dao.ComicsFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.comicszone.entitynetbeans.Comics;
import javax.ejb.EJB;

/**
 * 
 * @author Alexander Pyatakov
 */

@ManagedBean(name="slideshowController")
@ViewScoped
public class SlideshowController implements Serializable {
    
    private final Integer COMICS_QUANTITY = 12;
    
    private List<Comics> comicsList;
    @EJB
    private ComicsFacade comicsFacade;
    
    public SlideshowController() {
    }
        
    @PostConstruct
    public void fillComicsList() {
       comicsList = comicsFacade.getBest(COMICS_QUANTITY);
    }
    
    public List<Comics> getComicsList() {
        return comicsList;
    }
    
    public String redirect(Integer comicsId)
    {
        return "/resources/pages/comicsPage.jsf?faces-redirect=true&id=" + comicsId;
    }
    
}
