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
import java.util.Set;
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
    
    
    private ComicsFacade comicsFacade;
    
    private final List<Comics> datasource;
    
    public LazyComicsDataModel(List<Comics> datasource, ComicsFacade comicsFacade) {
        this.datasource = datasource;
        this.comicsFacade = comicsFacade;
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
        List<Comics> result;
        
        /*for (Iterator<String> ittt = filters.keySet().iterator(); ittt.hasNext();) {*/
        Iterator<String> ittt = filters.keySet().iterator();
        //Set<String> set = filters.keySet();
        if (ittt.hasNext()) {
            String filterProperty = ittt.next();
            String filterValue = filters.get(filterProperty).toString();
            
            Comics c = new Comics();
            c.setName(filterProperty);
            c.setDescription(filterValue);
            data.add(c);
                
            result = comicsFacade.findByNameStartsWith(filterValue);
            data.addAll(result);
        }
        
        /*Logger log = Logger.getLogger(LazyComicsComparator.class.getName());*/
        //for (Iterator<String> itt = filters.keySet().iterator(); itt.hasNext();) {
            /*Iterator<String> itt = filters.keySet().iterator();
            if (itt.hasNext()) {
                String filterProperty = itt.next();
                String filterValue = filters.get(filterProperty).toString();
                Comics c = new Comics();
                c.setName(filterProperty);
                c.setDescription(filterValue);
                data.add(c);
                //List<Comics> result = comicsFacade.findByNameStartsWith(filterValue);
            //comicsFacade.findByNameStartsWith((String)filters.get(filterProperty)));
                //data.addAll(result);
            }
            if (itt.hasNext()) {
                String filterProperty = itt.next();
                String filterValue = filters.get(filterProperty).toString();
                Comics c = new Comics();
                c.setName(filterProperty);
                c.setDescription(filterValue);
                data.add(c);
            }*/
            //log.logp(Level.ALL, filterProperty, (String)filters.get(filterProperty), sortField);
            //System.out.println(filterProperty +" " + filters.get(filterProperty));
        //}
        
        //filter
        /*for(Comics comics : datasource) {
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
        }*/
 
        /*if(sortField != null) {
            
            Comics[] array = (Comics[])data.toArray();
            Arrays.sort(array, new LazyComicsComparator(sortField, sortOrder));
            //data.sort();
        }*/
 
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
