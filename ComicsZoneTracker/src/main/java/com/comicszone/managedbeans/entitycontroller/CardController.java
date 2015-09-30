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
import javax.enterprise.inject.Model;
import javax.inject.Named;
import org.json.JSONObject;
import org.omnifaces.cdi.Eager;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author nofuture
 */
@Named("ctrl")
@Model
public class CardController implements Serializable {
    private JSONObject card;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getCard() {
        return card;
    }

    public void setCard(String cardd) {
        this.card = new JSONObject(cardd);
        if (card.has("name") && !card.getString("name").equals("Writers")) {
            card.put("Name", name);
            name = card.getString("name");
            card.remove("name");
        } else if(card.has("name")) 
            card.remove("name");
    }
    
    public String[] LHS(){
        return (String[]) card.keySet().toArray(new String[card.keySet().size()]);
    }
    
    public String RHS(String key){
        return card.getString(key);
    }
    
}
