/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.dao.userdao.UserRegistrationDao;
import com.comicszone.entitynetbeans.Users;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
public class VKAuthorization extends SocialNetworkAuthorization {

    @EJB
    private UserRegistrationDao userRegistrationDao;
    private static final String CLIENT_ID = "4695923";
    private static final String CLIENT_SECRET = "DN8uqaag7oUAPSfYCe2n";
    private static final String CALLBACK_URI = "http://188.166.44.178/ComicsZoneTracker/resources/templates/unauthorized/vk_redirect_page.jsf";
    private static final String VK_URL = "https://oauth.vk.com/authorize";
    private static final String ACCESS_TOKEN_URL = "https://oauth.vk.com/access_token";
    private static final String PERSONAL_INFO_URL = "https://api.vk.com/method/users.get";
    private String userUrl;
    private String authCode;

    @Override
    public String getUserUrl() {
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
    public void buildUserUrl() {
        String url = VK_URL + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + CALLBACK_URI
                + "&response_type=code";
        userUrl = url;
    }

    @Override
    public String fetchPersonalInfo() throws IOException {
        String urlAccessToken = ACCESS_TOKEN_URL
                + "?client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + authCode
                + "&redirect_uri=" + CALLBACK_URI;
        String json = getResponseJson(urlAccessToken);
        try {
            if (getJsonValue(json,"error")!=null)
            {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
                context.redirect("/ComicsZoneTracker");
            }
        } catch (ParseException ex) {
            Logger.getLogger(VKAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        String accessToken = parseJson(json, "access_token");
        String userId = parseJson(json, "user_id");
        String urlUserInfo = PERSONAL_INFO_URL
                + "?uids=" + userId
                + "&fields=uid,first_name,last_name,nickname,screen_name,sex,bdate,city,country,timezone,photo"
                + "&access_token=" + accessToken;
        json = getResponseJson(urlUserInfo);
        return json;
    }

    private String parseJson(String json, String parameter) {
        JSONParser jsonParser = new JSONParser();
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

    private String getResponseJson(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        ClientParamsStack httpParams = (ClientParamsStack) response.getParams();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    @Override
    public Users createUser() throws IOException, ParseException {
        String startJson = fetchPersonalInfo();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(startJson);
        JSONArray jsonArray = (JSONArray) jsonObject.get("response");
        JSONObject json = (JSONObject) jsonArray.get(0);
        String id = getJsonValue(json, "uid");
//        return id;
        String nickname = "VK" + id;
        String firstName = getJsonValue(json, "first_name");
        String lastName = getJsonValue(json, "last_name");
        String name = firstName + " " + lastName;
        String photo = getJsonValue(json, "photo");
        String bDate = getJsonValue(json, "bdate");
        Users user = new Users();
        user.setNickname(nickname);
        user.setEmail("default email");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            user.setBirthday(sdf.parse(bDate));
        } catch (java.text.ParseException ex) {
            Logger.getLogger(VKAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(VKAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}
