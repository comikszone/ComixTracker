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
        
    public void markAsRead(Users user, List<Issue> issueList) {
        List<Issue> prevList = user.getIssueList();
        prevList.addAll(issueList);
        user.setIssueList(prevList);
        getUserFacade().edit(user);
    }
    
    public void unMark(Users user, Issue issueToUnmark) {
        List<Issue> prevList = user.getIssueList();
        prevList.remove(issueToUnmark);
        user.setIssueList(prevList);
        getUserFacade().edit(user);
    }

}
