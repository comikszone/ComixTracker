package com.comicszone.dao.userdao;

import com.comicszone.dao.groupdao.GroupDao;
import com.comicszone.dao.util.encryption.*;
import com.comicszone.entitynetbeans.UserGroup;
import com.comicszone.entitynetbeans.Users;
import javax.ejb.Stateless;

@Stateless
public class UserRegistrationDao extends AbstractUserDao{
    
    
    public void registration(String nickname, String email, String password, String confirmPassword){
        if (!password.equals(confirmPassword))
            throw new IllegalArgumentException("Password isn't equals confirmPassword");
        
        if (getUserWithNickname(nickname) != null)
            throw new IllegalStateException("User with this nickname already exist!");
        
        IPasswordEncryptor encryptor = new SHA256Encriptor();
        password = encryptor.getEncodedPassword(password);
        Users user = new Users(nickname, email, password);
        
        
        save(user);
        
        UserGroup group = new UserGroup("user", nickname);
        GroupDao groupDao = new GroupDao();
        groupDao.save(group);
    }
}
