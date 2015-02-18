/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.recovery_password;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.dao.userdao.UserRegistrationFacade;
import com.comicszone.dao.util.encryption.IPasswordEncryptor;
import com.comicszone.dao.util.encryption.SHA256Encriptor;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.managedbeans.util.passwordcreators.IPasswordCreator;
import com.comicszone.managedbeans.util.passwordcreators.SimplePasswordCreator;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@RequestScoped
public class EmailSenderManagedBean implements Serializable {
    @EJB
    private UserDataFacade userDataFacade;
    private final int PASSWORD_LENGTH = 20;
    private String message;
    private String errorMessage;
    public void sendEmail() throws MessagingException, IOException{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = externalContext.getRequestParameterMap();
        String email=parameterMap.get("recoveryPasswordForm:email");
        List<Users> users=userDataFacade.findUserByEmail(email);
        if (users==null || users.size()!=1)
        {
            message="Can't find that email, sorry";
        }
        else
        {
            message="We’ve sent you an email containing a link that will allow you to reset your password for the next 24 hours. ";
            Users user=users.get(0);
            IPasswordCreator passwordCreator = new SimplePasswordCreator();
            String uid = passwordCreator.createPassword(PASSWORD_LENGTH);
            IPasswordEncryptor encryptor = new SHA256Encriptor();
            String encryptedUid = encryptor.getEncodedPassword(uid);
            user.setRecoveryPasswordId(encryptedUid);
            user.setRecoveryPasswordTime(new Date(System.currentTimeMillis()));
            userDataFacade.edit(user);
            SmtpMessageSender messageSender=new SmtpMessageSender();
            String link="https://www.comicszonetracker.tk/resources/templates/unauthorized/new_password.jsf?uid=" + uid;
            String href="<a href='"+link+"'>"+link+"</a>";
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append("We heard that you lost your ComicsZoneTracker password. Sorry about that!<br/>");
            stringBuilder.append("But don't worry! You can use the following link to reset your password:<br/> <br/>");
            stringBuilder.append(href);
            stringBuilder.append("<br/> <br/>");
            stringBuilder.append("If you don't use this link within 24 hours, it will expire. To get a new password reset link, visit");
            stringBuilder.append("https://www.comicszonetracker.tk/resources/templates/unauthorized/recover_password.jsf<br/> <br/>");
            stringBuilder.append("Thanks,<br/>");
            stringBuilder.append("Your friends at ComicsZoneTracker");
            String text=stringBuilder.toString();
            messageSender.sendEmail(email, text);
        }
    }

    public String getMessage() {
        return message;
    }   

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        message="It looks like you clicked on an invalid password reset link. Please try again.";
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
}
