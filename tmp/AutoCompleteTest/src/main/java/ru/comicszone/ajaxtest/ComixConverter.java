package ru.comicszone.ajaxtest;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

//import net.javabeat.primefaces.ThemeService;
//import net.javabeat.primefaces.data.Comix;
//import net.javabeat.primefaces.util.ComixDataSource;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@FacesConverter 
public class ComixConverter implements Converter
{
	@ManagedProperty("#{comixDataSource}")
	private ComixDataSource ds;
	
	public ComixDataSource getDs()
    {
		return ds;
	}

	public void setDs(ComixDataSource ds)
    {
		this.ds = ds;
	}


public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    if(value != null && value.trim().length() > 0) {
        try {
            List<Comix> comixes= (List<Comix>) fc.getExternalContext().getApplicationMap().get("key");
            return comixes.get(Integer.parseInt(value));
        } catch(NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
        }
    }
    else {
        return null;
    }
}
	@Override
	public String getAsString(FacesContext context, UIComponent component,Object value)
    {
        if(value instanceof Comix){
			Comix comix = (Comix)value;
			return comix.getComixId();
		}
		return "";
	}

}
