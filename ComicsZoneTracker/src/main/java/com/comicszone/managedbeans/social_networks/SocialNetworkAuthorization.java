/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.dao.userdao.UserRegistrationDao;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.managedbeans.util.passwordcreators.IPasswordCreator;
import com.comicszone.managedbeans.util.passwordcreators.SimplePasswordCreator;
import java.io.IOException;
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
public abstract class SocialNetworkAuthorization {

    @EJB
    private UserRegistrationDao userRegistrationDao;

    private final int PASSWORD_LENGTH = 20;
    
    public abstract String getUserUrl();

    public abstract String getAuthCode();

    public abstract void setAuthCode(String authCode);

    public abstract void buildUserUrl();

    public abstract String fetchPersonalInfo() throws IOException;

    public abstract Users createUser() throws IOException, ParseException;

    public void doRegistration() throws IOException, ParseException, ServletException {
        IPasswordCreator passwordCreator = new SimplePasswordCreator();
        String password = passwordCreator.createPassword(PASSWORD_LENGTH);
        
        Users user = createUser();
        userRegistrationDao.socialNetworkRegistration(user, password);

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        
        context.redirect("j_security_check?j_username="
                + user.getNickname()
                + "&j_password="
                + password);
    }
//    public String getJsonValue(String json,String parameter) throws ParseException
//    {
//        JSONParser jsonParser=new JSONParser();
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
//        String jsonId = jsonObject.get(parameter).toString();
//        return jsonId;
//    }

    public String getJsonValue(JSONObject jsonObject, String parameter) throws ParseException {
        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        Object obj = jsonObject.get(parameter);
        if (obj == null) {
            return null;
        }
        String json = obj.toString();
        return json;
    }

}
