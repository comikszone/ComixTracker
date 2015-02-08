/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
/**
 *
 * @author ArsenyPC
 */
@ManagedBean(name="comicsController")
@ViewScoped
public class ComicsController implements Serializable {
    @EJB
    private ComicsFacade comicsFacade;
    private Comics comics;
    private Integer comicsId;
    
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

}
