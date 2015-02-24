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
            String columnComicsName, String columnComicsRating) {
        datasource = new ArrayList<Comics>();
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
        System.err.println("FIRST***** " + first + "PAGESIZE***** " + pageSize);
        List<Comics> resultComics;
        
        //filtering and sorting
        if (sortField == null) {
            sortField = columnComicsRating;
            sortOrder = SortOrder.DESCENDING;
        }
        Iterator<String> itFilter = filters.keySet().iterator();
        if(itFilter.hasNext()) {
            
            String nextColumn = itFilter.next();
            String nextColumnValue = filters.get(nextColumn).toString();
            
            if (nextColumn.equals(columnComicsName) && !itFilter.hasNext()) {
                resultComics = catalogue.findByName(first,pageSize,
                        nextColumnValue,sortField,sortOrder);
                this.setRowCount((int)catalogue.getComicsCountFoundByName(nextColumnValue));
            }
            else if (nextColumn.equals(columnComicsRating) && !itFilter.hasNext()) {
                Double rating = Double.valueOf(nextColumnValue);
                resultComics = catalogue.findByRating(first,pageSize,
                        rating,sortField,sortOrder);
                this.setRowCount((int)catalogue.getComicsCountFoundByRating(rating));
            }
            else
            {
                String columnName;
                String columnRating;
                if (nextColumn.equals(columnComicsName)) {
                    columnName = nextColumn;
                    columnRating = itFilter.next();
                }
                else {
                    columnRating = nextColumn;
                    columnName = itFilter.next();
                }
                
                String columnNameValue = filters.get(columnName).toString();
                Double columnRatingValue = Double.valueOf(filters.get(columnRating).toString());
                
                resultComics = catalogue.findByNameAndRating(first,pageSize,
                        columnNameValue,columnRatingValue,sortField,sortOrder);
                long count = catalogue.getComicsCountFoundByNameAndRating(columnNameValue, columnRatingValue);
                this.setRowCount((int)count);
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
