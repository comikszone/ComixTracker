/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Issue;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;
/**
 *
 * @author ArsenyPC
 */
@Named("comicsController")
@ViewScoped
public class ComicsController implements Serializable {
    @EJB
    private ComicsFacade comicsFacade;
    private Comics comics;
    private Integer comicsId;
    
    @Inject
    private CardController ctrl;
    
    public ComicsController(){
    }
    
    public Integer getComicsId() {
        return comicsId;
    }

    public void setComicsId(Integer comicsId) {
        this.comicsId = comicsId;
    }

    public ComicsFacade getComicsFacade() {
        return comicsFacade;
    }
    
    public void setComicsFacade(ComicsFacade comicsFacade) {
        this.comicsFacade = comicsFacade;
    }
    
    public void initComics()
    {
        comics=comicsFacade.find(comicsId);
    }

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }
    
    public String getProperIssueName(Issue issue){
        ctrl.setName(issue.getName());
        ctrl.setCard(issue.getCard());
        return ctrl.getName();
    }

}
