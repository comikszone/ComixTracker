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
    private static final Logger logger;
    static
    {
        FileHandler handler= null;
        try
        {
            handler = new FileHandler("c:\\Users\\Арсений\\Documents\\Учеба\\NetCracker\\jsf\\PrimeFaces-AutoCompletePojo123\\logging.log");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        logger= Logger.getLogger(Comix.class.getName());
        logger.addHandler(handler);
        SimpleFormatter formatter=new SimpleFormatter();
        handler.setFormatter(formatter);
    }
	@ManagedProperty("#{comixDataSource}")
	private ComixDataSource ds;
	
	public ComixDataSource getDs()
    {
        logger.info("getDs()");
		return ds;
	}

	public void setDs(ComixDataSource ds)
    {
        logger.info("setDs(ComixDataSource ds)");
		this.ds = ds;
	}

//    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
//        if(value != null && value.trim().length() > 0) {
//            try {
//                ComixDataSource service = (ComixDataSource) fc.getExternalContext().getApplicationMap().get("themeService");
//                return service.getComixs().get(Integer.parseInt(value));
//            } catch(NumberFormatException e) {
//                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
//            }
//        }
//        else {
//            return null;
//        }
//    }

//    @Override
//	public Object getAsObject(FacesContext context, UIComponent component,String value)
//    {
//        logger.info("getAsObject(FacesContext context, UIComponent component,String value) has invoked");
//        logger.info("value"+value);
//        for(Comix p : ds.getComixs()){
//			if(p.getComixId().equals(value)){
//                logger.info(p.toString());
//				return p;
//			}
//		}
//		return null;
//	}
public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
    if(value != null && value.trim().length() > 0) {
        try {
            List<Comix> comixes= (List<Comix>) fc.getExternalContext().getApplicationMap().get("key");
            logger.info("COMIXES SIZE="+comixes.size());
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
        logger.info("getAsString(FacesContext context, UIComponent component,Object value) has invoked");
        logger.info(value.toString());
        if(value instanceof Comix){
            logger.info("(value instanceof Comix)");
			Comix comix = (Comix)value;
            logger.info(comix.getComixName());
            logger.info(comix.toString());
			return comix.getComixId();
		}
		return "";
	}

}
