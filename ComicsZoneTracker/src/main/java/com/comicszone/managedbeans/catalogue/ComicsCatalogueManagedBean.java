/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.CatalogueInterface;
import com.comicszone.entitynetbeans.Comics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Eschenko_DA
 */
@ManagedBean(name="comicsCatalogueView")
@SessionScoped
public class ComicsCatalogueManagedBean implements Serializable {
    
    private final String columnComicsName = "Name";
    private final String columnComicsRating = "Rating";
    private final String columnComicsImage = "Image";
    private final String columnComicsDescription = "Description";
    
    @EJB
    private CatalogueInterface catalogue;
    
    private LazyDataModel<Comics> lazyModel;
     
    private Comics selectedComics;
    
    private Rating rating;
    
    public void filter() {
        System.err.println("METHOD FILTER RUNS");
    }
     
    @PostConstruct
    public void init() {
        lazyModel = new LazyComicsDataModel(catalogue, columnComicsName, columnComicsRating);
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
