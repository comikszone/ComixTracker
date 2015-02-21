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
            query = "SELECT f FROM Friends f WHERE f.users = :user AND f.users1 = :user1 OR f.users = :user1 AND f.users1 = :user"),
    @NamedQuery(name = "Friends.findFriends",
            query = "SELECT f FROM Friends f WHERE (f.users = :user OR f.users1 = :user) AND f.areFriends = true")})
public class Friends implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friends_id_seq")
    @SequenceGenerator(name = "friends_id_seq", sequenceName = "friends_id_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_confirmed")
    private boolean isConfirmed;
    @Basic(optional = true)
    @NotNull
    @Column(name = "are_friends")
    private boolean areFriends;
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users users;
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users users1;

    public Friends() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    

    public boolean getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getUsers1() {
        return users1;
    }

    public void setUsers1(Users users1) {
        this.users1 = users1;
    }

    public boolean areFriends() {
        return areFriends;
    }

    public void setAreFriends(boolean areFriends) {
        this.areFriends = areFriends;
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
        return "Friends{" + "id=" + id + ", isConfirmed=" + isConfirmed + ", areFriends=" + areFriends + ", users=" + users + ", users1=" + users1 + '}';
    }
}
