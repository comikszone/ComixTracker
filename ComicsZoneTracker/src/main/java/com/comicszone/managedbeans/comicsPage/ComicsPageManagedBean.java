/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.comicsPage;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.ContentType;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Volume;
import com.comicszone.managedbeans.entitycontroller.ComicsController;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import net.playerfinder.jsf.components.rating.UIRating;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author aypyatakov
 */
@ManagedBean(name="comicsPageManagedBean")
@ViewScoped
public class ComicsPageManagedBean implements Serializable {
    
    @EJB
    private ComicsFacade comicsFacade; 
    @ManagedProperty(value="#{comicsController}")
    private ComicsController comicsController;
    
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
    
    public String redirect(Content content)
    {
        if (content.getContentType() == ContentType.Comics)
            return "/resources/templates/authorized/readingPage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Issue)
                return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Volume)
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + content.getId();
        else return "/resources/templates/index.jsf";
    }
    
    public void rate(AjaxBehaviorEvent actionEvent) {
        Comics comics = getComicsController().getComics();
        Float score = (Float)((UIRating) actionEvent.getComponent()).getValue();
        comics.setRating(score);
        comicsFacade.edit(comics);
    }
}
