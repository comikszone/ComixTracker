/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
/**
 *
 * @author Eschenko_DA
 */
@FacesConverter("ratingConverter")
public class RatingConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0 && !value.equalsIgnoreCase("All ratings")) {
            ComicsFinderManagedBean ratingFinderManagedBean = (ComicsFinderManagedBean)
                    fc.getExternalContext().getApplicationMap().get("comicsFinderManagedBean");
            return ratingFinderManagedBean.getRating(Integer.parseInt(value));
        }
        else {
            return null;
        }
    }
    
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        return object != null ? String.valueOf(((Rating) object).getValue()) : null;
    }  
}
