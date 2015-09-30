/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.addentity;

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.ImprintFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.PublisherFacade;
import com.comicszone.dao.RealmFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Imprint;
import com.comicszone.entity.Issue;
import com.comicszone.entity.Volume;
import com.comicszone.entity.Character;
import com.comicszone.entity.Realm;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    @EJB
    RealmFacade realmFacade;
    @EJB
    CharacterFacade characterFacade;
    
    private Comics comics;
    private Volume volume;
    private Issue issue;
    private Character character;
    
    private FacesContext context = FacesContext.getCurrentInstance();
    private Map<String, String> map = context.getExternalContext().getRequestParameterMap();
    
    private String type = map.get("type");
    private int id = Integer.parseInt(map.get("id"));
    
    public void errorMsg(String msg) {
        context.addMessage(null, new FacesMessage("Error:", msg));
    }
    
    public void successMsg(String msg) {
        context.addMessage(null, new FacesMessage("Success!", msg));
    }
    
    @PostConstruct
    public void init() {
        switch (type) {
            case "comics" : comics = comicsFacade.find(id); break;
            case "volume" : volume = volumeFacade.find(id); break;
            case "issue" : issue = issueFacade.find(id); break;
            case "character" : character = characterFacade.find(id); break;
            default : errorMsg("Wrong entity type!");
        }
    }
    
    public Comics findComics() {
        return comicsFacade.findByName(volume.getComicsId().getName()).get(0);
    }
    
    public Volume findVolume() {
        return volumeFacade.findByName(issue.getVolumeId().getName()).get(0);
    }
    
    public Imprint findImprint() {
        if (comics.getImprintId()==null) {
            return null;
        } else {
            return imprintFacade.findByName(comics.getImprint()).get(0);
        }
    }
    
    public void editEntity() {
        if (getTitle().length() == 0) {
            errorMsg("Title can't be empty!");
        } else {
            switch (type) {
                case "comics" : Comics comicsEdit = new Comics(comics.getName(),
                                                               comics.getDescription(),
                                                               comics.getImage(),
                                                               comics.getPublisherId(),
                                                               findImprint(),
                                                               "User",
                                                               comics.getId());
                                comicsFacade.create(comicsEdit);
                                successMsg("Comics sent for approve!");
                                break;
                case "volume" : Volume volumeEdit = new Volume(volume.getName(),
                                                               volume.getDescription(),
                                                               volume.getImage(),
                                                               "User",
                                                               volume.getComicsId(),
                                                               volume.getId());
                                volumeFacade.create(volumeEdit);
                                successMsg("Volume sent for approve!");
                                break;
                case "issue" : Issue issueEdit = new Issue(issue.getName(),
                                                           issue.getDescription(),
                                                           issue.getImage(),
                                                           issue.getRelDate(),
                                                           "User",
                                                           issue.getVolumeId(),
                                                           issue.getId());
                                issueFacade.create(issueEdit);
                                successMsg("Issue sent for approve!");
                                break;
                case "character" : characterFacade.edit(character); break;
                default : errorMsg("Wrong entity type! Try revisiting the page.");
            }
        }
    }
    
    public String getTitle() {
        switch (type) {
            case "comics" : return comics.getName();
            case "volume" : return volume.getName();
            case "issue" : return issue.getName();
            case "character" : return character.getName();    
            default : return null;
        }
    }
    
    public void setTitle(String title) {
        switch (type) {
            case "comics" : comics.setName(title); break;
            case "volume" : volume.setName(title); break;
            case "issue" : issue.setName(title); break;
            case "character" : character.setName(title); break;
            default : return;
        }
    }
    
    public String getRealName() {
        return character.getRealName();
    }
    
    public void setRealName(String realName) {
        if (realName.length() == 0) {
            character.setRealName(null);
        } else {
            character.setRealName(realName);
        }
    }
    
    public String getDescription() {
        switch (type) {
            case "comics" : return comics.getDescription();
            case "volume" : return volume.getDescription();
            case "issue" : return issue.getDescription();
            case "character" : return character.getDescription();
            default : return null;
        }
    }
    
    public void setDescription(String description) {
        if (description.length() == 0) {
            switch (type) {
                case "comics" : comics.setDescription(null); break;
                case "volume" : volume.setDescription(null); break;
                case "issue" : issue.setDescription(null); break;
                case "character" : character.setDescription(null); break;
                default : return;
            }
        } else {
            switch (type) {
                case "comics" : comics.setDescription(description); break;
                case "volume" : volume.setDescription(description); break;
                case "issue" : issue.setDescription(description); break;
                case "character" : character.setDescription(description);break;
                default : return;
            }
        }
    }
    
    public String getImage() {
        switch (type) {
            case "comics" : return comics.getImage();
            case "volume" : return volume.getImage();
            case "issue" : return issue.getImage();
            case "character" : return character.getImage();
            default : return null;
        }
    }
    
    public void setImage(String image) {
        if (image.length() == 0) {
            switch (type) {
                case "comics" : comics.setImage(null); break;
                case "volume" : volume.setImage(null); break;
                case "issue" : issue.setImage(null); break;
                case "character" : character.setImage(null);break;
                default : return;
            }
        } else {
            switch (type) {
                case "comics" : comics.setImage(image); break;
                case "volume" : volume.setImage(image); break;
                case "issue" : issue.setImage(image); break;
                case "character" : character.setImage(image);break;
                default : return;
            }
        }
    }
    
    public String getPublisher() {
        switch (type) {
            case "comics" : return comics.getPublisherId().getName();
            case "character" : return character.getPublisher();
            default : return null;
        }
    }
    
    public void setPublisher(String publisher) {
        switch (type) {
            case "comics" : comics.setPublisherId(publisherFacade.findByName(publisher).get(0)); break;
            case "character" : character.setPublisherId(publisherFacade.findByName(publisher).get(0)); break;
            default : return;
        }
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
    
    public String getRealm() {
        return character.getRealm();
    }
    
    public void setRealm(String realm) {
        if (realm.length() == 0) {
            character.setRealmId(null);
        } else {
            Realm rlm = new Realm(realm);
            if (realmFacade.findByName(realm).isEmpty()) {
                realmFacade.create(rlm);
            }
            character.setRealmId(rlm);
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