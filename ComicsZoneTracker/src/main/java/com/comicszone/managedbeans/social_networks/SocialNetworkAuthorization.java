/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.dao.userdao.UserRegistrationDao;
import com.comicszone.entitynetbeans.Users;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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

    protected String PASSWORD = "402881834a5bbhaon14a5bb0188f0001";

    public abstract String getUserUrl();

    public abstract String getAuthCode();

    public abstract void setAuthCode(String authCode);

    public abstract void buildUserUrl();

    public abstract String fetchPersonalInfo() throws IOException;

    public abstract Users createUser() throws IOException, ParseException;

    public void doRegistration() throws IOException, ParseException {
        Users user = createUser();
        userRegistrationDao.registration(user, PASSWORD);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ComicsZoneTracker/resources/templates/unauthorized/unauthorized.jsf");
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
        if (obj == null)
            return null;
        String json = jsonObject.get(parameter).toString();
        return json;
    }

}
