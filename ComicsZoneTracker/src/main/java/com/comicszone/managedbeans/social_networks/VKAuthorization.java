/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import java.io.IOException;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
public class VKAuthorization implements SocialNetworkAuthorization{
    private static final String CLIENT_ID = "4695923";
    private static final String CLIENT_SECRET = "DN8uqaag7oUAPSfYCe2n";
    private static final String CALLBACK_URI = "http://localhost:8080/ComicsZoneTracker/resources/templates/unauthorized/redirect_page.jsf";
    private static final String VK_URL = "https://oauth.vk.com/authorize"; 
    private String userUrl;
    private String authCode;
    
    public String getUserUrl()
    {
        return userUrl;
    }

    public String getAuthCode() {
        return authCode;
    }
    
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    
    @PostConstruct
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
        return "todo";
    }
}
