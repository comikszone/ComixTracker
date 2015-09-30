/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.password_recovery;

import com.comicszone.dao.user.UserDataFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

/**
 *
 * @author ArsenyPC
 */
@Named
@RequestScoped
public class EmailController implements Serializable {
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
        message=userDataFacade.sendEmail(email);
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
