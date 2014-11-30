/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Character;
import com.comicszone.dao.util.PersistenceUtil;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author ArsenyPC
 */
@Stateless
public class CharacterDaoImpl extends AbstractDao<Character> implements Finder{

    public CharacterDaoImpl() {
        super(Character.class);
    }
    
    @Override
       public List<Character> findByNameStartsWith(String name)
    {
        EntityManager em=null;
        List<Character> results=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Character> query =em.createNamedQuery("Character.findByNameStartsWith", Character.class);
            query.setParameter("name", name.toLowerCase()+"%");
            results = query.getResultList();
            em.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if ((em!=null) && (em.isOpen()))
                em.close();
        }   
        return results;
    }
  
    
}
