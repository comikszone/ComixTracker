/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

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
    private String name;
    @OneToMany(mappedBy = "realmId", fetch = FetchType.LAZY)
    private List<Character> characterList;

    public Realm() {
    }

    public Realm(Long realmId) {
        this.realmId = realmId;
    }

    public Realm(Long realmId, String name) {
        this.realmId = realmId;
        this.name = name;
    }

    public Long getRealmId() {
        return realmId;
    }

    public void setRealmId(Long realmId) {
        this.realmId = realmId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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
        int hash = 7;
        hash = 37 * hash + (this.realmId != null ? this.realmId.hashCode() : 0);
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 37 * hash + (this.characterList != null ? this.characterList.hashCode() : 0);
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
        final Realm other = (Realm) obj;
        if (this.realmId != other.realmId && (this.realmId == null || !this.realmId.equals(other.realmId))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.characterList != other.characterList && (this.characterList == null || !this.characterList.equals(other.characterList))) {
            return false;
        }
        return true;
    }



    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Realm[ realmId=" + realmId + " ]";
    }
    
}
