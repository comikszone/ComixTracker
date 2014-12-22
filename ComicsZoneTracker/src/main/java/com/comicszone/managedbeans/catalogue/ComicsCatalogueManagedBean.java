/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Eschenko_DA
 */
@ManagedBean(name="comicsCatalogueView")
@ViewScoped
public class ComicsCatalogueManagedBean {
    
    @EJB
    private ComicsFacade comicsFacade;
    
    private LazyDataModel<Comics> lazyModel;
     
    private Comics selectedComics;
    
    private Integer[] ratings = {0,1,2,3,4};
     
//    @ManagedProperty("#{comicsService}")
//    private ComicsService comicsService = new ComicsService();
     
    @PostConstruct
    public void init() {
        lazyModel = new LazyComicsDataModel(comicsFacade.get12Best(),comicsFacade);
    }
 
    public List<Integer> getRatings() {
        return Arrays.asList(ratings);
    }
    
    public LazyDataModel<Comics> getLazyModel() {
        return lazyModel;
    }
 
    public Comics getSelectedComics() {
        return selectedComics;
    }
 
    public void setSelectedComics(Comics selectedComics) {
        this.selectedComics = selectedComics;
    }
     
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Comics Selected",((Comics) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /*public void rate(AjaxBehaviorEvent actionEvent) {
        Double score = (Double)((UIRating) actionEvent.getComponent())
            .getValue();
        selectedComics.setRating(score);
    }*/
}
