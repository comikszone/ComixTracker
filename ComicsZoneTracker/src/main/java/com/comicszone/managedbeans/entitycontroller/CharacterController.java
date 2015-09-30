/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.CharacterFacade;
import javax.inject.Named;
import com.comicszone.entity.Character;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.Issue;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.omnifaces.cdi.ViewScoped;


/**
 *
 * @author ArsenyPC
 */
@Named("characterController")
@ViewScoped
public class CharacterController implements Serializable {

    @EJB
    private CharacterFacade characterFacade;
    private Integer characterId;
    private Character character;
    
    @Inject
    private CardController ctrl;
    
    public CardController getCtrl() {
        return ctrl;
    }
    
    public void setCtrl(CardController ctrl) {
        this.ctrl = ctrl;
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
    
    public void initCharacter()
    {
        character=characterFacade.find(characterId);
        ctrl.setCard(character.getCard());
    }
    
    public String getProperIssueName(Issue issue){
        ctrl.setName(issue.getName());
        ctrl.setCard(issue.getCard());
        return ctrl.getName();
    } 
}