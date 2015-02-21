/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.mainsearch.autocomplete;

/**
 *
 * @author ArsenyPC
 */
import com.comicszone.entitynetbeans.AjaxComicsCharacter;
import com.comicszone.dao.Finder;
//import com.netcracker.entitynetbeans.Comics;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
 
//import org.primefaces.showcase.domain.Theme;
//import org.primefaces.showcase.service.ThemeService;
 
@FacesConverter("ajaxComicsCharacterConverter")
public class AjaxComicsConverter implements Converter {
 
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                Finder finder=(Finder) FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get("finder");
                return finder.find(Integer.parseInt(value));
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid comics."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((AjaxComicsCharacter) object).getId());
        }
        else {
            return null;
        }
    }   
}     
