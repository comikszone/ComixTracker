/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

/**
 *
 * @author ArsenyPC
 */
import com.comicszone.entitynetbeans.Users;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
public class FacebookAuthorization extends SocialNetworkAuthorization implements Serializable {
    private static final String CLIENT_ID = "361365270701323";
    private static final String CLIENT_SECRET = "0b2cf81509ba71c7df172ab46fa49a57";
    private static final String CALLBACK_URI = "http://localhost:8080/resources/templates/unauthorized/facebook_redirect_page.jsf";
    private static final String FACEBOOK_URL = "https://www.facebook.com/dialog/oauth";
    private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
    private static final String PERSONAL_INFO_URL = "https://graph.facebook.com/me";
    private String userUrl;
    private String authCode;

    public String getUserUrl() {
        return userUrl;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @PostConstruct
    public void buildUserUrl() {
        String url = FACEBOOK_URL + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + CALLBACK_URI
                + "&response_type=code";
        userUrl = url;
    }

    @Override
    public String fetchPersonalInfo() throws IOException, ParseException {
        String urlAccessToken = ACCESS_TOKEN_URL
                + "?client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + authCode
                + "&redirect_uri=" + CALLBACK_URI;
        String response = getResponseString(urlAccessToken);
        try
        {
            if (getJsonValue(response, "error")!=null)
            {
                ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
                context.redirect("/");
            }
        }
        catch (ParseException e)
        {}
        String accessToken = getAccessToken(response);
//        String userId=getAccessToken();
        String urlUserInfo = PERSONAL_INFO_URL
                + "?access_token=" + accessToken;
        String json = getResponseString(urlUserInfo);
        String personalFacebookId = getJsonValue(json, "id");
        String pictureUrl = "https://graph.facebook.com/" + personalFacebookId + "/picture?return_ssl_resources=true";
        json = addPictureUrl(json, pictureUrl);
        return json;
    }

    private String getAccessToken(String response) {
        int first = response.indexOf("=") + 1;
        int last = response.indexOf("&expires");
        return response.substring(first, last);
    }

    private String getResponseString(String url) throws IOException {
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

//    private String getPersonalFacebookId(String json, String parameter) {
//        JSONParser jsonParser = new JSONParser();
//        try {
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
//            String jsonId = jsonObject.get(parameter).toString();
////            String jsonComponent=array.get(index).toString();
//            return jsonId;
//        } catch (ParseException ex) {
//            Logger.getLogger(VKAuthorization.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return "";
//    }

    private String addPictureUrl(String json, String pictureUrl) {
        JSONParser jsonParser = new JSONParser();
        String result = "";
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            jsonObject.put("picture", pictureUrl);
            result = jsonObject.toJSONString();
//            String jsonComponent=array.get(index).toString();
        } catch (ParseException ex) {
            Logger.getLogger(VKAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public Users createUser() throws IOException, ParseException {
        String startJson = fetchPersonalInfo();
        JSONParser jsonParser = new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(startJson);
//        String error=getJsonValue(json, "error");
        String id = getJsonValue(json, "id");
//        return id;
        String nickname = "Facebook" + id;
        String name = getJsonValue(json, "name");
        String photo = getJsonValue(json, "picture");
        Users user = new Users();
        user.setAvatarUrl(photo);
        user.setName(name);
        user.setNickname(nickname);
        user.setEmail("default email");
        return user;
    }

}
