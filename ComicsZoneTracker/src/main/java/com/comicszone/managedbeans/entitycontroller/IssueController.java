/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.IssueFacade;
import com.comicszone.entity.Issue;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Alexander Pyatakov
 */
@ManagedBean(name="issueController")
@ViewScoped
public class IssueController implements Serializable {
    @EJB
    public IssueFacade issueFacade;
    public Integer issueId;
    public Issue issue;
    @ManagedProperty(value = "#{ctrl}")
    public CardController ctrl;

    public CardController getCtrl() {
        return ctrl;
    }

    public void setCtrl(CardController ctrl) {
        this.ctrl = ctrl;
    }

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
        ctrl.setCard(issue.getCard());
        ctrl.setName(issue.getName());
        ctrl.init();
    }
    
    
    
}
