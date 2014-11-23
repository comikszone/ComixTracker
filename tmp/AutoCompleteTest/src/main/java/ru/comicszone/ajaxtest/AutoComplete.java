package ru.comicszone.ajaxtest;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;

//import net.javabeat.primefaces.data.Comix;
//import net.javabeat.primefaces.util.ComixDataSource;
//import org.apache.log4j.Logger;

@ManagedBean
//@SessionScoped
public class AutoComplete {

	@ManagedProperty("#{comixDataSource}")
	private ComixDataSource ds;
	
	private Comix comix;
	public AutoComplete (){
	}
	
	public List<Comix> complete(String query){
        return ds.queryByName(query);
	}

	public Comix getComix() {
		return comix;
	}

	public void setComix(Comix comix) {
        this.comix = comix;
	}

	public ComixDataSource getDs() {
		return ds;
	}

	public void setDs(ComixDataSource ds) {
		this.ds = ds;
	}
	public String showChoice()
    {
        return "index1";
    }
}
