/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Users;
import java.io.ByteArrayInputStream;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Eschenko_DA
 */
@Named
@ApplicationScoped
public class UserAvatarsController {
    
    @EJB
    private UserDataFacade userDataFacade;
    
    public StreamedContent getAvatar() {
        
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the HTML. 
            //Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            String userId = context.getExternalContext().getRequestParameterMap().get("userId");
            Users user = userDataFacade.find(Integer.valueOf(userId));
            // So, browser is requesting the image. 
            //Return a real StreamedContent with the image bytes.
            return new DefaultStreamedContent(new ByteArrayInputStream(user.getAvatar()));
        }
    }
}
