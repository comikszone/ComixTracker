/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.rating;

import com.comicszone.dao.content.ContentFacade;
import com.comicszone.dao.rating.RatingInterface;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.Users;
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
    private RatingInterface ratingFacade;
    @EJB
    private ContentFacade contentFacade;
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
            Content content = contentFacade.find(contentId, type);
            Float score = (Float)((UIRating) actionEvent.getComponent()).getValue();
            getRatingFacade().rateContent(content, getCurrentUser(), score);    
    }

    /**
     * @return the ratingFacade
     */
    public RatingInterface getRatingFacade() {
        return ratingFacade;
    }

    /**
     * @param ratingFacade the ratingFacade to set
     */
    public void setRatingFacade(RatingInterface ratingFacade) {
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
