/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.recovery_password;

import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.dao.util.encryption.IPasswordEncryptor;
import com.comicszone.dao.util.encryption.SHA256Encryptor;
import com.comicszone.dao.util.encryption.SHA256SimpleSaltedEncryptor;
import com.comicszone.entity.Users;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@ViewScoped
public class PasswordRestorationController implements Serializable{
    @EJB
    private UserDataFacade userDataFacade;
    private String uid;
    private String message;
    private Users user;
    private long HOUR=60*60*1000;
    

    public void init()
    {
        if (uid==null || uid.equals(""))
        {
            redirect();
        }
        IPasswordEncryptor encryptor = new SHA256Encryptor();
        String encryptedUid = encryptor.getEncodedPassword(uid);
        user=userDataFacade.findUserByUid(encryptedUid);
        if (user==null)
        {
            redirect();
        }
        else
        {
            Date currentData=new Date();
            Date uidTime=user.getRecoveryPasswordTime();
            long delta=currentData.getTime()-uidTime.getTime();
            if (delta>HOUR)
            {
                redirect();
            }
        }
    }
    public void newPassword() throws IOException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = externalContext.getRequestParameterMap();
        String password=parameterMap.get("newPasswordForm:password");
        String confirmPassword=parameterMap.get("newPasswordForm:confirmPassword");
        message=userDataFacade.recoveryPassword(user,password, confirmPassword);
        if (message.equals("OK"))
        {
            externalContext.redirect("/");
        }
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
//        init();
    }

    public String getMessage() {
        return message;
    }
    private void redirect()
    {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {        
            context.redirect("/resources/templates/unauthorized/recover_password.jsf?check=true");
        } catch (IOException ex) {
            Logger.getLogger(PasswordRestorationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
