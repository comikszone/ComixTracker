/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.readingPage;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.ReadingFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.ContentType;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.entitynetbeans.Volume;
import com.comicszone.managedbeans.entitycontroller.ComicsController;
import com.comicszone.managedbeans.userbeans.CurrentUserManagedBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleSelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author GuronPavorro
 */
@ManagedBean
@ViewScoped
public class ReadingPageManagedBean implements Serializable {
    
    @EJB
    private ComicsFacade comicsFacade;
    
    @ManagedProperty(value="#{comicsController}")
    private ComicsController comicsController;
    
    @EJB
    private ReadingFacade readingFacade;
    
    @ManagedProperty(value="#{currentUserManagedBean}")
    private CurrentUserManagedBean userManagedBean;
    
//    private Volume selectedVolume;
//    private List<Volume> selectedVolumes;
    private Issue selectedIssue;
    private List<Issue> selectedIssues;
    private List<Issue> prevSelectedIssues;

    public ComicsFacade getComicsFacade() {
        return comicsFacade;
    }
    
    public void setComicsFacade(ComicsFacade comicsFacade) {
        this.comicsFacade = comicsFacade;
    }
    
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
    
    public CurrentUserManagedBean getUserManagedBean() {
        return userManagedBean;
    }
    
    public void setUserManagedBean(CurrentUserManagedBean userManagedBean) {
        this.userManagedBean = userManagedBean;
    }
    
        /**
     * @return the selectedIssue
     */
    public Issue getSelectedIssue() {
        return selectedIssue;
    }

    /**
     * @param selectedIssue the selectedIssue to set
     */
    public void setSelectedIssue(Issue selectedIssue) {
        this.selectedIssue = selectedIssue;
    }
    
    public List<Issue> getSelectedIssues() {
        return selectedIssues;
    }
 
    public void setSelectedIssues(List<Issue> selectedIssues) {
        prevSelectedIssues = this.selectedIssues;
        this.selectedIssues = selectedIssues;
    }
    
    public ReadingFacade getReadingFacade() {
        return readingFacade;
    }
    
    public void setReadingFacade(ReadingFacade readingFacade) {
        this.readingFacade = readingFacade;
    }
    
    public void init() {
        try {
            Integer comicsId = comicsController.getComics().getId();
            Integer userId = userManagedBean.getCurrentUser().getUserId();
            List<Issue> prevIssues = readingFacade.getIssueFacade().findMarkedByUserAndComics(comicsId, userId);
            if (!prevIssues.isEmpty())
                selectedIssues = prevIssues;
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onHeaderCheckboxClick(ToggleSelectEvent event) {
        if (!selectedIssues.isEmpty()) {
            try {
                Users currentUser = userManagedBean.getCurrentUser();
                readingFacade.markAsRead(currentUser, selectedIssues);
            }
            catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void selectAll() {
        setSelectedIssues(readingFacade.getIssueFacade().findByComics(comicsController.getComics().getId()));
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            readingFacade.markAsRead(currentUser, selectedIssues);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            readingFacade.markAsRead(currentUser, selectedIssues);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRowUnselect(UnselectEvent event) {
        prevSelectedIssues.removeAll(selectedIssues);
        Issue issueToUnmark = prevSelectedIssues.get(0);
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            readingFacade.unMark(currentUser, issueToUnmark);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
        
    public String redirect(Content content)
    {
        if (content.getContentType() == ContentType.Issue)
            return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + content.getId();
        else if (content.getContentType() == ContentType.Volume)
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + content.getId();
        else return "/resources/templates/index.jsf";
    }

}
