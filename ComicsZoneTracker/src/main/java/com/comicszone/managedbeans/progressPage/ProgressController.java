/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.progressPage;

import com.comicszone.dao.progress.ProgressInterface;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Content;
import com.comicszone.managedbeans.userbeans.CurrentUserController;
import java.io.Serializable;
import java.util.List;
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
public class ProgressController implements Serializable {
    @EJB(beanName="progressFacade")
    private ProgressInterface progressFacade;
    @ManagedProperty(value="#{currentUserManagedBean}")
    private CurrentUserController userManagedBean;
    
    private List<Comics> usersComics;
    

    /**
     * @return the progressFacade
     */
    public ProgressInterface getProgressFacade() {
        return progressFacade;
    }

    /**
     * @param progressFacade the progressFacade to set
     */
    public void setProgressFacade(ProgressInterface progressFacade) {
        this.progressFacade = progressFacade;
    }

    /**
     * @return the userManagedBean
     */
    public CurrentUserController getUserManagedBean() {
        return userManagedBean;
    }

    /**
     * @param userManagedBean the userManagedBean to set
     */
    public void setUserManagedBean(CurrentUserController userManagedBean) {
        this.userManagedBean = userManagedBean;
    }
    
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
        return "/resources/templates/authorized/readingPage.jsf?faces-redirect=true&id=" + content.getId();
    }
    
}