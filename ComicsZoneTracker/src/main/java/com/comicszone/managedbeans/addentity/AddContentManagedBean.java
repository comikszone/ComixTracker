/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.addentity;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.ImprintFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.PublisherFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Imprint;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Publisher;
import com.comicszone.entitynetbeans.Volume;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
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
public class AddContentManagedBean implements Serializable {
    
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
    
    private String title = map.get("addContentForm:title");
    private String description = map.get("addContentForm:description_input");
    private String image = map.get("addContentForm:imageUrl");
    private String date = map.get("addContentForm:date");
    private String publisherName = map.get("addContentForm:publisher_input");
    private String imprintName = map.get("addContentForm:imprint");
    private String type = map.get("type");
    
    public int getId() {
        return Integer.parseInt(map.get("id"));
    }
    
    public String replaceNull(String var) {
        if (var.length() == 0) {
            return null;
        } else {
            return var;
        }
    }
    
    public Imprint findOrCreateImprint() {
        if (imprintName.length() == 0) {
            return null;
        } else {
            Imprint imprint = new Imprint(imprintName);
            if (imprintFacade.findByName(imprintName).isEmpty()) {
                imprintFacade.create(imprint);
            }
            return imprintFacade.findByName(imprintName).get(0);
        }
    }
    
    @PostConstruct
    public void init() {
        String msg = "Wrong type during init! Received is "+type;
        switch (type) { 
            case "issue" : volume = volumeFacade.find(getId()); break;
            case "volume" : comics = comicsFacade.find(getId()); break ;
            case "comics" : System.out.println("0"); break;
            default : context.addMessage(null, new FacesMessage("Error:", msg));
        }
    }
    
    public void addEntity() {
        try {
            if (title.length() == 0) {
                context.addMessage(null, new FacesMessage("Error:", "Title can't be empty"));
            } else {
                switch (type) {
                    case "issue" : issueFacade.createNew(title,
                                                         replaceNull(description),
                                                         replaceNull(image),
                                                         replaceNull(date),
                                                         "User",
                                                         volume); break;
                    case "volume" : volumeFacade.createNew(title,
                                                           replaceNull(description),
                                                           replaceNull(image),
                                                           "User",
                                                           comics); break;
                    case "comics" : comicsFacade.createNew(title,
                                                           replaceNull(description),
                                                           replaceNull(image),
                                                           publisherFacade.findByName(publisherName).get(0),
                                                           findOrCreateImprint(),
                                                          "User"); break;
                    default : context.addMessage(null, new FacesMessage("Error:", "Wrong entity type!"));
                }
                context.addMessage(null, new FacesMessage("Success! ", "New "+type+" added!"));
            }
        } catch (EJBException e) {
            context.addMessage(null, new FacesMessage("Error: ", "Such "+type+" already exists!"));
        }
    }
}

