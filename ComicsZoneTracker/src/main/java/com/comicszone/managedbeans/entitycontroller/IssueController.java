/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.IssueFacade;
import com.comicszone.entitynetbeans.Issue;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Alexander Pyatakov
 */
@ManagedBean(name="issueController")
@RequestScoped
public class IssueController {
    @EJB
    public IssueFacade issueFacade;
    public Integer issueId;
    public Issue issue;

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }
    
    public void initIssue()
    {
        issue = issueFacade.find(issueId);
    }
    
}
