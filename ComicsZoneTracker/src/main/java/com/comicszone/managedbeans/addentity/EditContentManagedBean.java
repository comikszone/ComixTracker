/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.addentity;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.ImprintFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.PublisherFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Imprint;
import com.comicszone.entity.Issue;
import com.comicszone.entity.Volume;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Phoenix1092
 */

@ManagedBean
@RequestScoped
public class EditContentManagedBean implements Serializable {
    
    @EJB
    ComicsFacade comicsFacade;
    @EJB
    VolumeFacade volumeFacade;
    @EJB
    IssueFacade issueFacade;
    @EJB
    PublisherFacade publisherFacade;
    @EJB
    ImprintFacade imprintFacade;
    
    private Comics comics;
    private Volume volume;
    private Issue issue;
    
    private FacesContext context = FacesContext.getCurrentInstance();
    private Map<String, String> map = context.getExternalContext().getRequestParameterMap();
    
    private String type = map.get("type");
    private int id = Integer.parseInt(map.get("id"));
    
    public void errorMsg(String msg) {
        context.addMessage(null, new FacesMessage("Error:", msg));
    }
    
    @PostConstruct
    public void init() {
        switch (type) {
            case "comics" : comics = comicsFacade.find(id); break;
            case "volume" : volume = volumeFacade.find(id); break;
            case "issue" : issue = issueFacade.find(id); break;
            default : errorMsg("Wrong entity type!");
        }
    }
    
    public void editEntity() {
        if (getTitle().length() == 0) {
            errorMsg("Title can't be empty!");
        } else {
            switch (type) {
                case "comics" : comicsFacade.edit(comics); break;
                case "volume" : volumeFacade.edit(volume); break;
                case "issue" : issueFacade.edit(issue); break;
                default : errorMsg("Wrong entity type! Try revisiting the page.");
            }
        }
    }
    
    public String getTitle() {
        switch (type) {
            case "comics" : return comics.getName();
            case "volume" : return volume.getName();
            case "issue" : return issue.getName();
            default : return null;
        }
    }
    
    public void setTitle(String title) {
        switch (type) {
            case "comics" : comics.setName(title); break;
            case "volume" : volume.setName(title); break;
            case "issue" : issue.setName(title); break;
            default : return;
        }
    }
    
    public String getDescription() {
        switch (type) {
            case "comics" : return comics.getDescription();
            case "volume" : return volume.getDescription();
            case "issue" : return issue.getDescription();
            default : return null;
        }
    }
    
    public void setDescription(String description) {
        if (description.length() == 0) {
            switch (type) {
                case "comics" : comics.setDescription(null); break;
                case "volume" : volume.setDescription(null); break;
                case "issue" : issue.setDescription(null); break;
                default : return;
            }
        } else {
            switch (type) {
                case "comics" : comics.setDescription(description); break;
                case "volume" : volume.setDescription(description); break;
                case "issue" : issue.setDescription(description); break;
                default : return;
            }
        }
    }
    
    public String getImage() {
        switch (type) {
            case "comics" : return comics.getImage();
            case "volume" : return volume.getImage();
            case "issue" : return issue.getImage();
            default : return null;
        }
    }
    
    public void setImage(String image) {
        if (image.length() == 0) {
            switch (type) {
                case "comics" : comics.setImage(null); break;
                case "volume" : volume.setImage(null); break;
                case "issue" : issue.setImage(null); break;
                default : return;
            }
        } else {
            switch (type) {
                case "comics" : comics.setImage(image); break;
                case "volume" : volume.setImage(image); break;
                case "issue" : issue.setImage(image); break;
                default : return;
            }
        }
    }
    
    public String getPublisher() {
        return comics.getPublisherId().getName();
    }
    
    public void setPublisher(String publisher) {
        comics.setPublisherId(publisherFacade.findByName(publisher).get(0));
    }
    
    public String getImprint() {
        return comics.getImprintId().getName();
    }
    
    public void setImprint(String imprint) {
        if (imprint.length() == 0) {
            comics.setImprintId(null);
        } else {
            Imprint imp = new Imprint(imprint);
            if (imprintFacade.findByName(imprint).isEmpty()) {
                imprintFacade.create(imp);
            }
            comics.setImprintId(imp);
        }
    }
    
    public String getDate() {
        return issue.getRelDate();
    }
    
    public void setDate(String date) {
        if (date.length() == 0) {
            issue.setRelDate(null);
        } else {
            issue.setRelDate(date);
        }
    }
}
