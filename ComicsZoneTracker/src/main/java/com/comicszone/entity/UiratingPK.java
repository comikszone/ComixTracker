/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author GuronPavorro
 */
@Embeddable
public class UiratingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "issue_id")
    private int issueId;

    public UiratingPK() {
    }

    public UiratingPK(int userId, int issueId) {
        this.userId = userId;
        this.issueId = issueId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) issueId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UiratingPK)) {
            return false;
        }
        UiratingPK other = (UiratingPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.issueId != other.issueId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.UiratingPK[ userId=" + userId + ", issueId=" + issueId + " ]";
    }
    
}
