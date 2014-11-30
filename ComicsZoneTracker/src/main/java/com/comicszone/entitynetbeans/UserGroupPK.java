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
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Embeddable
public class UserGroupPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "gname")
    private String gname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nickname")
    private String nickname;

    public UserGroupPK() {
    }

    public UserGroupPK(String gname, String nickname) {
        this.gname = gname;
        this.nickname = nickname;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gname != null ? gname.hashCode() : 0);
        hash += (nickname != null ? nickname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserGroupPK)) {
            return false;
        }
        UserGroupPK other = (UserGroupPK) object;
        if ((this.gname == null && other.gname != null) || (this.gname != null && !this.gname.equals(other.gname))) {
            return false;
        }
        if ((this.nickname == null && other.nickname != null) || (this.nickname != null && !this.nickname.equals(other.nickname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.UserGroupPK[ gname=" + gname + ", nickname=" + nickname + " ]";
    }
    
}
