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
    
    public void markAsRead(Users user, List<Issue> issueList) {
        List<Issue> prevList = user.getIssueList();
        prevList.addAll(issueList);
        user.setIssueList(prevList);
        userFacade.edit(user);
    }
}
