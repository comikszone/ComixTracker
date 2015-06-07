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
import javax.inject.Named;
import com.comicszone.entity.Comics;
import javax.ejb.EJB;
import org.omnifaces.cdi.ViewScoped;

/**
 * 
 * @author Alexander Pyatakov
 */

@Named("slideshowController")
@ViewScoped
public class SlideShowController implements Serializable {
    
    private final Integer COMICS_QUANTITY = 12;
    
    private List<Comics> comicsList;
    @EJB
    private ComicsFacade comicsFacade;
    
    public SlideShowController() {
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
