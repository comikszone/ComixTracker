/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import com.comicszone.dao.userdao.UserRegistrationDao;
import com.comicszone.entitynetbeans.Users;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@SessionScoped
public class GoogleAuthorization extends SocialNetworkAuthorization{
    @EJB
    private UserRegistrationDao userRegistrationDao;
    private static final String CLIENT_ID = "232041634310-t8k3nf1cbede85kbu8ljsc16j2fdfbvf.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "KYTJxAdhHkJ1ccx7uy9HIgKn";
    private static final String CALLBACK_URI = "http://localhost:8080/ComicsZoneTracker/resources/templates/unauthorized/redirect_page.jsf";
    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private  GoogleAuthorizationCodeFlow flow;

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
        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,JSON_FACTORY, CLIENT_ID,
                CLIENT_SECRET, SCOPE).build();
        final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        userUrl= url.setRedirectUri(CALLBACK_URI).build();
    }
    public String fetchPersonalInfo() throws IOException{

//            logger.info(authCode);
//            logger.info(flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI));
//            flow.
		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
//		logger.info("response.getAccessToken()"+response.getAccessToken());
                final Credential credential = flow.createAndStoreCredential(response, null);
//                setCredential(credential);
//                credential
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
//                logger.info("genericUrl="+url.);
		final HttpRequest request = requestFactory.buildGetRequest(url);
//                HttpRequset requset=new HttpRequest();
//                logger.info(request.getContent().getEncoding());
                request.setUrl(new GenericUrl(USER_INFO_URL+"?access_token0="+credential.getAccessToken()));
//                logger.info("request.getUrl().build()="+request.getUrl().build());
		request.getHeaders().setContentType("application/json");
//                logger.info("parameters="+request.getContent().getEncoding());
		final String jsonIdentity = request.execute().parseAsString();
//                                logger.info("!!!"+credential.getAccessToken()+"___AAAAAAA_____"+credential.getRefreshToken());
		return jsonIdentity;

	}

    @Override
    public Users createUser() throws IOException, ParseException {
        String startJson=fetchPersonalInfo();
        JSONParser jsonParser=new JSONParser();
        JSONObject json = (JSONObject) jsonParser.parse(startJson);
        String id=getJsonValue(json, "id");
//        return id;
        String nickname="Google"+id;
        String name=getJsonValue(json, "name");
        String photo=getJsonValue(json, "picture");
        String email=getJsonValue(json,"email");
        Users user=new Users();
        user.setEmail(email);
        user.setNickname(nickname);
        return user;
    }

    @Override
    public void doRegistration() throws IOException, ParseException {
        Users user=createUser();
        userRegistrationDao.registration(user, PASSWORD);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ComicsZoneTracker/resources/templates/unauthorized/unauthorized.jsf");
    }
}
