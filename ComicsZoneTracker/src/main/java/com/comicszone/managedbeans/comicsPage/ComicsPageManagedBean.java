/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.comicsPage;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Volume;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author aypyatakov
 */
@ManagedBean(name="comicsPageManagedBean")
@RequestScoped
public class ComicsPageManagedBean {
    
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
    
    public String redirect(Integer issueId)
    {
        return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + issueId;
    }
    
    /*public void rate(AjaxBehaviorEvent actionEvent) {
        Double score = (Double)((UIRating) actionEvent.getComponent())
            .getValue();
        selectedComics.setRating(score);
    }*/

}
