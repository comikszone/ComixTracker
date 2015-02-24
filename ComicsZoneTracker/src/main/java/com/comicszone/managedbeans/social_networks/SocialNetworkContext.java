/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.social_networks;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.servlet.ServletException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
public class SocialNetworkContext {
    private SocialNetworkAuthorization authorization;
    public void doRegistration() throws IOException, ParseException, ServletException
    {
        authorization.doRegistration();
    }

    public void setAuthorization(SocialNetworkAuthorization authorization) {
        this.authorization = authorization;
    }    
}
