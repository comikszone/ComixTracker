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
import javax.persistence.CascadeType;
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
import javax.persistence.OrderBy;
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
    @NamedQuery(name = "Issue.findByIssueId", query = "SELECT i FROM Issue i WHERE i.Id = :Id"),
    @NamedQuery(name = "Issue.findByName", query = "SELECT i FROM Issue i WHERE i.name = :name"),
    @NamedQuery(name = "Issue.findByDescription", query = "SELECT i FROM Issue i WHERE i.description = :description"),
    @NamedQuery(name = "Issue.findByImg", query = "SELECT i FROM Issue i WHERE i.image = :image"),
    @NamedQuery(name = "Issue.findByRating", query = "SELECT i FROM Issue i WHERE i.rating = :rating"),
    @NamedQuery(name = "Issue.findByVotes", query = "SELECT i FROM Issue i WHERE i.votes = :votes"),
    @NamedQuery(name = "Issue.findByRelDate", query = "SELECT i FROM Issue i WHERE i.relDate = :relDate"),
    @NamedQuery(name = "Issue.findByChecking", query = "SELECT i FROM Issue i WHERE i.isChecked = :isChecked ORDER BY i.Id"),
    @NamedQuery(name = "Issue.findByRelDate", query = "SELECT i FROM Issue i WHERE i.relDate = :relDate"),
    @NamedQuery(name = "Comics.findBySource", query = "SELECT c FROM Comics c WHERE c.source = :source"),
    //for news
    @NamedQuery(name = "Issue.getIssuesWithNewCommentsAfterUser", query = "SELECT DISTINCT i FROM Issue i INNER JOIN i.commentsList il WHERE il.commentTime > (SELECT MAX(iil.commentTime) FROM  Issue ii INNER JOIN ii.commentsList iil WHERE ii.Id = i.Id AND iil.userId = :userId)"),
    @NamedQuery(name = "Issue.getMaxCommentDateForUser", query = "SELECT MAX(il.commentTime) FROM  Issue i INNER JOIN i.commentsList il WHERE i.Id = :Id AND il.userId = :userId"), 
    @NamedQuery(name = "Issue.getCountOfNewCommentsForUser", query = "SELECT COUNT(il.commentId) FROM Issue i INNER JOIN i.commentsList il WHERE i.Id = :Id AND il.commentTime > (SELECT MAX(iil.commentTime) FROM  Issue ii INNER JOIN ii.commentsList iil WHERE ii.Id = i.Id AND iil.userId = :userId)"), 
    @NamedQuery(name = "Issue.getCommentsAfterDateToIssue", query = "SELECT DISTINCT il FROM Issue i INNER JOIN i.commentsList il WHERE i.Id = :Id AND il.commentTime > :date ORDER BY il.commentTime"),

    @NamedQuery(name = "Issue.findMarkedByUserAndComics", 
            query = "SELECT i FROM Issue i "
                  + "JOIN i.usersList u "
                  + "JOIN i.volumeId v "
                  + "JOIN v.comicsId c "
                  + "WHERE c.Id = :comicsId AND u.userId = :userId"),
    @NamedQuery(name = "Issue.findByComics", 
            query = "SELECT i FROM Issue i "
                  + "JOIN i.volumeId v "
                  + "JOIN v.comicsId c "
                  + "WHERE c.Id = :comicsId")})
public class Issue implements Serializable, CommentsContainer, Content, AjaxComicsCharacter {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "issue_issue_id_seq")
    @SequenceGenerator(name = "issue_issue_id_seq", sequenceName = "issue_issue_id_seq")
    @Basic(optional = false)
    @Column(name = "issue_id")
    private Integer Id;
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
    private String image;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Float rating;
    @Column(name = "votes")
    private Integer votes;
    @Column(name = "rel_date")
    @Temporal(TemporalType.DATE)
    private Date relDate;
    @Column(name = "is_checked")
    private Boolean isChecked;
    @Column(name = "source")
    private String source;
    @ManyToMany(mappedBy = "issueList", fetch = FetchType.LAZY)
    private List<Character> characterList;
    @ManyToMany(mappedBy = "issueList", fetch = FetchType.LAZY)
    private List<Users> usersList;
    @OneToMany(mappedBy = "issueId", fetch = FetchType.LAZY)
    @OrderBy("commentId")
    private List<Comments> commentsList;
    @JoinColumn(name = "volume_id", referencedColumnName = "volume_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Volume volumeId;
    private final static ContentType CONTENT_TYPE = ContentType.Issue;

    public Issue() {
    }

    public Issue(Integer issueId) {
        this.Id = issueId;
    }

    public Issue(Integer issueId, String name) {
        this.Id = issueId;
        this.name = name;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer issueId) {
        this.Id = issueId;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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

    @Override
    public List<Comments> getCommentsList() {
        return commentsList;
    }

    @Override
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
    public Boolean getIsChecked() {
        return isChecked;
    }
    
    @Override
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (Id != null ? Id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String getSource() {
        return source;
    }
    
    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Issue)) {
            return false;
        }
        Issue other = (Issue) object;
        if ((this.Id == null && other.Id != null) || (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Issue[ issueId=" + Id + " ]";
    }

    @Override
    public ContentType getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public String getExtraInfo() {
        return "Volume: " + volumeId.getName();
    }
    
}
