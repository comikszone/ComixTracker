/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

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
public class UserTrackingStatusPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "comics_id")
    private int comicsId;

    public UserTrackingStatusPK() {
    }

    public UserTrackingStatusPK(int userId, int comicsId) {
        this.userId = userId;
        this.comicsId = comicsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getComicsId() {
        return comicsId;
    }

    public void setComicsId(int comicsId) {
        this.comicsId = comicsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) comicsId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserTrackingStatusPK)) {
            return false;
        }
        UserTrackingStatusPK other = (UserTrackingStatusPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.comicsId != other.comicsId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.UserTrackingStatusPK[ userId=" + userId + ", comicsId=" + comicsId + " ]";
    }
    
}
