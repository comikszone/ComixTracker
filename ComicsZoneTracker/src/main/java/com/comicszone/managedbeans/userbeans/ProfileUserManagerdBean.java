package com.comicszone.managedbeans.userbeans;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class ProfileUserManagerdBean {
    
    @PostConstruct
    public void loadCurrentUserInfo(){
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();
    }
}
