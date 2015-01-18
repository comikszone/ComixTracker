/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.entitynetbeans.Users;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
public abstract class SocialNetworkAuthorization {
    public abstract String getUserUrl();
    public abstract String getAuthCode();
    public abstract void setAuthCode(String authCode);
    public abstract void buildUserUrl();
    public abstract String fetchPersonalInfo() throws IOException;
    public abstract Users createUser() throws IOException, ParseException;
//    public String getJsonValue(String json,String parameter) throws ParseException
//    {
//        JSONParser jsonParser=new JSONParser();
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
//        String jsonId = jsonObject.get(parameter).toString();
//        return jsonId;
//    }
    public String getJsonValue(JSONObject jsonObject,String parameter) throws ParseException
    {
        JSONParser jsonParser=new JSONParser();
//        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String json = jsonObject.get(parameter).toString();
        return json;
    }
    
}
