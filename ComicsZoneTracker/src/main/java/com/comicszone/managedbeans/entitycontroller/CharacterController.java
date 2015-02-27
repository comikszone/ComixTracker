/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.CharacterFacade;
import javax.faces.bean.ManagedBean;
import com.comicszone.entitynetbeans.Character;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean(name="characterController")
@ViewScoped
public class CharacterController implements Serializable {

    @EJB
    private CharacterFacade characterFacade;
    private Integer characterId;
    private Character character;
    @ManagedProperty(value = "#{ctrl}")
    private CardCtrl ctrl;
    
    public CardCtrl getCtrl() {
        return ctrl;
    }
    
    public String redirect(Character character){
        return "/resources/pages/characterPage.jsf?faces-redirect=true&id=" + character.getId();
    }

    public void setCtrl(CardCtrl ctrl) {
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
   
    
}
