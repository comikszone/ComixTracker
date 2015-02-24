package com.comicszone.dao.userdao;

import com.comicszone.dao.groupdao.GroupFacade;
import com.comicszone.dao.util.encryption.*;
import com.comicszone.entitynetbeans.UserGroup;
import com.comicszone.entitynetbeans.Users;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserRegistrationFacade extends AbstractUserFacade {

    @EJB
    private GroupFacade groupFacade;

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public void registration(String nickname, String email, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Password isn't equals confirmPassword");
        }

        SHA256SimpleSaltedEncryptor encryptor = new SHA256SimpleSaltedEncryptor();
        password = encryptor.getEncodedPassword(password, nickname);
        Users user = new Users(nickname, email, password);

        create(user);

        UserGroup group = new UserGroup("user", nickname);
        groupFacade.create(group);
    }

    public void socialNetworkRegistration(Users user, String password) {
        SHA256SimpleSaltedEncryptor encryptor = new SHA256SimpleSaltedEncryptor();
        password = encryptor.getEncodedPassword(password, user.getNickname());
        
        Users tempUser = getUserWithNickname(user.getNickname());
        if (tempUser != null) {
            tempUser.setPass(password);
//            tempUser.setBirthday(user.getBirthday());
            tempUser.setAvatarUrl(user.getAvatarUrl());
//            tempUser.setName(user.getName());
            edit(tempUser);
            return;
        }

        user.setPass(password);
        user.setRealNickname(user.getName());
        user.setIsSocial(Boolean.TRUE);
        create(user);

        UserGroup group = new UserGroup("user", user.getNickname());
        groupFacade.create(group);

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
