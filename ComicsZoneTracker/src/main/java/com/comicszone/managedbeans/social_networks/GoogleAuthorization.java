/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.entity.Users;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@SessionScoped
public class GoogleAuthorization extends SocialNetworkAuthorization implements Serializable {
//    private static final String clientId = "232041634310-t8k3nf1cbede85kbu8ljsc16j2fdfbvf.apps.googleusercontent.com";
//    private static final String clientSecret = "KYTJxAdhHkJ1ccx7uy9HIgKn";
//    private static final String redirectUri = "http://www.comicszonetracker.tk/resources/templates/unauthorized/redirect_page.jsf";
    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
//    private static final String userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private GoogleAuthorizationCodeFlow flow;

//    private String userUrl;
//    private String authCode;

    public GoogleAuthorization() {
        clientId = "232041634310-t8k3nf1cbede85kbu8ljsc16j2fdfbvf.apps.googleusercontent.com";
        clientSecret = "KYTJxAdhHkJ1ccx7uy9HIgKn";
        redirectUri = "http://www.comicszonetracker.tk/resources/templates/unauthorized/redirect_page.jsf";
        userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
        isError=false;
    }

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
    @Override
    public void buildUserUrl() {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientId,
                clientSecret, SCOPE).build();
        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        userUrl = url.setRedirectUri(redirectUri).build();
    }

//    @Override
//    public String createUserUrl() {
//        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientId,
//                clientSecret, SCOPE).build();
//        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
//        return url.setRedirectUri(redirectUri).build();
//    }
    

    @Override
    public String fetchPersonalInfo() throws IOException {

        if (authCode!=null)
        {
//            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
//            context.redirect("/");
//        }
            final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(redirectUri).execute();
            final Credential credential = flow.createAndStoreCredential(response, null);
            final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
            // Make an authenticated request
            final GenericUrl url = new GenericUrl(userInfoUrl);
            final HttpRequest request = requestFactory.buildGetRequest(url);
            request.getHeaders().setContentType("application/json");
            final String jsonIdentity = request.execute().parseAsString();
            return jsonIdentity;
        }
        return "";
    }

    @Override
    public Users createUser() throws IOException, ParseException {
        String startJson = fetchPersonalInfo();
        if (!startJson.equals(""))
        {
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(startJson);
            String id = getJsonValue(json, "id");
            String nickname = "Google" + id;
            String name = getJsonValue(json, "name");
            String photo = getJsonValue(json, "picture");
            String email = getJsonValue(json, "email");
            Users user = new Users();
            user.setEmail(email);
            user.setNickname(nickname);
            user.setName(name);
            user.setAvatarUrl(photo); 
            return user;
        }
        return null;
    }
}
