/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import com.comicszone.entitynetbeans.Comics;
import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author yesch_000
 */
public class LazyComicsComparator implements Comparator<Comics> {
    
    private String sortFieldString;
    private SortOrder sortOrder;
     
    public LazyComicsComparator(String sortField, SortOrder sortOrder) {
        this.sortFieldString = sortField;
        this.sortOrder = sortOrder;
    }
    
    @Override
    public int compare(Comics comics1, Comics comics2) {
        try {
            //LOG.trace("Field!");
            Field sortField = Comics.class.getDeclaredField(this.sortFieldString);
            sortField.setAccessible(true);
            Object value1 = sortField.get(comics1);
            Object value2 = sortField.get(comics2);
            
            int value = ((Comparable)value1).compareTo(value2);
             
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            e.printStackTrace();  
        }
        return 0;
    }
}
