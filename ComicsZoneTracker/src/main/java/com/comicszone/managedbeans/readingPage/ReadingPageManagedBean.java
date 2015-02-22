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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
public class ReadingPageManagedBean implements Serializable {
    
    @EJB
    private ComicsFacade comicsFacade;
    
    @ManagedProperty(value="#{comicsController}")
    private ComicsController comicsController;
    
    @EJB
    private ReadingFacade readingFacade;
    
    @ManagedProperty(value="#{currentUserManagedBean}")
    private CurrentUserManagedBean userManagedBean;
    
    private Volume selectedVolume;
    private List<Volume> selectedVolumes;
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
                    readingFacade.markAsRead(currentUser, issue);
            }
            catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
        else {
            try {
                Users currentUser = userManagedBean.getCurrentUser();
                for (Issue issue: (List<Issue>)((DataTable) event.getSource()).getValue())
                    readingFacade.unMark(currentUser, issue);
            }
            catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void selectAll() {
        init();
        setSelectedIssues(readingFacade.getIssueFacade().findByComics(comicsController.getComics().getId()));
        List<Issue> temp = selectedIssues;
        if (prevSelectedIssues != null)
            temp.removeAll(prevSelectedIssues);
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            for (Issue issue: temp)
                readingFacade.markAsRead(currentUser, issue);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        Issue issueToMark = (Issue)event.getObject();
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            readingFacade.markAsRead(currentUser, issueToMark);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void onRowUnselect(UnselectEvent event) {
        Issue issueToUnmark = (Issue)event.getObject();
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
        if (content.getContentType() == ContentType.Volume)
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + content.getId();
        if (content.getContentType() == ContentType.Comics)
                return "/resources/templates/authorized/progressPage.jsf?faces-redirect=true&id=" + content.getId();
        return "";
    }

    /**
     * @return the selectedVolumes
     */
    public List<Volume> getSelectedVolumes() {
        return selectedVolumes;
    }

    /**
     * @param selectedVolumes the selectedVolumes to set
     */
    public void setSelectedVolumes(List<Volume> selectedVolumes) {
        this.selectedVolumes = selectedVolumes;
    }

}
