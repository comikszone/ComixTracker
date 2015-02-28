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
public class UvratingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "volume_id")
    private int volumeId;

    public UvratingPK() {
    }

    public UvratingPK(int userId, int volumeId) {
        this.userId = userId;
        this.volumeId = volumeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(int volumeId) {
        this.volumeId = volumeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) volumeId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UvratingPK)) {
            return false;
        }
        UvratingPK other = (UvratingPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.volumeId != other.volumeId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.UvratingPK[ userId=" + userId + ", volumeId=" + volumeId + " ]";
    }
    
}
