package com.comicszone.dao.userdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entitynetbeans.Users;
import java.util.List;
import javax.persistence.TypedQuery;

public abstract class AbstractUserFacade extends AbstractFacade<Users> {

    public AbstractUserFacade() {
        super(Users.class);
    }

    public Users getUserWithNickname(String nickname) {

        List<Users> users = null;

        users = getEntityManager().createNamedQuery("Users.findByNickname")
                .setParameter("nickname", nickname)
                .getResultList();

        if (users.size() > 1) {
            throw new IllegalStateException("Database contains some users with one nickname!");
        }

        return (users.isEmpty() ? null : users.get(0));

    }
    
    public List<Users> getUsersWithNicknameStartsWith(String nickname) {
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findByNicknameStartsWith", Users.class);
        query.setParameter("nickname", nickname.toLowerCase() + "%");
        query.setMaxResults(5);
        return query.getResultList();
    }
    
        public List<Users> getUsersWithRealNicknameStartsWith(String realNickname) {
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findByRealNicknameStartsWith", Users.class);
        query.setParameter("realNickname", realNickname.toLowerCase() + "%");
        query.setMaxResults(5);
        return query.getResultList();
    }
    
    public List<Users> getBannedUsersWithNicknameStartsWith(boolean banned, String nickname) {
        TypedQuery<Users> query = getEntityManager().
                createNamedQuery("Users.findBannedOrUnbannedByNicknameStartsWith", Users.class);
        query.setParameter("banned", banned);
        query.setParameter("nickname", nickname.toLowerCase() + "%");
        query.setMaxResults(5);
        return query.getResultList();
    }
}
