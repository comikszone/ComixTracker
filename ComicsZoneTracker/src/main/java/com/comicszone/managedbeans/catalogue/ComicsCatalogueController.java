/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.CatalogueInterface;
import com.comicszone.entity.Comics;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Eschenko_DA
 */
@Named("comicsCatalogueView")
@SessionScoped
public class ComicsCatalogueController implements Serializable {
    
    private final String columnComicsName = "Name";
    private final String columnComicsRating = "Rating";
    private final String columnComicsImage = "Image";
    private final String columnComicsDescription = "Description";
    
    @EJB
    private CatalogueInterface catalogue;
    
    private LazyDataModel<Comics> lazyModel;
     
    private Comics selectedComics;
    
    private Rating rating;
     
    @PostConstruct
    public void init() {
        lazyModel = new LazyComicsDataModel(catalogue, columnComicsName, columnComicsRating,rating);
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
}
