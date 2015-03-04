/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.tracking;

import com.comicszone.dao.tracking.TrackingInterface;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.Issue;
import com.comicszone.entity.NamedImage;
import com.comicszone.entity.Users;
import com.comicszone.entity.Volume;
import com.comicszone.managedbeans.entitycontroller.ComicsController;
import com.comicszone.managedbeans.userbeans.CurrentUserController;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author GuronPavorro
 */
@ManagedBean
@ViewScoped
public class TrackingController implements Serializable {
    
    @ManagedProperty(value="#{comicsController}")
    private ComicsController comicsController;
    
    @EJB
    private TrackingInterface trackingFacade;
    
    @EJB
    private UserDataFacade userFacade;
        
    private Volume selectedVolume;
    private List<Issue> selectedIssues;
    private List<Issue> prevSelectedIssues;
    
    private Users currentUser;

     /**
     * @return the comicsController
     */
    public ComicsController getComicsController() {
        return comicsController;
    }

    /**
     * @param comicsController the comicsController to set
     */
    public void setComicsController(ComicsController comicsController) {
        this.comicsController = comicsController;
    }
    
        /**
     * @return the selectedVolume
     */
    public Volume getSelectedVolume() {
        return selectedVolume;
    }

    /**
     * @param selectedVolume the selectedIssue to set
     */
    public void setSelectedVolume(Volume selectedVolume) {
        this.selectedVolume = selectedVolume;
    }
    
    public List<Issue> getSelectedIssues() {
        return selectedIssues;
    }
 
    public void setSelectedIssues(List<Issue> selectedIssues) {
        prevSelectedIssues = this.selectedIssues;
        this.selectedIssues = selectedIssues;
    }
    
    public TrackingInterface getReadingFacade() {
        return trackingFacade;
    }
    
    public void setReadingFacade(TrackingInterface trackingFacade) {
        this.trackingFacade = trackingFacade;
    }
    
    public void init() {
            Integer comicsId = comicsController.getComics().getId();
            Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
            if (prin != null) {
                setCurrentUser(getUserFacade().getUserWithNickname(prin.getName()));
                Integer userId = getCurrentUser().getUserId();
                List<Issue> prevIssues = trackingFacade.getIssueFacade().findMarkedByUserAndComics(comicsId, userId);
                selectedIssues = prevIssues;
                prevSelectedIssues = prevIssues;
            }
    }
    
    public void onHeaderCheckboxClick(ToggleSelectEvent event) {
        if (!selectedIssues.isEmpty()) {
                List<Issue> temp = selectedIssues;
                temp.removeAll(prevSelectedIssues);
                for (Issue issue: temp)
                    trackingFacade.markAsRead(currentUser, issue);
        }
        else {
                for (Issue issue: (List<Issue>)((DataTable) event.getSource()).getValue())
                    trackingFacade.unMark(currentUser, issue);
            }
    }
    
    public void selectAll() {
        setSelectedIssues(trackingFacade.getIssueFacade().findByComics(comicsController.getComics().getId()));
        List<Issue> temp = selectedIssues;
        if (prevSelectedIssues != null)
            temp.removeAll(prevSelectedIssues);
        for (Issue issue: temp)
            trackingFacade.markAsRead(currentUser, issue);
    }
    
    public void onRowSelect(SelectEvent event) {
        Issue issueToMark = (Issue)event.getObject();
        trackingFacade.markAsRead(currentUser, issueToMark);
    }
    
    public void onRowUnselect(UnselectEvent event) {
        Issue issueToUnmark = (Issue)event.getObject();
        trackingFacade.unMark(currentUser, issueToUnmark);
    }
    
    public String redirect(NamedImage content)
    {
        if (content.getContentType() == ContentType.Comics)
            return "/resources/templates/authorized/progressPage.jsf?faces-redirect=true";
        else return "/resources/templates/index.jsf";
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

}
