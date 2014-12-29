/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    
    
    private ComicsFacade comicsFacade;
    
    private final List<Comics> datasource;
    private final String columnComicsName;
    private final String columnComicsRating;
    
    public LazyComicsDataModel(List<Comics> datasource, ComicsFacade comicsFacade,
                            String columnComicsName, String columnComicsRating) {
        this.datasource = datasource;
        this.comicsFacade = comicsFacade;
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
        
        List<Comics> data = new ArrayList<Comics>();
        List<Comics> resultComics;
        
        //filter
        Iterator<String> itFilter = filters.keySet().iterator();
        if(itFilter.hasNext()) {
            
            String nextColumn = itFilter.next();
            String nextColumnValue = filters.get(nextColumn).toString();
            
            if (nextColumn.equals(columnComicsName) && !itFilter.hasNext()) {
                resultComics = comicsFacade.findByName(first+pageSize,nextColumnValue);
                this.setRowCount((int)comicsFacade.getComicsCountFoundByName(nextColumnValue));
            }
            else if (nextColumn.equals(columnComicsRating) && !itFilter.hasNext()) {
                Double rating = Double.valueOf(nextColumnValue);
                resultComics = comicsFacade.findByRating(first+pageSize, rating);
                this.setRowCount((int)comicsFacade.getComicsCountFoundByRating(rating));
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
                
                
                resultComics = comicsFacade.findByNameAndRating(first+pageSize,columnNameValue,columnRatingValue);
                long count = comicsFacade.getComicsCountFoundByNameAndRating(columnNameValue, columnRatingValue);
                this.setRowCount((int)count);
            }
            
        }
        else {
            resultComics = comicsFacade.findAllForCatalogue(first+pageSize,sortField,sortOrder);
            
            this.setRowCount((int)(comicsFacade.getComicsCount()));
        }
        
        data.addAll(resultComics);
        
        //rowCount
        int dataSize = data.size();
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
