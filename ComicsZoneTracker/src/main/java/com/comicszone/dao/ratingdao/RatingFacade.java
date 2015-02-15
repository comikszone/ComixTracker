package com.comicszone.dao.ratingdao;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.contentdao.ContentFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.ContentType;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Ucrating;
import com.comicszone.entitynetbeans.Uirating;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.entitynetbeans.Uvrating;
import com.comicszone.entitynetbeans.Volume;
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
    
    public void init(Integer userId, Integer contentId, ContentType type) {
        switch(type) {
            case Comics:
                ucrating = ucratingFacade.findByUserAndComics(userId, contentId);
                if (ucrating == null) {
                    ucrating = new Ucrating(userId, contentId);
                    ucrating.setRating(0f);
                    ucratingFacade.create(ucrating);
                }
                break;
            case Volume:
                uvrating = uvratingFacade.findByUserAndVolume(userId, contentId);
                if (uvrating == null) {
                    uvrating = new Uvrating(userId, contentId);
                    uvrating.setRating(0f);
                    uvratingFacade.create(uvrating);
                }
                break;
            case Issue:
                uirating = uiratingFacade.findByUserAndIssue(userId, contentId);
                if (uirating == null) {
                    uirating = new Uirating(userId, contentId);
                    uirating.setRating(0f);
                    uiratingFacade.create(uirating);
                }
                break;
        }
    }

    @Override
    public void rateContent(Content content, Users user, Float rating) {
        ContentType type = content.getContentType();
        Long quantity = 0l;
        switch (type) {
            case Comics: 
                ucrating.setRating(rating);
                ucratingFacade.edit(ucrating);
                quantity = ucratingFacade.getCountByComics(content.getId());
                break;
            case Volume:
                uvrating.setRating(rating);
                uvratingFacade.edit(uvrating);
                quantity = uvratingFacade.getCountByVolume(content.getId());
                break;
            case Issue:
                uirating.setRating(rating);
                uiratingFacade.edit(uirating);
                quantity = uiratingFacade.getCountByIssue(content.getId());
                break;
        }
        if (content.getRating() != null)
            content.setRating((rating + content.getRating())/quantity);
        else content.setRating(rating);
        contentFacade.edit(content);
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
