/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Users;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author GuronPavorro
 */
@Stateless
public class ReadingFacade {
    @EJB
    private UserDataFacade userFacade;
    @EJB
    private IssueFacade issueFacade;
    
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
        
    public void markAsRead(Users user, Issue issueToMark) {
        user.getIssueList().add(issueToMark);
        getUserFacade().edit(user);
        issueToMark.getUsersList().add(user);
        getIssueFacade().edit(issueToMark);
    }
    
    public void unMark(Users user, Issue issueToUnmark) {
        user.getIssueList().remove(issueToUnmark);
        issueToUnmark.getUsersList().remove(user);
        getUserFacade().edit(user);
        getIssueFacade().edit(issueToUnmark);
    }

}
