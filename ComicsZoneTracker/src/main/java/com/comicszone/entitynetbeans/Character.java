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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "character")
@NamedQueries({
    @NamedQuery(name = "Character.findAll", query = "SELECT c FROM Character c"),
    @NamedQuery(name = "Character.findAllAscId", query = "SELECT c FROM Character c ORDER BY c.Id ASC"),
    @NamedQuery(name = "Character.findByName", query = "SELECT c FROM Character c WHERE c.name = :name"),
    @NamedQuery(name = "Character.findByRealName", query = "SELECT c FROM Character c WHERE c.realName = :realName"),
    @NamedQuery(name = "Character.findByDescription", query = "SELECT c FROM Character c WHERE c.description = :description"),
    @NamedQuery(name = "Character.findByImage", query = "SELECT c FROM Character c WHERE c.image = :image"),
    @NamedQuery(name = "Character.findBySource", query = "SELECT c FROM Character c WHERE c.source = :source"),
    @NamedQuery(name = "Character.findByNameStartsWith", query = "SELECT c FROM Character c WHERE  LOWER(c.name) LIKE :name"),
    @NamedQuery(name = "Character.findByNameStartsWithAscId", query = "SELECT c FROM Character c WHERE  LOWER(c.name) LIKE :name ORDER BY c.Id")})
public class Character implements Serializable,AjaxComicsCharacter {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "character_char_id_seq")
    @SequenceGenerator(name = "character_char_id_seq", sequenceName = "character_char_id_seq", allocationSize=1)
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
    @Column(name = "card")
    private String card;

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 2147483647)
    @Column(name = "image")
    private String image;
    @Column(name="source")
    private String source;
    @JoinTable(name = "charver", joinColumns = {
        @JoinColumn(name = "char1", referencedColumnName = "char_id")}, inverseJoinColumns = {
        @JoinColumn(name = "char2", referencedColumnName = "char_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Character> characterList;
    @ManyToMany(mappedBy = "characterList", fetch = FetchType.LAZY)
    private List<Character> characterList1;
    @JoinTable(name = "charrefs", joinColumns = {
        @JoinColumn(name = "char_id", referencedColumnName = "char_id")}, inverseJoinColumns = {
        @JoinColumn(name = "issue_id", referencedColumnName = "issue_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Issue> issueList;
    @JoinColumn(name = "realm_id", referencedColumnName = "realm_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Realm realmId;
    
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisherId;


    public Character() {
    }

    public Character(Integer charId) {
        this.Id = charId;
    }

    public Character(Integer charId, String name) {
        this.Id = charId;
        this.name = name;
    }
    
    public Character(String name, String description, String image, Publisher publisher, Realm realm, String source) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.publisherId = publisher;
        this.realmId = realm;
        this.source = source;
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
    
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public Realm getRealmId() {
        return realmId;
    }

    public void setRealmId(Realm realmId) {
        this.realmId = realmId;
    }

    public Publisher getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Publisher publisherId) {
        this.publisherId = publisherId;
    }
    
    public String getPublisher() {
        return publisherId.getName();
    }
    
    public String getRealm() {
        if (realmId == null) {
            return null;
        } else {
            return realmId.getName();
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.Id != null ? this.Id.hashCode() : 0);
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
        final Character other = (Character) obj;
        if (this.Id != other.Id && (this.Id == null || !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }
    

   

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Character[ charId=" + Id + " ]";
    }
    
}
