/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.comicscatalogue;

import com.mycompany.comicscatalogue.entity.Comics;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import net.playerfinder.jsf.components.rating.UIRating;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author yesch_000
 */
@ManagedBean(name="comicsCatalogueView")
@ViewScoped
public class ComicsCatalogueView implements Serializable {
         
    private LazyDataModel<Comics> lazyModel;
     
    private Comics selectedComics;
    
    private Integer[] ratings = {0,1,2,3,4};
     
    @ManagedProperty("#{comicsService}")
    private ComicsService comicsService = new ComicsService();

    public void setComicsService(ComicsService comicsService) {
        this.comicsService = comicsService;
    }
     
    @PostConstruct
    public void init() {
        lazyModel = new LazyComicsDataModel(comicsService.createComics());
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
    
    public void rate(AjaxBehaviorEvent actionEvent) {
        Double score = (Double)((UIRating) actionEvent.getComponent())
            .getValue();
        selectedComics.setRating(score);
    }
}
