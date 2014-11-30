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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "universe")
@NamedQueries({
    @NamedQuery(name = "Universe.findAll", query = "SELECT u FROM Universe u"),
    @NamedQuery(name = "Universe.findByUniverseId", query = "SELECT u FROM Universe u WHERE u.universeId = :universeId"),
    @NamedQuery(name = "Universe.findByName", query = "SELECT u FROM Universe u WHERE u.name = :name")})
public class Universe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "universe_universe_id_seq")
    @SequenceGenerator(name = "universe_universe_id_seq", sequenceName = "universe_universe_id_seq")
    @Basic(optional = false)
    @Column(name = "universe_id")
    private Integer universeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "universeId", fetch = FetchType.LAZY)
    private List<Character> characterList;

    public Universe() {
    }

    public Universe(Integer universeId) {
        this.universeId = universeId;
    }

    public Universe(Integer universeId, String name) {
        this.universeId = universeId;
        this.name = name;
    }

    public Integer getUniverseId() {
        return universeId;
    }

    public void setUniverseId(Integer universeId) {
        this.universeId = universeId;
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
        int hash = 0;
        hash += (universeId != null ? universeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Universe)) {
            return false;
        }
        Universe other = (Universe) object;
        if ((this.universeId == null && other.universeId != null) || (this.universeId != null && !this.universeId.equals(other.universeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Universe[ universeId=" + universeId + " ]";
    }
    
}
