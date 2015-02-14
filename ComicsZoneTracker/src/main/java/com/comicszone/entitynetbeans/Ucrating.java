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
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author GuronPavorro
 */
@Entity
@Table(name = "ucrating")
@NamedQueries({
    @NamedQuery(name = "Ucrating.findAll", query = "SELECT u FROM Ucrating u"),
    @NamedQuery(name = "Ucrating.findByUserId", query = "SELECT u FROM Ucrating u WHERE u.ucratingPK.userId = :userId"),
    @NamedQuery(name = "Ucrating.findByComicsId", query = "SELECT u FROM Ucrating u WHERE u.ucratingPK.comicsId = :comicsId"),
    @NamedQuery(name = "Ucrating.findByRating", query = "SELECT u FROM Ucrating u WHERE u.rating = :rating"),
    @NamedQuery(name = "Ucrating.findByUserAndComics", 
                query = "SELECT u FROM Ucrating u WHERE u.ucratingPK.userId = :userId AND u.ucratingPK.comicsId = :comicsId"),
    @NamedQuery(name = "Ucrating.countByComics", query = "SELECT COUNT(u) FROM Ucrating u WHERE u.ucratingPK.comicsId = :comicsId")})
public class Ucrating implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UcratingPK ucratingPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private Float rating;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Users users;
    @JoinColumn(name = "comics_id", referencedColumnName = "comics_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comics comics;

    public Ucrating() {
    }

    public Ucrating(UcratingPK ucratingPK) {
        this.ucratingPK = ucratingPK;
    }

    public Ucrating(UcratingPK ucratingPK, Float rating) {
        this.ucratingPK = ucratingPK;
        this.rating = rating;
    }

    public Ucrating(int userId, int comicsId) {
        this.ucratingPK = new UcratingPK(userId, comicsId);
    }

    public UcratingPK getUcratingPK() {
        return ucratingPK;
    }

    public void setUcratingPK(UcratingPK ucratingPK) {
        this.ucratingPK = ucratingPK;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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
        hash += (ucratingPK != null ? ucratingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ucrating)) {
            return false;
        }
        Ucrating other = (Ucrating) object;
        if ((this.ucratingPK == null && other.ucratingPK != null) || (this.ucratingPK != null && !this.ucratingPK.equals(other.ucratingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Ucrating[ ucratingPK=" + ucratingPK + " ]";
    }
    
}
