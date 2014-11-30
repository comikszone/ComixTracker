/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
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
public class ComicsDaoImpl extends AbstractDao<Comics> implements Finder, SlideshowFacade{
    public ComicsDaoImpl() {
        super(Comics.class);
    }
    
    @Override
    public List<Comics> findByNameStartsWith(String name)
    {
        EntityManager em=null;
        List<Comics> results=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Comics> query =em.createNamedQuery("Comics.findByNameStartsWith", Comics.class);
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

    @Override
    public List<Comics> get12Best() {
        EntityManager em=null;
        List<Comics> results=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Comics> query =em.createNamedQuery("Comics.get12Best", Comics.class);
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
