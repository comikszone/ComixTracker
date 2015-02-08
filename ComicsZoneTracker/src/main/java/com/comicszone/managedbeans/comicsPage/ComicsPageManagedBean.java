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
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
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
    private Volume volume;
    private Comics comics;
    private Integer comicsId;
    
    private LazyDataModel<Issue> lazyIssueModel;
    
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
    
    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }
    
    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }
    public LazyDataModel<Issue> getLazyIssueModel() {
        return lazyIssueModel;
    }
    
    public void init() {
        comics = comicsFacade.find(comicsId);
    }
    
    public void initLazyModel() {
        lazyIssueModel = new LazyIssueDataModel(volume);
    }
    
    public String redirect(Content content)
    {
        if (content.getContentType() == ContentType.Issue)
            return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Volume)
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + content.getId();
        else return "/resources/templates/index.jsf";
    }
    
    /*public void rate(AjaxBehaviorEvent actionEvent) {
        Double score = (Double)((UIRating) actionEvent.getComponent())
            .getValue();
        selectedComics.setRating(score);
    }*/

}
