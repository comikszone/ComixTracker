package com.comicszone.managedbeans.registration;

import com.comicszone.dao.userdao.UserRegistrationDao;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class RegistrationManagedBean {

    @EJB
    UserRegistrationDao registrationDao;
    
    public String registrationNewUser(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        Map<String, String> parameterMap = externalContext.getRequestParameterMap();
        
        registrationDao.registration(
                parameterMap.get("registrationForm:nickname"),
                parameterMap.get("registrationForm:email"),
                parameterMap.get("registrationForm:password"), 
                parameterMap.get("registrationForm:confirmPassword"));
        
        return "/resources/templates/index.jsf";
    }
}
