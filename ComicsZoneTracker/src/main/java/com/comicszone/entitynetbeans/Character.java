/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import com.comicszone.dao.AjaxComicsCharacter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "\"CHARACTER\"")
@NamedQueries({
    @NamedQuery(name = "Character.findAll", query = "SELECT c FROM Character c"),
    @NamedQuery(name = "Character.findById", query = "SELECT c FROM Character c WHERE c.Id = :Id"),
    @NamedQuery(name = "Character.findByName", query = "SELECT c FROM Character c WHERE c.name = :name"),
    @NamedQuery(name = "Character.findByRealName", query = "SELECT c FROM Character c WHERE c.realName = :realName"),
    @NamedQuery(name = "Character.findByDescription", query = "SELECT c FROM Character c WHERE c.description = :description"),
    @NamedQuery(name = "Character.findByImage", query = "SELECT c FROM Character c WHERE c.image = :image"),
    @NamedQuery(name = "Character.findByNameStartsWith", query = "SELECT c FROM Character c WHERE  LOWER(c.name) LIKE :name")
})
public class Character implements AjaxComicsCharacter, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "char_id")
    private Integer Id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "real_name")
    private String realName;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 2147483647)
    @Column(name = "image")
    private String image;
    @JoinTable(name = "CHARREFS", joinColumns = {
        @JoinColumn(name = "char_id", referencedColumnName = "char_id")}, inverseJoinColumns = {
        @JoinColumn(name = "issue_id", referencedColumnName = "issue_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Issue> issueList;
    @JoinTable(name = "CHARVER", joinColumns = {
        @JoinColumn(name = "char1", referencedColumnName = "char_id")}, inverseJoinColumns = {
        @JoinColumn(name = "char2", referencedColumnName = "char_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Character> characterList;
    @ManyToMany(mappedBy = "characterList", fetch = FetchType.LAZY)
    private List<Character> characterList1;
    @JoinColumn(name = "realm_id", referencedColumnName = "realm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Realm realmId;
    @JoinColumn(name = "universe_id", referencedColumnName = "universe_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Universe universeId;

    public Character() {
    }

    public Character(Integer charId) {
        this.Id = charId;
    }

    public Character(Integer charId, String name) {
        this.Id = charId;
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    public List<Character> getCharacterList1() {
        return characterList1;
    }

    public void setCharacterList1(List<Character> characterList1) {
        this.characterList1 = characterList1;
    }

    public Realm getRealmId() {
        return realmId;
    }

    public void setRealmId(Realm realmId) {
        this.realmId = realmId;
    }

    public Universe getUniverseId() {
        return universeId;
    }

    public void setUniverseId(Universe universeId) {
        this.universeId = universeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Character)) {
            return false;
        }
        Character other = (Character) object;
        if ((this.Id == null && other.Id != null) || (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entitynetbeans.Character[ charId=" + Id + " ]";
    }    
}
