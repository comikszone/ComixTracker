package com.comicszone.dao.userdao;

import com.comicszone.entitynetbeans.Users;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserDataFacade extends AbstractUserFacade {

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    Users user;

    public void findUserByNickname(String nickname) {
        if (user == null)
            user = getUserWithNickname(nickname);
    }

    public String getNickname() {
        return user.getNickname();
    }

    public byte[] getAvatar() {
        return user.getAvatar();
    }

    public int getSex() {
        return user.getSex();
    }

    public Date getBirthday() {
        return user.getBirthday();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
