/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Eschenko_DA
 */
@FacesConverter("ratingConverter")
public class RatingConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            
                ComicsFinderManagedBean ratingFinderManagedBean = (ComicsFinderManagedBean) 
                        fc.getExternalContext().getApplicationMap().get("comicsFinderManagedBean");
                System.err.println("**ratingFinderBean" + ratingFinderManagedBean);
                System.err.println("**ratings" + ratingFinderManagedBean.getRatings());
                System.err.println("**value" + value);
                System.err.println("**currentRating" + ratingFinderManagedBean.getRating(Integer.parseInt(value)).getValue());
                return ratingFinderManagedBean.getRating(Integer.parseInt(value));
            
        }
        else {
            return null;
        }
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            System.err.println("****BBBBBB**");
            System.err.println("***stringRating + "+String.valueOf(((Rating) object).getValue()));
            return String.valueOf(((Rating) object).getValue());
            
        }
        else {
            return null;
        }
    }  
}
