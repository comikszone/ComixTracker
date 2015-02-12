/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author GuronPavorro
 */
@Entity
@Table(name = "uirating")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Uirating.findAll", query = "SELECT u FROM Uirating u"),
    @NamedQuery(name = "Uirating.findByUserId", query = "SELECT u FROM Uirating u WHERE u.uiratingPK.userId = :userId"),
    @NamedQuery(name = "Uirating.findByIssueId", query = "SELECT u FROM Uirating u WHERE u.uiratingPK.issueId = :issueId"),
    @NamedQuery(name = "Uirating.findByRating", query = "SELECT u FROM Uirating u WHERE u.rating = :rating")})
public class Uirating implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UiratingPK uiratingPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private float rating;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "issue_id", referencedColumnName = "issue_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Issue issue;

    public Uirating() {
    }

    public Uirating(UiratingPK uiratingPK) {
        this.uiratingPK = uiratingPK;
    }

    public Uirating(UiratingPK uiratingPK, float rating) {
        this.uiratingPK = uiratingPK;
        this.rating = rating;
    }

    public Uirating(int userId, int issueId) {
        this.uiratingPK = new UiratingPK(userId, issueId);
    }

    public UiratingPK getUiratingPK() {
        return uiratingPK;
    }

    public void setUiratingPK(UiratingPK uiratingPK) {
        this.uiratingPK = uiratingPK;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uiratingPK != null ? uiratingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uirating)) {
            return false;
        }
        Uirating other = (Uirating) object;
        if ((this.uiratingPK == null && other.uiratingPK != null) || (this.uiratingPK != null && !this.uiratingPK.equals(other.uiratingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Uirating[ uiratingPK=" + uiratingPK + " ]";
    }
    
}
