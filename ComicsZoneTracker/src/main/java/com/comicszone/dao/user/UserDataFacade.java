package com.comicszone.dao.user;

import com.comicszone.dao.util.encryption.IPasswordEncryptor;
import com.comicszone.dao.util.encryption.SHA256Encryptor;
import com.comicszone.dao.util.encryption.SHA256SimpleSaltedEncryptor;
import com.comicszone.entity.Users;
import com.comicszone.managedbeans.recovery_password.SmtpMessageSender;
import com.comicszone.managedbeans.util.passwordcreators.IPasswordCreator;
import com.comicszone.managedbeans.util.passwordcreators.SimplePasswordCreator;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class UserDataFacade extends AbstractUserFacade {

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Users> findUserByEmail(String email)
    {
        TypedQuery<Users> query=(TypedQuery<Users>) em.createNamedQuery("Users.findByEmail");
        query.setParameter("email", email);
        return query.getResultList();
    }
    public Users findUserByUid(String uid)
    {
        TypedQuery<Users> query=(TypedQuery<Users>) em.createNamedQuery("Users.findByRecoveryPasswordId");
        query.setParameter("uid", uid);
        List<Users> users= query.getResultList();
        if (users==null || users.isEmpty())
            return null;
        return users.get(0);
    }
    public String sendEmail(String email) throws IOException, MessagingException{
        List<Users> users=findUserByEmail(email);
        if (users==null || users.size()!=1)
        {
            return "Can't find that email, sorry";
        }
        else
        {
            Users user=users.get(0);
            IPasswordCreator passwordCreator = new SimplePasswordCreator();
            String uid = passwordCreator.createPassword(20);
            IPasswordEncryptor encryptor = new SHA256Encryptor();
            String encryptedUid = encryptor.getEncodedPassword(uid);
            user.setRecoveryPasswordId(encryptedUid);
            user.setRecoveryPasswordTime(new Date(System.currentTimeMillis()));
            edit(user);
            SmtpMessageSender messageSender=new SmtpMessageSender();
            String link="https://www.comicszonetracker.tk/resources/templates/unauthorized/new_password.jsf?uid=" + uid;
            String href="<a href='"+link+"'>"+link+"</a>";
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("We heard that you lost your ComicsZoneTracker password. Sorry about that!<br/>");
            stringBuilder.append("But don't worry! You can use the following link to reset your password:<br/> <br/>");
            stringBuilder.append(href);
            stringBuilder.append("<br/> <br/>");
            stringBuilder.append("If you don't use this link within 24 hours, it will expire. To get a new password reset link, visit ");
            stringBuilder.append("https://www.comicszonetracker.tk/resources/templates/unauthorized/recover_password.jsf<br/> <br/>");
            stringBuilder.append("Thanks,<br/>");
            stringBuilder.append("Your friends at ComicsZoneTracker");
            String text=stringBuilder.toString();
            messageSender.sendEmail(email, text);
            return "We’ve sent you an email containing a link that will allow you to reset your password for the next 24 hours. ";
        }
    }
    public String recoveryPassword(Users user, String password, String confirmPassword)
    {
        String message="Password can't be empty";
        if (password==null || !password.equals(confirmPassword))
        {
            message="Passwords don't match!";
        }
        else if (!password.equals(""))
        {
            SHA256SimpleSaltedEncryptor encryptor = new SHA256SimpleSaltedEncryptor();
            String encryptedPassword = encryptor.getEncodedPassword(password, user.getNickname());
            user.setPass(encryptedPassword);
            user.setRecoveryPasswordId(null);
            user.setRecoveryPasswordTime(null);
            edit(user);
            message="OK";
        }
        return message;
    }
}
