/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@SessionScoped
public class SocialNetwork {
    private static final String CLIENT_ID = "232041634310-t8k3nf1cbede85kbu8ljsc16j2fdfbvf.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "KYTJxAdhHkJ1ccx7uy9HIgKn";
    private static final String CALLBACK_URI = "http://localhost:8080/ComicsZoneTracker/resources/templates/unauthorized/unauthorized.jsf";
    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private  GoogleAuthorizationCodeFlow flow;

    private String userUrl;
    
    public String getUserUrl()
    {
        return userUrl;
    }
    
    @PostConstruct
    public void buildUserUrl()
    {
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY, CLIENT_ID,
                CLIENT_SECRET, SCOPE).build();
        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        userUrl= url.setRedirectUri(CALLBACK_URI).build();
    }
}
