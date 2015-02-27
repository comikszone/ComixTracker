/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "user_group")
@NamedQueries({
    @NamedQuery(name = "UserGroup.findAll", query = "SELECT u FROM UserGroup u"),
    @NamedQuery(name = "UserGroup.findByGname", query = "SELECT u FROM UserGroup u WHERE u.userGroupPK.gname = :gname"),
    @NamedQuery(name = "UserGroup.findByNickname", query = "SELECT u FROM UserGroup u WHERE u.userGroupPK.nickname = :nickname")})
public class UserGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserGroupPK userGroupPK;
    @JoinColumn(name = "nickname", referencedColumnName = "nickname", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users users;

    public UserGroup() {
    }

    public UserGroup(UserGroupPK userGroupPK) {
        this.userGroupPK = userGroupPK;
    }

    public UserGroup(String gname, String nickname) {
        this.userGroupPK = new UserGroupPK(gname, nickname);
    }

    public UserGroupPK getUserGroupPK() {
        return userGroupPK;
    }

    public void setUserGroupPK(UserGroupPK userGroupPK) {
        this.userGroupPK = userGroupPK;
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
        hash += (userGroupPK != null ? userGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.userGroupPK == null && other.userGroupPK != null) || (this.userGroupPK != null && !this.userGroupPK.equals(other.userGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.UserGroup[ userGroupPK=" + userGroupPK + " ]";
    }
    
}
