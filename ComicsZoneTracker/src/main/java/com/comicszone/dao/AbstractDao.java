/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.dao.util.PersistenceUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
//import javax.persistence.PersistenceUtil;

/**
 *
 * @author ArsenyPC
 */
public abstract class AbstractDao<T> {
//    @PersistenceContext (unitName = "ComicsTracker1")
//    private EntityManager entityManager;
  private Class<T> entityClass;

  public AbstractDao(Class<T> entityClass) {
    this.entityClass = entityClass;
    //PersistenceUtil.getEntityManagerFactory().createEntityManager();
  }

//  protected abstract EntityManager getEntityManager();

  public void save(T entity){ 
        EntityManager em=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if ((em!=null) && (em.isOpen()))
                em.close();
        } 
    }

    public void edit(T entity) {
        EntityManager em=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        }
            catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if ((em!=null) && (em.isOpen()))
                em.close();
        } 
    }

  public void remove(T entity) {
        EntityManager em=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if ((em!=null) && (em.isOpen()))
                em.close();
        } 
    }
  

  public T findById(Object id) {
        EntityManager em=null;
        T entity=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            entity=em.find(entityClass, id);//?
            em.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if ((em!=null) && (em.isOpen()))
                em.close();
        } 
        return entity;
    }

  public List<T> findAll() {
       EntityManager em=null;
       List<T> entities=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClass));
            entities=em.createQuery(cq).getResultList();
            em.getTransaction().commit();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            if ((em!=null) && (em.isOpen()))
                em.close();
        }
        return entities;
  }
  public List<T> findByName(String name,String parameter)
{
        EntityManager em=null;
        List<T> results=null;
        try
        {
            em=PersistenceUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<T> query =em.createQuery("SELECT c FROM "+entityClass.getName()+" c WHERE c."+ parameter+"=:name", entityClass);
            query.setParameter("name", name);
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
  