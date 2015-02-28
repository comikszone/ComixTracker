/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.json.JSONObject;

/**
 *
 * @author nofuture
 */
@ManagedBean(name = "ctrl")
@ViewScoped
public class CardCtrl implements Serializable {
    private JSONObject card;
    private String name;

    public String getName() {
        return name;
    }
    
    public void init(){
        if (card.has("name") && card.getString(name) != "Writers") {
            card.put("Name", name);
            name = card.getString("name");
            card.remove("name");
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private CharacterFacade characterFacade;

    public JSONObject getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = new JSONObject(card);
    }
    
    public String[] LHS(){
        return (String[]) card.keySet().toArray(new String[card.keySet().size()]);
    }
    
    public String RHS(String key){
        return card.getString(key);
    }
    
}
