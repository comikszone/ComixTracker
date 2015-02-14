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

/**
 *
 * @author GuronPavorro
 */
@Entity
@Table(name = "uvrating")
@NamedQueries({
    @NamedQuery(name = "Uvrating.findAll", query = "SELECT u FROM Uvrating u"),
    @NamedQuery(name = "Uvrating.findByUserId", query = "SELECT u FROM Uvrating u WHERE u.uvratingPK.userId = :userId"),
    @NamedQuery(name = "Uvrating.findByVolumeId", query = "SELECT u FROM Uvrating u WHERE u.uvratingPK.volumeId = :volumeId"),
    @NamedQuery(name = "Uvrating.findByRating", query = "SELECT u FROM Uvrating u WHERE u.rating = :rating"),
    @NamedQuery(name = "Uvrating.findByUserAndVolume", 
                query = "SELECT u FROM Uvrating u WHERE u.uvratingPK.userId = :userId AND u.uvratingPK.volumeId = :volumeId"),
    @NamedQuery(name = "Uvrating.countByVolume", query = "SELECT COUNT(u) FROM Uvrating u WHERE u.uvratingPK.volumeId = :volumeId")})
public class Uvrating implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UvratingPK uvratingPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private Float rating;
    @JoinColumn(name = "volume_id", referencedColumnName = "volume_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Volume volume;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;

    public Uvrating() {
    }

    public Uvrating(UvratingPK uvratingPK) {
        this.uvratingPK = uvratingPK;
    }

    public Uvrating(UvratingPK uvratingPK, Float rating) {
        this.uvratingPK = uvratingPK;
        this.rating = rating;
    }

    public Uvrating(int userId, int volumeId) {
        this.uvratingPK = new UvratingPK(userId, volumeId);
    }

    public UvratingPK getUvratingPK() {
        return uvratingPK;
    }

    public void setUvratingPK(UvratingPK uvratingPK) {
        this.uvratingPK = uvratingPK;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uvratingPK != null ? uvratingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uvrating)) {
            return false;
        }
        Uvrating other = (Uvrating) object;
        if ((this.uvratingPK == null && other.uvratingPK != null) || (this.uvratingPK != null && !this.uvratingPK.equals(other.uvratingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Uvrating[ uvratingPK=" + uvratingPK + " ]";
    }
    
}
