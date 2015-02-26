/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.CatalogueInterface;
import com.comicszone.entitynetbeans.Comics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Eschenko_DA
 */
public class LazyComicsDataModel extends LazyDataModel<Comics> {
    
    private final CatalogueInterface catalogue;
    
    private List<Comics> datasource;
    private final String columnComicsName;
    private final String columnComicsRating;
    
    public LazyComicsDataModel(CatalogueInterface comicsFacade,
            String columnComicsName, String columnComicsRating, Rating rating) {
        datasource = new ArrayList<>();
        this.catalogue = comicsFacade;
        this.columnComicsName = columnComicsName.toLowerCase();
        this.columnComicsRating = columnComicsRating.toLowerCase();
    }
    
    @Override
    public Comics getRowData(String rowKey) {
        for(Comics comics : datasource) {
            if(comics.getId().toString().equals(rowKey))
                return comics;
        }
 
        return null;
    }
 
    @Override
    public Object getRowKey(Comics comics) {
        return comics.getId();
    }
    
    @Override
    public List<Comics> load(int first, int pageSize, String sortField, 
            SortOrder sortOrder, Map<String,Object> filters) {

        System.out.println("filters=" +filters);
        if (sortField == null) {
            sortField = columnComicsRating;
            sortOrder = SortOrder.DESCENDING;
        }
        
        List<Comics> resultComics;
        Rating rating = (Rating)filters.get(columnComicsRating);
        Object nameObject = filters.get(columnComicsName);
        
        if (nameObject != null && (rating.getValue() == null || rating.getValue() == 0)) {
            resultComics = catalogue.findByName(first,pageSize,nameObject.toString(),
                    sortField,sortOrder);
            this.setRowCount((int)catalogue.getComicsCountFoundByName(nameObject.toString()));
        }
        else if (rating != null && rating.getValue() != null) {
            Double doubleRating = rating.getValue().doubleValue();
            
            if (nameObject != null) {
                resultComics = catalogue.findByNameAndRating(first,pageSize,
                        nameObject.toString(),doubleRating,sortField,sortOrder);
                this.setRowCount((int)catalogue.
                        getComicsCountFoundByNameAndRating(nameObject.toString(), doubleRating));
            }
            else {
                resultComics = catalogue.findByRating(first,pageSize,
                        doubleRating,sortField,sortOrder);
                this.setRowCount((int)catalogue.getComicsCountFoundByRating(doubleRating));
            }
        }
        else {
            resultComics = catalogue.findAllForCatalogue(first,pageSize,sortField,sortOrder);
            this.setRowCount((int)(catalogue.getComicsCount()));
        }
        
        datasource = resultComics;
        return resultComics;
    }
}
