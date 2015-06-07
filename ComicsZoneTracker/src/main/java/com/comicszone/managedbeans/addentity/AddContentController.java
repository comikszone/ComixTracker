/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.addentity;

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.ImprintFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.PublisherFacade;
import com.comicszone.dao.RealmFacade;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Imprint;
import com.comicszone.entity.Issue;
import com.comicszone.entity.Character;
import com.comicszone.entity.Realm;
import com.comicszone.entity.Volume;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author Phoenix1092
 */

@Named
@RequestScoped
public class AddContentController implements Serializable {
    
    @EJB
    ComicsFacade comicsFacade;
    @EJB
    VolumeFacade volumeFacade;
    @EJB
    IssueFacade issueFacade;
    @EJB
    CharacterFacade characterFacade;
    @EJB
    PublisherFacade publisherFacade;
    @EJB
    ImprintFacade imprintFacade;
    @EJB
    RealmFacade realmFacade;
    
    private Comics comics;
    private Volume volume;
    private Issue issue;
    private Character character;
    
    private FacesContext context = FacesContext.getCurrentInstance();
    private Map<String, String> map = context.getExternalContext().getRequestParameterMap();
    
    private String title = map.get("addContentForm:title");
    private String realName = map.get("addContentForm:realName");
    private String description = map.get("addContentForm:description_input");
    private String image = map.get("addContentForm:imageUrl");
    private String date = map.get("addContentForm:date");
    private String publisherName = map.get("addContentForm:publisher_input");
    private String imprintName = map.get("addContentForm:imprint");
    private String realmName = map.get("addContentForm:realm");
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
    
    public Realm findOrCreateRealm() {
        if (realmName.length() == 0) {
            return null;
        } else {
            Realm realm = new Realm(realmName);
            if (realmFacade.findByName(realmName).isEmpty()) {
                realmFacade.create(realm);
            }
            return realmFacade.findByName(realmName).get(0);
        }
    }
    
    public String addOrReplaceImage() {
        if (image.length() == 0) {
            return "/resources/images/image_not_found.png";
        } else {
            return image;
        }
    }
    
    @PostConstruct
    public void init() {
        String msg = "Wrong type during init! Received is "+type;
        switch (type) { 
            case "issue" : volume = volumeFacade.find(getId()); break;
            case "volume" : comics = comicsFacade.find(getId()); break ;
            case "character" : character = characterFacade.find(getId()); break;
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
                                                         addOrReplaceImage(),
                                                         replaceNull(date),
                                                         "User",
                                                         volume); break;
                    case "volume" : volumeFacade.createNew(title,
                                                           replaceNull(description),
                                                           addOrReplaceImage(),
                                                           "User",
                                                           comics); break;
                    case "comics" : comicsFacade.createNew(title,
                                                           replaceNull(description),
                                                           addOrReplaceImage(),
                                                           publisherFacade.findByName(publisherName).get(0),
                                                           findOrCreateImprint(),
                                                          "User"); break;
                    case "character" : characterFacade.createNew(title,
                                                                 replaceNull(realName),
                                                                 replaceNull(description), 
                                                                 addOrReplaceImage(), 
                                                                 publisherFacade.findByName(publisherName).get(0), 
                                                                 findOrCreateRealm(), 
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