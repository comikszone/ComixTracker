/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.util.sourceswitch;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Phoenix1092
 */

@Singleton
@ApplicationScoped
@ManagedBean
public class SourceSwitch {
    
    private String source;
    
    @PostConstruct
    public void init() {
        setSource("Wikia");
    }
    
    public String getSource() {
        return this.source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public void onChange() {
        String msg = "Current data source is "+source;
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, new FacesMessage(msg));
        System.out.println(msg);
    }
}
