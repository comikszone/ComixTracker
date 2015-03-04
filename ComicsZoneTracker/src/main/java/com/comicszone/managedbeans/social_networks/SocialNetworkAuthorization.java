/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.dao.user.UserRegistrationFacade;
import com.comicszone.entity.Users;
import com.comicszone.managedbeans.userbeans.AuthorisationController;
import com.comicszone.managedbeans.userbeans.CurrentUserController;
import com.comicszone.managedbeans.util.passwordcreators.IPasswordCreator;
import com.comicszone.managedbeans.util.passwordcreators.SimplePasswordCreator;
import com.comicszone.managedbeans.util.passwordcreators.UserAuthentification;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
public abstract class SocialNetworkAuthorization implements Serializable {

    @EJB
    private UserRegistrationFacade userRegistrationDao;
    protected String clientId;
    protected String clientSecret;
    protected String userUrl;
    protected String authCode;
    protected String redirectUri;
    protected String userInfoUrl;
    protected boolean isError;
    private final int PASSWORD_LENGTH = 20;
    
    public abstract String fetchPersonalInfo() throws IOException, ParseException;

    public abstract Users createUser() throws IOException, ParseException;
//    public abstract String createUserUrl();
    public abstract void buildUserUrl();

    public void doRegistration(AuthorisationController mb) throws IOException, ParseException, ServletException {
        IPasswordCreator passwordCreator = new SimplePasswordCreator();
        String password = passwordCreator.createPassword(PASSWORD_LENGTH);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();        
        Users user = createUser();
        if (user !=null)
        {
                userRegistrationDao.socialNetworkRegistration(user, password);

    //        AuthorisationController mb = ((AuthorisationController) FacesContext
    //                .getCurrentInstance()
    //                .getViewRoot()
    //                .getViewMap()
    ////                .getExternalContext()
    ////                .getSessionMap()
    //                .get("authorisationManagedBean"));
            mb.setNickname(user.getNickname());
            mb.setPassword(password);
            mb.loginWithoutBan();
        }
        else
        {
            context.redirect("/");
        }
    }
    public String getJsonValue(String json,String parameter) throws ParseException
    {
        JSONParser jsonParser=new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        Object obj = jsonObject.get(parameter);
        if (obj == null) {
            return null;
        }
        String result = obj.toString();
        return result;
    }

    public String getJsonValue(JSONObject jsonObject, String parameter) throws ParseException {
        Object obj = jsonObject.get(parameter);
        if (obj == null) {
            return null;
        }
        String json = obj.toString();
        return json;
    }

}
