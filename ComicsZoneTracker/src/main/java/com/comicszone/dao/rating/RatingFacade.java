package com.comicszone.dao.rating;

import com.comicszone.dao.content.ContentFacade;
import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.Ucrating;
import com.comicszone.entity.Uirating;
import com.comicszone.entity.Users;
import com.comicszone.entity.Uvrating;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author ajpyatakov
 */

@Stateless
@LocalBean
public class RatingFacade implements RatingInterface {
    
    private Ucrating ucrating;
    private Uvrating uvrating;
    private Uirating uirating;
    
    @EJB
    private UcratingFacade ucratingFacade;
    @EJB
    private UvratingFacade uvratingFacade;
    @EJB
    private UiratingFacade uiratingFacade;
    
    @EJB
    private ContentFacade contentFacade;
    
    private Float contentRating;
    
    @Override
    public void init(Integer userId, Integer contentId, ContentType type) {
        switch(type) {
            case Comics:
                ucrating = ucratingFacade.findByUserAndComics(userId, contentId);
                if (ucrating == null) {
                    ucrating = new Ucrating(userId, contentId);
                    ucrating.setRating(0f);
                    ucratingFacade.create(ucrating);
                }
                contentRating = ucrating.getRating();
                break;
            case Volume:
                uvrating = uvratingFacade.findByUserAndVolume(userId, contentId);
                if (uvrating == null) {
                    uvrating = new Uvrating(userId, contentId);
                    uvrating.setRating(0f);
                    uvratingFacade.create(uvrating);
                }
                contentRating = uvrating.getRating();
                break;
            case Issue:
                uirating = uiratingFacade.findByUserAndIssue(userId, contentId);
                if (uirating == null) {
                    uirating = new Uirating(userId, contentId);
                    uirating.setRating(0f);
                    uiratingFacade.create(uirating);
                }
                contentRating = uirating.getRating();
                break;
        }
    }

    @Override
    public void rateContent(Content content, Users user, Float rating) {
        switch (content.getContentType()) {
            case Comics: 
                ucrating.setRating(rating);
                ucratingFacade.edit(ucrating);
                break;
            case Volume:
                uvrating.setRating(rating);
                uvratingFacade.edit(uvrating);
                break;
            case Issue:
                uirating.setRating(rating);
                uiratingFacade.edit(uirating);
                break;
        }
        if (content.getRating() != null)
            content.setRating((getAverageRating(content).floatValue()));
        else content.setRating(rating);
        contentFacade.edit(content);
    }
    
    public Double getAverageRating(Content content) {
        switch(content.getContentType()) {
            case Comics:
                return ucratingFacade.getAverageRating(content.getId());
            case Volume:
                return uvratingFacade.getAverageRating(content.getId());
            case Issue:
                return uiratingFacade.getAverageRating(content.getId());
        }
        return 0d;
    }
    
    @Override
    public Float getContentRating() {
        return contentRating;
    }
    
    @Override
    public void setContentRating(Float rating) {
        this.contentRating = rating;
    }
     
    /**
     * @return the ucrating
     */
    public Ucrating getUcrating() {
        return ucrating;
    }

    /**
     * @param ucrating the ucrating to set
     */
    public void setUcrating(Ucrating ucrating) {
        this.ucrating = ucrating;
    }

    /**
     * @return the uvrating
     */
    public Uvrating getUvrating() {
        return uvrating;
    }

    /**
     * @param uvrating the uvrating to set
     */
    public void setUvrating(Uvrating uvrating) {
        this.uvrating = uvrating;
    }

    /**
     * @return the uirating
     */
    public Uirating getUirating() {
        return uirating;
    }

    /**
     * @param uirating the uirating to set
     */
    public void setUirating(Uirating uirating) {
        this.uirating = uirating;
    }
    
        /**
     * @return the ucratingFacade
     */
    public UcratingFacade getUcratingFacade() {
        return ucratingFacade;
    }

    /**
     * @param ucratingFacade the ucratingFacade to set
     */
    public void setUcratingFacade(UcratingFacade ucratingFacade) {
        this.ucratingFacade = ucratingFacade;
    }

    /**
     * @return the uvratingFacade
     */
    public UvratingFacade getUvratingFacade() {
        return uvratingFacade;
    }

    /**
     * @param uvratingFacade the uvratingFacade to set
     */
    public void setUvratingFacade(UvratingFacade uvratingFacade) {
        this.uvratingFacade = uvratingFacade;
    }

    /**
     * @return the uiratingFacade
     */
    public UiratingFacade getUiratingFacade() {
        return uiratingFacade;
    }

    /**
     * @param uiratingFacade the uiratingFacade to set
     */
    public void setUiratingFacade(UiratingFacade uiratingFacade) {
        this.uiratingFacade = uiratingFacade;
    }
            
}
