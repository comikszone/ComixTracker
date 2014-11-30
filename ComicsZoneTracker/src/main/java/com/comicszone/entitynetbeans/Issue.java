/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "issue")
@NamedQueries({
    @NamedQuery(name = "Issue.findAll", query = "SELECT i FROM Issue i"),
    @NamedQuery(name = "Issue.findByIssueId", query = "SELECT i FROM Issue i WHERE i.issueId = :issueId"),
    @NamedQuery(name = "Issue.findByName", query = "SELECT i FROM Issue i WHERE i.name = :name"),
    @NamedQuery(name = "Issue.findByDescription", query = "SELECT i FROM Issue i WHERE i.description = :description"),
    @NamedQuery(name = "Issue.findByImg", query = "SELECT i FROM Issue i WHERE i.img = :img"),
    @NamedQuery(name = "Issue.findByRating", query = "SELECT i FROM Issue i WHERE i.rating = :rating"),
    @NamedQuery(name = "Issue.findByVotes", query = "SELECT i FROM Issue i WHERE i.votes = :votes"),
    @NamedQuery(name = "Issue.findByRelDate", query = "SELECT i FROM Issue i WHERE i.relDate = :relDate")})
public class Issue implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "issue_issue_id_seq")
    @SequenceGenerator(name = "issue_issue_id_seq", sequenceName = "issue_issue_id_seq")
    @Basic(optional = false)
    @Column(name = "issue_id")
    private Integer issueId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 2147483647)
    @Column(name = "img")
    private String img;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Float rating;
    @Column(name = "votes")
    private Integer votes;
    @Column(name = "rel_date")
    @Temporal(TemporalType.DATE)
    private Date relDate;
    @ManyToMany(mappedBy = "issueList", fetch = FetchType.LAZY)
    private List<Character> characterList;
    @ManyToMany(mappedBy = "issueList", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @OneToMany(mappedBy = "issueId", fetch = FetchType.LAZY)
    private List<Comments> commentsList;
    @JoinColumn(name = "volume_id", referencedColumnName = "volume_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Volume volumeId;

    public Issue() {
    }

    public Issue(Integer issueId) {
        this.issueId = issueId;
    }

    public Issue(Integer issueId, String name) {
        this.issueId = issueId;
        this.name = name;
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Date getRelDate() {
        return relDate;
    }

    public void setRelDate(Date relDate) {
        this.relDate = relDate;
    }

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public Volume getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Volume volumeId) {
        this.volumeId = volumeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueId != null ? issueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) object;
        if ((this.issueId == null && other.issueId != null) || (this.issueId != null && !this.issueId.equals(other.issueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Issue[ issueId=" + issueId + " ]";
    }
    
}
