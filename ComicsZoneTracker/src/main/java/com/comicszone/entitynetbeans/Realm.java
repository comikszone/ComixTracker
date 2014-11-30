/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "realm")
@NamedQueries({
    @NamedQuery(name = "Realm.findAll", query = "SELECT r FROM Realm r"),
    @NamedQuery(name = "Realm.findByRealmId", query = "SELECT r FROM Realm r WHERE r.realmId = :realmId"),
    @NamedQuery(name = "Realm.findByName", query = "SELECT r FROM Realm r WHERE r.name = :name")})
public class Realm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "realm_id")
    private Long realmId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "name")
    private long name;
    @OneToMany(mappedBy = "realmId", fetch = FetchType.LAZY)
    private List<Character> characterList;

    public Realm() {
    }

    public Realm(Long realmId) {
        this.realmId = realmId;
    }

    public Realm(Long realmId, long name) {
        this.realmId = realmId;
        this.name = name;
    }

    public Long getRealmId() {
        return realmId;
    }

    public void setRealmId(Long realmId) {
        this.realmId = realmId;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (realmId != null ? realmId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Realm)) {
            return false;
        }
        Realm other = (Realm) object;
        if ((this.realmId == null && other.realmId != null) || (this.realmId != null && !this.realmId.equals(other.realmId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Realm[ realmId=" + realmId + " ]";
    }
    
}
