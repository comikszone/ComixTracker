/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author ArsenyPC
 */
@ManagedBean
public class VKAuthorization implements SocialNetworkAuthorization{
    private static final String CLIENT_ID = "4695923";
    private static final String CLIENT_SECRET = "DN8uqaag7oUAPSfYCe2n";
    private static final String CALLBACK_URI = "http://localhost:8080/ComicsZoneTracker/resources/templates/unauthorized/vk_redirect_page.jsf";
    private static final String VK_URL = "https://oauth.vk.com/authorize";
    private static final String ACCESS_TOKEN_URL="https://oauth.vk.com/access_token";
    private static final String PERSONAL_INFO_URL="https://api.vk.com/method/users.get";
    private String userUrl;
    private String authCode;
    
    @Override
    public String getUserUrl()
    {
        return userUrl;
    }

    @Override
    public String getAuthCode() {
        return authCode;
    }
    
    @Override
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    
    @PostConstruct
    @Override
    public void buildUserUrl()
    {
        String url=VK_URL+"?client_id="+CLIENT_ID +
                "&redirect_uri="+CALLBACK_URI+
                "&response_type=code";
        userUrl= url;
    }

    @Override
    public String fetchPersonalInfo() throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String urlAccessToken=ACCESS_TOKEN_URL+
                "?client_id="+CLIENT_ID+
                "&client_secret="+CLIENT_SECRET+
                "&code="+authCode+
                "&redirect_uri="+CALLBACK_URI;
        String json=getResponseJson(urlAccessToken);
        String accessToken=parseJson(json, "access_token");
        String userId=parseJson(json,"user_id");
        String urlUserInfo=PERSONAL_INFO_URL+
                "?uids="+userId+
                "&fields=uid,first_name,last_name,nickname,screen_name,sex,bdate,city,country,timezone,photo"+
                "&access_token="+accessToken;
        json=getResponseJson(urlUserInfo);
        return json;
    }
    private String parseJson(String json,String parameter)
    {
        JSONParser jsonParser=new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            String token = jsonObject.get(parameter).toString();
//            String jsonComponent=array.get(index).toString();
            return token;
        } catch (ParseException ex) {
            Logger.getLogger(VKAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    private String getResponseJson(String url) throws IOException
    {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        ClientParamsStack httpParams=(ClientParamsStack) response.getParams();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(response.getEntity().getContent(),"UTF-8"));
        StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
        return result.toString();
    }
}
