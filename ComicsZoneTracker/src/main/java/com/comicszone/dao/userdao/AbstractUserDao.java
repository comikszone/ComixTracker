package com.comicszone.dao.userdao;

import com.comicszone.dao.AbstractDao;
import com.comicszone.dao.util.PersistenceUtil;
import com.comicszone.entitynetbeans.Users;
import java.util.List;
import javax.persistence.EntityManager;


public abstract class AbstractUserDao extends AbstractDao<Users> {

    public AbstractUserDao() {
        super(Users.class);
    }

    public Users getUserWithNickname(String nickname) throws IllegalStateException {
        EntityManager em = null;
        List<Users> users = null;
        try {
            em = PersistenceUtil.getEntityManagerFactory().createEntityManager();
            
            em.getTransaction().begin();

            users = em.createNamedQuery("Users.findByNickname")
                    .setParameter("nickname", nickname)
                    .getResultList();

            em.getTransaction().commit();
        }
        catch (Throwable ex){
            ex.printStackTrace();
            if (em != null && em.isOpen()){
               if (em.getTransaction().isActive())
                   em.getTransaction().rollback();
               em.close();
            }
            return null;
        }
        finally{
            if ((em!=null) && (em.isOpen()))
                em.close();
        }
        
        if (users.size() > 1) {
            throw new IllegalStateException("Database contains some users with one nickname!");
        }

        return (users.isEmpty() ? null : users.get(0));

    }
}
