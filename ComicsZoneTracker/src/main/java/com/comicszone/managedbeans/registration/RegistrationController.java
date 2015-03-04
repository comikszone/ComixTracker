package com.comicszone.managedbeans.registration;

import com.comicszone.dao.user.UserDataFacade;
import java.io.Serializable;
import com.comicszone.dao.user.UserRegistrationFacade;
import com.comicszone.managedbeans.userbeans.AuthorisationController;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RegistrationController implements Serializable {

    @EJB
    UserRegistrationFacade registrationDao;

    public void registrationNewUser() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = externalContext.getRequestParameterMap();

        if (parameterMap.get("registrationForm:nickname").trim().length() == 0
                || parameterMap.get("registrationForm:email").trim().length() == 0
                || parameterMap.get("registrationForm:password").trim().length() == 0) {
            facesContext.addMessage(null, new FacesMessage("Error: ", "Some fields are empty!"));
            return;
        }

        if (!parameterMap.get("registrationForm:password").equals(parameterMap.get("registrationForm:confirmPassword"))) {
            facesContext.addMessage(null, new FacesMessage("Error: ", "Password isn't equals confirmPassword"));
            return;
        }

        if (registrationDao.getUserWithNickname(parameterMap.get("registrationForm:nickname")) != null) {
            facesContext.addMessage(null, new FacesMessage("Error: ", "User with this nickname already exist!"));
            return;
        }

        if (!isValidEmail(parameterMap.get("registrationForm:email"))) {
            facesContext.addMessage(null, new FacesMessage("Error: ", "Email isn't correct!"));
            return;
        }

        try {
            registrationDao.registration(
                    parameterMap.get("registrationForm:nickname"),
                    parameterMap.get("registrationForm:email"),
                    parameterMap.get("registrationForm:password"),
                    parameterMap.get("registrationForm:confirmPassword"));
        } catch (EJBException ex) {
            facesContext.addMessage(null, new FacesMessage("Error: ", "Can't create user!"));
            return;
        }

        facesContext.addMessage(null, new FacesMessage("Error: ", "User has been created!"));

        AuthorisationController amb = new AuthorisationController();
        amb.setNickname(parameterMap.get("registrationForm:nickname"));
        amb.setPassword(parameterMap.get("registrationForm:password"));
        amb.loginWithoutBan();
    }

    private boolean isValidEmail(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
