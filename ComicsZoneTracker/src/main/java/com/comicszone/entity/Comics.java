
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

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
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "comics")
@NamedQueries({
    @NamedQuery(name = "Comics.findAll", query = "SELECT c FROM Comics c"),
    @NamedQuery(name = "Comics.findAllAscId", query = "SELECT c FROM Comics c ORDER BY c.Id ASC"),
    @NamedQuery(name = "Comics.findByName", query = "SELECT c FROM Comics c WHERE c.name = :name"),
    @NamedQuery(name = "Comics.findByDescription", query = "SELECT c FROM Comics c WHERE c.description = :description"),
    @NamedQuery(name = "Comics.findByImage", query = "SELECT c FROM Comics c WHERE c.image = :image"),
    @NamedQuery(name = "Comics.findByRating", query = "SELECT c FROM Comics c WHERE c.rating = :rating"),
    @NamedQuery(name = "Comics.findByVotes", query = "SELECT c FROM Comics c WHERE c.votes = :votes"),
    @NamedQuery(name = "Comics.findByStartDate", query = "SELECT c FROM Comics c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "Comics.findByEndDate", query = "SELECT c FROM Comics c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "Comics.findByInProgress", query = "SELECT c FROM Comics c WHERE c.inProgress = :inProgress"),
    @NamedQuery(name = "Comics.findByNameStartsWith", query = "SELECT c FROM Comics c WHERE  LOWER(c.name) LIKE :name"),
    @NamedQuery(name = "Comics.findByNameStartsWithAscId", query = "SELECT c FROM Comics c WHERE  LOWER(c.name) LIKE :name ORDER BY c.Id"),
    @NamedQuery(name = "Comics.getBestComicsWithImages", 
            query = "SELECT c FROM Comics c WHERE c.image !='/resources/images/image_not_found.png' ORDER BY c.rating DESC"),
    @NamedQuery(name = "Comics.findByChecking", query = "SELECT c FROM Comics c WHERE c.isChecked = :isChecked ORDER BY c.Id"),
    @NamedQuery(name = "Comics.findBySource", query = "SELECT c FROM Comics c WHERE c.source = :source"),
    
    //for ComicsCatalogue
    @NamedQuery(name = "Comics.count", 
            query = "SELECT COUNT(c) FROM Comics c"),
    @NamedQuery(name = "Comics.countFoundByNameAndRating", 
            query = "SELECT COUNT(c) FROM Comics c "
                    + "WHERE LOWER(c.name) LIKE :name AND c.rating >= :rating"),
    @NamedQuery(name = "Comics.countFoundByName",
            query = "SELECT COUNT(c) FROM Comics c WHERE LOWER(c.name) LIKE :name"),
    @NamedQuery(name = "Comics.countFoundByRating",
            query = "SELECT COUNT(c) FROM Comics c WHERE c.rating >= :rating"),

    //for news
    @NamedQuery(name = "Comics.getComicsWithImages", query = "SELECT c FROM Comics c WHERE c.image !='/resources/images/image_not_found.png'"), 
    @NamedQuery(name = "Comics.getComicsWithNewCommentsAfterUser", query = "SELECT DISTINCT c FROM Comics c INNER JOIN c.commentsList cl WHERE cl.commentTime > (SELECT MAX(ccl.commentTime) FROM  Comics cc INNER JOIN cc.commentsList ccl WHERE cc.Id = c.Id AND ccl.userId = :userId)"),
    @NamedQuery(name = "Comics.getMaxCommentDateForUser", query = "SELECT MAX(cl.commentTime) FROM  Comics c INNER JOIN c.commentsList cl WHERE c.Id = :Id AND cl.userId = :userId"), 
    @NamedQuery(name = "Comics.getCountOfNewCommentsForUser", query = "SELECT COUNT(cl.commentId) FROM Comics c INNER JOIN c.commentsList cl WHERE c.Id = :Id AND cl.commentTime > (SELECT MAX(ccl.commentTime) FROM  Comics cc INNER JOIN cc.commentsList ccl WHERE cc.Id = c.Id AND ccl.userId = :userId)"),
    @NamedQuery(name = "Comics.getCommentsAfterDateToComics", query = "SELECT DISTINCT cl FROM Comics c INNER JOIN c.commentsList cl WHERE c.Id = :Id AND cl.commentTime > :date ORDER BY cl.commentTime"),
    @NamedQuery(name = "Comics.findPlannedComics", 
            query = "SELECT DISTINCT c FROM Comics c "
                     + "JOIN c.volumeList v "
                     + "JOIN c.userTrackingStatusList l "
                     + "WHERE l.userTrackingStatusPK.userId = :userId AND l.status = 2"),
    @NamedQuery(name = "Comics.findCurrentComics", 
            query = "SELECT DISTINCT c FROM Comics c "
                     + "JOIN c.volumeList v "
                     + "JOIN c.userTrackingStatusList l "
                     + "WHERE l.userTrackingStatusPK.userId = :userId AND l.status = 1"),
    @NamedQuery(name = "Comics.findDroppedComics", 
            query = "SELECT DISTINCT c FROM Comics c "
                     + "JOIN c.volumeList v "
                     + "JOIN c.userTrackingStatusList l "
                     + "WHERE l.userTrackingStatusPK.userId = :userId AND l.status = 3"),
    @NamedQuery(name = "Comics.getTotalIssueCount",
            query = "SELECT COUNT(i.Id) FROM Issue i"
                    + " JOIN i.volumeId v "
                    + " JOIN v.comicsId c"
                    + " WHERE c.Id = :comicsId"),
    @NamedQuery(name = "Comics.getMarkedIssueCount",
            query = "SELECT COUNT(i.Id) FROM Issue i"
                    + " JOIN i.usersList u "
                    + " JOIN i.volumeId v"
                    + " JOIN v.comicsId c"
                    + " WHERE c.Id = :comicsId AND u.userId = :userId"),
    @NamedQuery(name = "Comics.getCommentNewsForUserAndComics", query = "SELECT DISTINCT n FROM Comics c INNER JOIN c.commentsNews n WHERE c = :comics AND n.userId = :user")})

public class Comics implements Serializable, CommentsContainer, Content {
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private float rating;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comics", fetch = FetchType.LAZY)
    private List<UserTrackingStatus> userTrackingStatusList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comics_comics_id_seq")
    @SequenceGenerator(name = "comics_comics_id_seq", sequenceName = "comics_comics_id_seq", allocationSize=1)
    @Basic(optional = false)
    @Column(name = "comics_id")
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
    @Column(name = "image")
    private String image;
    @Column(name = "votes")
    private Integer votes;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "in_progress")
    @Temporal(TemporalType.DATE)
    private Date inProgress;
    @Column(name = "is_checked")
    private Boolean isChecked;
    @Column(name = "source")
    private String source;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comicsId", fetch = FetchType.LAZY)
    private List<Volume> volumeList;
    @OneToMany(mappedBy = "comicsId", fetch = FetchType.LAZY)
    @OrderBy("commentId")
    private List<Comments> commentsList;
    @JoinColumn(name = "imprint_id", referencedColumnName = "imprint_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imprint imprintId;
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisherId;
    @OneToMany(mappedBy = "comicsId", fetch = FetchType.LAZY)
    private List<UserCommentsNews> commentsNews;
    private static final ContentType CONTENT_TYPE = ContentType.Comics;

    public Comics() {
    }

    public Comics(Integer comicsId) {
        this.Id = comicsId;
    }

    public Comics(Integer comicsId, String name) {
        this.Id = comicsId;
        this.name = name;
    }
    
    public Comics(String title, String description, String image, Publisher publisher, Imprint imprint, String source) {
        this.name = title;
        this.description = description;
        this.image = image;
        this.publisherId = publisher;
        this.imprintId = imprint;
        this.source = source;
    }

    @Override
    public Integer getId() {
        return Id;
    }

    @Override
    public void setId(Integer Id) {
        this.Id = Id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getInProgress() {
        return inProgress;
    }

    public void setInProgress(Date inProgress) {
        this.inProgress = inProgress;
    }

    public List<Volume> getVolumeList() {
        return volumeList;
    }

    public void setVolumeList(List<Volume> volumeList) {
        this.volumeList = volumeList;
    }

    @Override
    public List<UserCommentsNews> getCommentsNews() {
        return commentsNews;
    }

    @Override
    public void setCommentsNews(List<UserCommentsNews> commentsNews) {
        this.commentsNews = commentsNews;
    }

    @Override
    public List<Comments> getCommentsList() {
        return commentsList;
    }

    @Override
    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public Imprint getImprintId() {
        return imprintId;
    }

    public void setImprintId(Imprint imprintId) {
        this.imprintId = imprintId;
    }

    public Publisher getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Publisher publisherId) {
        this.publisherId = publisherId;
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
    public String getSource() {
        return source;
    }
    
    @Override
    public void setSource(String source) {
        this.source = source;
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
        if (!(object instanceof Comics)) {
            return false;
        }
        Comics other = (Comics) object;
        if ((this.Id == null && other.Id != null) || (this.Id != null && !this.Id.equals(other.Id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entity.Comics[ comicsId=" + Id + " ]";
    }

    @Override
    public ContentType getContentType() {
        return CONTENT_TYPE;
    }


    @Override
    public String getExtraInfo() {
        if (imprintId == null) {
            return "Publisher: " + publisherId.getName();
        } else {
            return "Publisher: " + publisherId.getName() + "\n" + 
                    "Imprint: " + imprintId.getName();
        }
    }

    @XmlTransient
    @JsonIgnore
    public List<UserTrackingStatus> getUserTrackingStatusList() {
        return userTrackingStatusList;
    }

    public void setUserTrackingStatusList(List<UserTrackingStatus> userTrackingStatusList) {
        this.userTrackingStatusList = userTrackingStatusList;
    }
}
