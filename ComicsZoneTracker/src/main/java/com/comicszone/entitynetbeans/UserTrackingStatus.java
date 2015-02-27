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
import javax.persistence.FetchType;
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
@Table(name = "user_tracking_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTrackingStatus.findAll", query = "SELECT u FROM UserTrackingStatus u"),
    @NamedQuery(name = "UserTrackingStatus.findByUserId", query = "SELECT u FROM UserTrackingStatus u WHERE u.userTrackingStatusPK.userId = :userId"),
    @NamedQuery(name = "UserTrackingStatus.findByComicsId", query = "SELECT u FROM UserTrackingStatus u WHERE u.userTrackingStatusPK.comicsId = :comicsId"),
    @NamedQuery(name = "UserTrackingStatus.findByStatus", query = "SELECT u FROM UserTrackingStatus u WHERE u.status = :status")})
public class UserTrackingStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserTrackingStatusPK userTrackingStatusPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users users;
    @JoinColumn(name = "comics_id", referencedColumnName = "comics_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Comics comics;

    public UserTrackingStatus() {
    }

    public UserTrackingStatus(UserTrackingStatusPK userTrackingStatusPK) {
        this.userTrackingStatusPK = userTrackingStatusPK;
    }

    public UserTrackingStatus(UserTrackingStatusPK userTrackingStatusPK, int status) {
        this.userTrackingStatusPK = userTrackingStatusPK;
        this.status = status;
    }

    public UserTrackingStatus(int userId, int comicsId) {
        this.userTrackingStatusPK = new UserTrackingStatusPK(userId, comicsId);
    }

    public UserTrackingStatusPK getUserTrackingStatusPK() {
        return userTrackingStatusPK;
    }

    public void setUserTrackingStatusPK(UserTrackingStatusPK userTrackingStatusPK) {
        this.userTrackingStatusPK = userTrackingStatusPK;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Comics getComics() {
        return comics;
    }

    public void setComics(Comics comics) {
        this.comics = comics;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userTrackingStatusPK != null ? userTrackingStatusPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserTrackingStatus)) {
            return false;
        }
        UserTrackingStatus other = (UserTrackingStatus) object;
        if ((this.userTrackingStatusPK == null && other.userTrackingStatusPK != null) || (this.userTrackingStatusPK != null && !this.userTrackingStatusPK.equals(other.userTrackingStatusPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.UserTrackingStatus[ userTrackingStatusPK=" + userTrackingStatusPK + " ]";
    }
    
}
