/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.progressPage;

import com.comicszone.dao.ProgressFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.managedbeans.userbeans.CurrentUserManagedBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author GuronPavorro
 */

@ManagedBean
@ViewScoped
public class ProgressPageManagedBean implements Serializable {
    @EJB
    private ProgressFacade progressFacade;
    @ManagedProperty(value="#{currentUserManagedBean}")
    private CurrentUserManagedBean userManagedBean;
    
    private List<Comics> usersComics;
    

    /**
     * @return the progressFacade
     */
    public ProgressFacade getProgressFacade() {
        return progressFacade;
    }

    /**
     * @param progressFacade the progressFacade to set
     */
    public void setProgressFacade(ProgressFacade progressFacade) {
        this.progressFacade = progressFacade;
    }

    /**
     * @return the userManagedBean
     */
    public CurrentUserManagedBean getUserManagedBean() {
        return userManagedBean;
    }

    /**
     * @param userManagedBean the userManagedBean to set
     */
    public void setUserManagedBean(CurrentUserManagedBean userManagedBean) {
        this.userManagedBean = userManagedBean;
    }
    
    @PostConstruct
    public void init() {
        try {
            Integer userId = userManagedBean.getCurrentUser().getUserId();
            setUsersComics(progressFacade.findByUserInProgress(userId));
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public Long getValue(Comics comics) throws CloneNotSupportedException {
        double markedCount = progressFacade.getMarkedIssueCount(comics.getId(), userManagedBean.getCurrentUser().getUserId());
        double totalCount = progressFacade.getTotalIssueCount(comics.getId());
        double res = (markedCount/totalCount)*100;
        return ((long)(res));
    }

    /**
     * @return the usersComics
     */
    public List<Comics> getUsersComics() {
        return usersComics;
    }

    /**
     * @param usersComics the usersComics to set
     */
    public void setUsersComics(List<Comics> usersComics) {
        this.usersComics = usersComics;
    }
    
    public String redirect(Content content) {
        return "/resources/pages/comicsPage.jsf?faces-redirect=true&id=" + content.getId();
    }
    
}
