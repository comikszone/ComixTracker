/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Eschenko_DA
 */
@Entity
@Table(name = "friends")
@NamedQueries({
    @NamedQuery(name = "Friends.findAll", query = "SELECT f FROM Friends f"),
    @NamedQuery(name = "Friends.findByUsers",
            query = "SELECT f FROM Friends f WHERE f.user1 = :user1 AND f.user2 = :user2 OR f.user1 = :user2 AND f.user2 = :user1"),
    @NamedQuery(name = "Friends.findFriends",
            query = "SELECT f FROM Friends f "
                    + "WHERE (f.user1 = :user OR f.user2 = :user) "
                    + "AND f.user1Subscribed = true AND f.user2Subscribed = true"),
    @NamedQuery(name = "Friends.findFollowers",
            query = "SELECT f FROM Friends f "
                    + "WHERE f.user1 = :user AND f.user1Subscribed = false AND f.user2Subscribed = true "
                    + "OR f.user2 = :user AND f.user1Subscribed = true AND f.user2Subscribed = false"),
    @NamedQuery(name = "Friends.findUnconfirmedFriends",
            query = "SELECT f FROM Friends f WHERE "
                    + "f.user1 = :user AND f.user1Subscribed = true AND f.user2Subscribed = false "
                    + "OR f.user2 = :user AND f.user1Subscribed = false AND f.user2Subscribed = true")
        
})
public class Friends implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friends_id_seq")
    @SequenceGenerator(name = "friends_id_seq", sequenceName = "friends_id_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = true)
    @NotNull
    @Column(name = "user1_subscribed")
    private boolean user1Subscribed;
    @Basic(optional = true)
    @NotNull
    @Column(name = "user2_subscribed")
    private boolean user2Subscribed;
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users user1;
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users user2;

    public Friends() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser1() {
        return user1;
    }

    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    public Users getUser2() {
        return user2;
    }

    public void setUser2(Users user2) {
        this.user2 = user2;
    }
    
    public boolean isUser1Subscribed() {
        return user1Subscribed;
    }

    public void setUser1Subscribed(boolean user1Subscribed) {
        this.user1Subscribed = user1Subscribed;
    }

    public boolean isUser2Subscribed() {
        return user2Subscribed;
    }

    public void setUser2Subscribed(boolean user2Subscribed) {
        this.user2Subscribed = user2Subscribed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Friends other = (Friends) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Friends{" + "id=" + id + ", user1Subscribed=" + user1Subscribed + ", user2Subscribed=" + user2Subscribed + ", user1=" + user1 + ", user2=" + user2 + '}';
    }

    

}
