/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.addentity;

import com.comicszone.dao.content.ContentFacade;
import com.comicszone.entity.Comics;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Phoenix1092
 */

@ManagedBean
public class AddContentManagedBean implements Serializable {
    
    @EJB
    ContentFacade contentFacade;
    
    private String title;
    private String description;
    private String date;
    private String publisher;
    private String imprint;
    private Byte image;
    private FacesContext context = FacesContext.getCurrentInstance();
    private Map<String, String> map = context.getExternalContext().getRequestParameterMap();
    
    public String getTitle() {
        return title;
    }
 
    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getDescription() {
        return description;
    }
 
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getPublisher() {
        return publisher;
    }
 
    public void setPublisher(String publisher) {
        this.publisher = title;
    }
    
    public String getImprint() {
        return imprint;
    }
 
    public void setImprint(String imprint) {
        this.imprint = imprint;
    }
    
    public String getDate() {
        return date;
    }
 
    public void setDate(String date) {
        this.date = date;
    }
    
    public Byte getImage() {
        return image;
    }
 
    public void setImage(Byte image) {
        this.image = image;
    }
    
    public String getTypeParam(){
        return map.get("type");
    }
    
    private UploadedFile file;
 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}


