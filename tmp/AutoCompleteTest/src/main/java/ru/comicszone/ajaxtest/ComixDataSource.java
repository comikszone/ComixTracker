package ru.comicszone.ajaxtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

//import net.javabeat.primefaces.data.Comix;
@ManagedBean(name ="comixDataSource", eager = true)
//@SessionScoped
public class ComixDataSource {
    	public List<Comix> comixs = new ArrayList<Comix>();
    @PostConstruct
    public void init()
    {
        Comix comix = new Comix();
        comix.setComixId("0");
        comix.setComixName("Marvel - 1");
        comix.setComixDescription("The best marvel comix!!!");

        // Add Comix
        comixs.add(comix);

        // Assumed Comix
        comix = new Comix();
        comix.setComixId("1");
        comix.setComixName("DC comix");
        comix.setComixDescription("Usual comix");

        // Add Comix
        comixs.add(comix);

        // Assumed Comix
        comix = new Comix();
        comix.setComixId("2");
        comix.setComixName("Alisa");
        comix.setComixDescription("My favourite comix");

        // Add Comix
        comixs.add(comix);
         FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("key",comixs);
    }
	public List<Comix> getComixs() {

        return comixs;
	}

	public void setComixs(List<Comix> comixs)
    {
        this.comixs = comixs;
	} 
	
	public List<Comix> queryByName(String name){
		List<Comix> queried = new ArrayList<Comix>();
		for(Comix comix: this.comixs){
			if(comix.getComixName().toLowerCase().contains(name.toLowerCase())){
				queried.add(comix);
			}
		}
		return queried;
	}

}
