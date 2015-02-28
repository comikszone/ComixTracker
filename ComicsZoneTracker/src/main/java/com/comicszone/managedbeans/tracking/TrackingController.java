/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.tracking;

import com.comicszone.dao.tracking.TrackingInterface;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.Issue;
import com.comicszone.entity.Users;
import com.comicszone.entity.Volume;
import com.comicszone.managedbeans.entitycontroller.ComicsController;
import com.comicszone.managedbeans.userbeans.CurrentUserController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
    
    @ManagedProperty(value="#{currentUserManagedBean}")
    private CurrentUserController userManagedBean;
    
    private Volume selectedVolume;
    private List<Issue> selectedIssues;
    private List<Issue> prevSelectedIssues;
    
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
    
    public CurrentUserController getUserManagedBean() {
        return userManagedBean;
    }
    
    public void setUserManagedBean(CurrentUserController userManagedBean) {
        this.userManagedBean = userManagedBean;
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
    
    @PostConstruct
    public void init() {
        try {
            Integer comicsId = comicsController.getComics().getId();
            Integer userId = userManagedBean.getCurrentUser().getUserId();
            List<Issue> prevIssues = trackingFacade.getIssueFacade().findMarkedByUserAndComics(comicsId, userId);
            selectedIssues = prevIssues;
            prevSelectedIssues = prevIssues;
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onHeaderCheckboxClick(ToggleSelectEvent event) {
        if (!selectedIssues.isEmpty()) {
            try {
                List<Issue> temp = selectedIssues;
                temp.removeAll(prevSelectedIssues);
                Users currentUser = userManagedBean.getCurrentUser();
                for (Issue issue: temp)
                    trackingFacade.markAsRead(currentUser, issue);
            }
            catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        else {
            try {
                Users currentUser = userManagedBean.getCurrentUser();
                for (Issue issue: (List<Issue>)((DataTable) event.getSource()).getValue())
                    trackingFacade.unMark(currentUser, issue);
            }
            catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void selectAll() {
        init();
        setSelectedIssues(trackingFacade.getIssueFacade().findByComics(comicsController.getComics().getId()));
        List<Issue> temp = selectedIssues;
        if (prevSelectedIssues != null)
            temp.removeAll(prevSelectedIssues);
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            for (Issue issue: temp)
                trackingFacade.markAsRead(currentUser, issue);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        Issue issueToMark = (Issue)event.getObject();
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            trackingFacade.markAsRead(currentUser, issueToMark);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRowUnselect(UnselectEvent event) {
        Issue issueToUnmark = (Issue)event.getObject();
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            trackingFacade.unMark(currentUser, issueToUnmark);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
        
    public String redirect(Content content)
    {
        if (content.getContentType() == ContentType.Issue)
            return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + content.getId();
        if (content.getContentType() == ContentType.Volume)
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + content.getId();
        if (content.getContentType() == ContentType.Comics)
                return "/resources/templates/authorized/progressPage.jsf?faces-redirect=true&id=" + content.getId();
        return "";
    }

}
