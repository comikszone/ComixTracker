/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.rating;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.ratingdao.RatingFacade;
import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.ContentType;
import com.comicszone.entitynetbeans.Users;
import java.io.Serializable;
import java.security.Principal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import net.playerfinder.jsf.components.rating.UIRating;

/**
 *
 * @author ajpyatakov
 */

@ManagedBean
@ViewScoped
public class RatingController implements Serializable {
    @EJB
    private RatingFacade ratingFacade;
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private VolumeFacade volumeFacade;
    @EJB
    private IssueFacade issueFacade;
    @EJB
    private UserDataFacade userFacade;
    
    
    private ContentType type;
    private Users currentUser;
    private Integer contentId;
    
    public void init() {
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        if (prin != null)
            setCurrentUser(userFacade.getUserWithNickname(prin.getName()));
        ratingFacade.init(currentUser.getUserId(), contentId, type);
    }
    
    public void rate(AjaxBehaviorEvent actionEvent) {
            Content content = null;
            switch (type) {
                case Comics:
                    content = getComicsFacade().find(contentId);
                    break;
                case Volume:
                    content = getVolumeFacade().find(contentId);
                    break;
                case Issue:
                    content = getIssueFacade().find(contentId);
                    break;
            }
            Float score = (Float)((UIRating) actionEvent.getComponent()).getValue();
            getRatingFacade().rateContent(content, getCurrentUser(), score);    
    }

    /**
     * @return the ratingFacade
     */
    public RatingFacade getRatingFacade() {
        return ratingFacade;
    }

    /**
     * @param ratingFacade the ratingFacade to set
     */
    public void setRatingFacade(RatingFacade ratingFacade) {
        this.ratingFacade = ratingFacade;
    }

    /**
     * @return the type
     */
    public ContentType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ContentType type) {
        this.type = type;
    }

    /**
     * @return the contentId
     */
    public Integer getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    /**
     * @return the comicsFacade
     */
    public ComicsFacade getComicsFacade() {
        return comicsFacade;
    }

    /**
     * @param comicsFacade the comicsFacade to set
     */
    public void setComicsFacade(ComicsFacade comicsFacade) {
        this.comicsFacade = comicsFacade;
    }

    /**
     * @return the volumeFacade
     */
    public VolumeFacade getVolumeFacade() {
        return volumeFacade;
    }

    /**
     * @param volumeFacade the volumeFacade to set
     */
    public void setVolumeFacade(VolumeFacade volumeFacade) {
        this.volumeFacade = volumeFacade;
    }

    /**
     * @return the issueFacade
     */
    public IssueFacade getIssueFacade() {
        return issueFacade;
    }

    /**
     * @param issueFacade the issueFacade to set
     */
    public void setIssueFacade(IssueFacade issueFacade) {
        this.issueFacade = issueFacade;
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
