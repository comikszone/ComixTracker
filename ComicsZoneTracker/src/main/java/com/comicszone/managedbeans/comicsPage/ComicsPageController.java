/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.comicsPage;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.managedbeans.entitycontroller.ComicsController;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author aypyatakov
 */
@ManagedBean(name="comicsPageController")
@ViewScoped
public class ComicsPageController implements Serializable {
    
    @EJB
    private ComicsFacade comicsFacade; 
    @ManagedProperty(value="#{comicsController}")
    private ComicsController comicsController;
    
    private boolean readingModeOn;
    
    private Integer comicsId;
    /**
     * @return the comicsFacade
     */
    public ComicsFacade getComicsFacade() {
        return comicsFacade;
    }

    /**
     * @param comicsFacade the comicsFacade to set
     */
    public void setComicsFacade(ComicsFacade comicsFacade) {
        this.comicsFacade = comicsFacade;
    }
    
    public ComicsController getComicsController() {
        return comicsController;
    }
    
    public void setComicsController(ComicsController comicsController) {
        this.comicsController = comicsController;
    }
    
        /**
     * @return the comicsId
     */
    public Integer getComicsId() {
        return comicsId;
    }

    /**
     * @param comicsId the comicsId to set
     */
    public void setComicsId(Integer comicsId) {
        this.comicsId = comicsId;
    }
    public void init() {
        comicsController.setComicsId(comicsId);
        comicsController.initComics();
    }
    
    /**
     * @return the readingModeOn
     */
    public boolean isReadingModeOn() {
        return readingModeOn;
    }

    /**
     * @param readingModeOn the readingModeOn to set
     */
    public void setReadingModeOn(boolean readingModeOn) {
        this.readingModeOn = readingModeOn;
    }
    
}
