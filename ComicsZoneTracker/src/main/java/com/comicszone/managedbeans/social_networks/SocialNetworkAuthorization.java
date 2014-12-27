/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import java.io.IOException;
import javax.annotation.PostConstruct;

/**
 *
 * @author ArsenyPC
 */
public interface SocialNetworkAuthorization {
    public String getUserUrl();
    public String getAuthCode();
    public void setAuthCode(String authCode);
    public void buildUserUrl();
    public String fetchPersonalInfo() throws IOException;
}
