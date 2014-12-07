/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Character;
import com.comicszone.entitynetbeans.Comics;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author ArsenyPC
 */
@Stateless
@LocalBean
public class CharacterFacade extends AbstractFacade<Character> implements Finder{
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CharacterFacade() {
        super(Character.class);
    }

    @Override
    public List<? extends AjaxComicsCharacter> findByNameStartsWith(String name) {
            TypedQuery<Character> query =em.createNamedQuery("Character.findByNameStartsWith", Character.class);
            query.setParameter("name", name.toLowerCase()+"%");
            return query.getResultList();
        }
    
}
