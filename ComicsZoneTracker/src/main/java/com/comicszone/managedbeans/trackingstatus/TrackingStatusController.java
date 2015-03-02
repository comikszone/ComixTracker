/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.trackingstatus;

import com.comicszone.dao.trackingstatus.TrackingStatusFacade;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Users;
import java.security.Principal;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author GuronPavorro
 */
@ManagedBean
@ViewScoped
public class TrackingStatusController {
        
    @EJB
    private TrackingStatusFacade statusFacade;
    
    private String selectedStatus;
    
    private Integer comicsId;
    
    @EJB
    private UserDataFacade userFacade;
    
    private Users currentUser;
    
    public TrackingStatusController() {
        
    }
    
    public void init() {
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        if (prin != null)
            setCurrentUser(getUserFacade().getUserWithNickname(prin.getName()));
        statusFacade.init(currentUser.getUserId(), comicsId);
        selectedStatus = statusFacade.getStatus().toString();
    }

    /**
     * @return the statusFacade
     */
    public TrackingStatusFacade getStatusFacade() {
        return statusFacade;
    }

    /**
     * @param statusFacade the statusFacade to set
     */
    public void setStatusFacade(TrackingStatusFacade statusFacade) {
        this.statusFacade = statusFacade;
    }

    /**
     * @return the selectedStatus
     */
    public String getSelectedStatus() {
        return selectedStatus;
    }

    /**
     * @param selectedStatus the selectedStatus to set
     */
    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
        statusFacade.updateStatus(Integer.parseInt(selectedStatus));
    }

    /**
     * @return the userFacade
     */
    public UserDataFacade getUserFacade() {
        return userFacade;
    }

    /**
     * @param userFacade the userFacade to set
     */
    public void setUserFacade(UserDataFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * @return the currentUser
     */
    public Users getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the comicsId
     */
    public Integer getComicsId() {
        return comicsId;
    }

    /**
     * @param comicsId the comicsId to set
     */
    public void setComicsId(Integer comicsId) {
        this.comicsId = comicsId;
    }
    
}
