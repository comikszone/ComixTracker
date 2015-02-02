/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import java.io.Serializable;
import java.util.ArrayList;
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
public class ComicsCatalogueManagedBean implements Serializable {
    
    private final String columnComicsName = "Name";
    private final String columnComicsRating = "Rating";
    private final String columnComicsImage = "Image";
    private final String columnComicsDescription = "Description";
    
    @EJB
    private ComicsFacade comicsFacade;
    
    private LazyDataModel<Comics> lazyModel;
     
    private Comics selectedComics;
    
    private Integer[] intRatings = {0,1,2,3,4};

    public List<Integer> getIntRatings() {
        return Arrays.asList(intRatings);
    }

    private List<Rating> ratings;
    private Rating rating;
     
    @PostConstruct
    public void init() {
        lazyModel = new LazyComicsDataModel(comicsFacade,
                columnComicsName, columnComicsRating);
        ratings = new ArrayList();
        ratings.add(new Rating(0,"/resources/images/ratings/1.jpg"));
        ratings.add(new Rating(1,"/resources/images/ratings/2.jpg"));
        ratings.add(new Rating(2,"/resources/images/ratings/3.jpg"));
        ratings.add(new Rating(3,"/resources/images/ratings/4.jpg"));
        ratings.add(new Rating(4,"/resources/images/ratings/5.jpg"));
    }
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Comics Selected",
                ((Comics) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
 
    public List<Rating> getRatings() {
        return ratings;
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
    
    public String getColumnComicsName() {
        return columnComicsName;
    }

    public String getColumnComicsRating() {
        return columnComicsRating;
    }

    public String getColumnComicsImage() {
        return columnComicsImage;
    }

    public String getColumnComicsDescription() {
        return columnComicsDescription;
    }
    
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    
    /*public void rate(AjaxBehaviorEvent actionEvent) {
        Double score = (Double)((UIRating) actionEvent.getComponent())
            .getValue();
        selectedComics.setRating(score);
    }*/
}
