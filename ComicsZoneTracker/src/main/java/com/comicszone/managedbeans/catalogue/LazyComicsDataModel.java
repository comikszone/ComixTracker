/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Eschenko_DA
 */
public class LazyComicsDataModel extends LazyDataModel<Comics> {
    
    @EJB
    private ComicsFacade comicsFacade;
    
    private final List<Comics> datasource;
    
    public LazyComicsDataModel(List<Comics> datasource) {
        this.datasource = datasource;
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
        
        
        /*Logger log = Logger.getLogger(LazyComicsComparator.class.getName());*/
        /*for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
            String filterProperty = it.next();
            List<Comics> result = comicsFacade.findByNameStartsWith((String)filters.get(filterProperty));
            //comicsFacade.findByNameStartsWith((String)filters.get(filterProperty)));
            data.addAll(result);
            //log.logp(Level.ALL, filterProperty, (String)filters.get(filterProperty), sortField);
            //System.out.println(filterProperty +" " + filters.get(filterProperty));
        }*/
        
        //filter
        for(Comics comics : datasource) {
            boolean match = true;
 
            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty);
                        Field filterField  = comics.getClass().getDeclaredField(filterProperty);
                        filterField.setAccessible(true);
                        String fieldValue = String.valueOf(filterField.get(comics));
                        if(filterValue == null || fieldValue.startsWith(filterValue.toString())) {  
                            match = true;
                        }
                        else {
                            match = false;
                            break;
                        }
                    } catch(Exception e) {
                        match = false;
                    }
                }
            }
 
            if(match) {
                data.add(comics);
            }
        }
 
        if(sortField != null) {
            
            Comics[] array = (Comics[])data.toArray();
            Arrays.sort(array, new LazyComicsComparator(sortField, sortOrder));
            //data.sort();
        }
 
        //rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);
 
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
