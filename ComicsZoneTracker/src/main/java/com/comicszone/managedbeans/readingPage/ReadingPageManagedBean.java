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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;

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
    
//    public Volume getSelectedVolume() {
//        return selectedVolume;
//    }
//
//    public void setSelectedVolume(Volume selectedVolume) {
//        this.selectedVolume = selectedVolume;
//    }
    
    public Issue getSelectedIssue() {
        return selectedIssue;
    }

    public void setSelectedIssue(Issue selectedIssue) {
        this.selectedIssue = selectedIssue;
    }
    
//    public List<Volume> getSelectedVolumes() {
//        return selectedVolumes;
//    }
// 
//    public void setSelectedVolumes(List<Volume> selectedVolumes) {
//        this.selectedVolumes = selectedVolumes;
//    }
    
    public List<Issue> getSelectedIssues() {
        return selectedIssues;
    }
 
    public void setSelectedIssues(List<Issue> selectedIssues) {
        this.selectedIssues = selectedIssues;
    }
    
    public ReadingFacade getReadingFacade() {
        return readingFacade;
    }
    
    public void setReadingFacade(ReadingFacade readingFacade) {
        this.readingFacade = readingFacade;
    }
    
    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Issue Selected",
                ((Issue) event.getObject()).getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void markAsRead(ActionEvent actionEvent) {
        try {
            Users currentUser = userManagedBean.getCurrentUser();
            readingFacade.markAsRead(currentUser, selectedIssues);
        }
        catch (CloneNotSupportedException ex) {
            System.err.println("Same user!");
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
