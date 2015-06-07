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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.ViewScoped;

/**
 *
 * @author GuronPavorro
 */

@Named
@ViewScoped
public class ProgressController implements Serializable {
    @EJB
    private ProgressInterface progressFacade;
    @Inject
    private CurrentUserController userManagedBean;
    
    private List<Comics> currentComics;
    private List<Comics> droppedComics;
    private List<Comics> plannedComics;
    

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
    
    @PostConstruct
    public void init() {
        try {
            Integer userId = userManagedBean.getCurrentUser().getUserId();
            currentComics = progressFacade.findCurrentComics(userId);
            plannedComics = progressFacade.findPlannedComics(userId);
            droppedComics = progressFacade.findDroppedComics(userId);
        }
        catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
    
    public Double getValue(Comics comics) throws CloneNotSupportedException {
        double markedCount = progressFacade.getMarkedIssueCount(comics.getId(), userManagedBean.getCurrentUser().getUserId());
        double totalCount = progressFacade.getTotalIssueCount(comics.getId());
        double res = (markedCount/totalCount)*100;
        return res;
    }
    
    public Long getMarkedIssueCount(Comics comics) throws CloneNotSupportedException {
        return progressFacade.getMarkedIssueCount(comics.getId(), userManagedBean.getCurrentUser().getUserId());
    }
    
    public Long getTotalIssueCount(Comics comics) {
        return progressFacade.getTotalIssueCount(comics.getId());
    }
 
    public String redirect(Content content) {
        return "/resources/pages/comicsPage.jsf?faces-redirect=true&id=" + content.getId() + "&tracking=true";
    }

    /**
     * @return the currentComics
     */
    public List<Comics> getCurrentComics() {
        return currentComics;
    }

    /**
     * @param currentComics the currentComics to set
     */
    public void setCurrentComics(List<Comics> currentComics) {
        this.currentComics = currentComics;
    }

    /**
     * @return the droppedComics
     */
    public List<Comics> getDroppedComics() {
        return droppedComics;
    }

    /**
     * @param droppedComics the droppedComics to set
     */
    public void setDroppedComics(List<Comics> droppedComics) {
        this.droppedComics = droppedComics;
    }

    /**
     * @return the plannedComics
     */
    public List<Comics> getPlannedComics() {
        return plannedComics;
    }

    /**
     * @param plannedComics the plannedComics to set
     */
    public void setPlannedComics(List<Comics> plannedComics) {
        this.plannedComics = plannedComics;
    }
    
}
