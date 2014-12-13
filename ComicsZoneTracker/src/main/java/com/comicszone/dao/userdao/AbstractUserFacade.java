package com.comicszone.dao.userdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entitynetbeans.Users;
import java.util.List;

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
}
