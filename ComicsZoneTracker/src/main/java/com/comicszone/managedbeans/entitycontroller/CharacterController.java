/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.CharacterFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import com.comicszone.entitynetbeans.Character;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean(name="characterController")
@RequestScoped
public class CharacterController implements Serializable {
    @EJB
    public CharacterFacade characterFacade;
    public Integer characterId;
    public Character character;

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
    }
   
    
}
