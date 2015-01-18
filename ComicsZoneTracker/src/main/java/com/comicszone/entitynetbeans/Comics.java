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
@Table(name = "comics")
@NamedQueries({
    @NamedQuery(name = "Comics.findAll", query = "SELECT c FROM Comics c"),
    @NamedQuery(name = "Comics.findByName", query = "SELECT c FROM Comics c WHERE c.name = :name"),
    @NamedQuery(name = "Comics.findByDescription", query = "SELECT c FROM Comics c WHERE c.description = :description"),
    @NamedQuery(name = "Comics.findByImage", query = "SELECT c FROM Comics c WHERE c.image = :image"),
    @NamedQuery(name = "Comics.findByRating", query = "SELECT c FROM Comics c WHERE c.rating = :rating"),
    @NamedQuery(name = "Comics.findByVotes", query = "SELECT c FROM Comics c WHERE c.votes = :votes"),
    @NamedQuery(name = "Comics.findByStartDate", query = "SELECT c FROM Comics c WHERE c.startDate = :startDate"),
    @NamedQuery(name = "Comics.findByEndDate", query = "SELECT c FROM Comics c WHERE c.endDate = :endDate"),
    @NamedQuery(name = "Comics.findByInProgress", query = "SELECT c FROM Comics c WHERE c.inProgress = :inProgress"),
    @NamedQuery(name = "Comics.findByNameStartsWith", query = "SELECT c FROM Comics c WHERE  LOWER(c.name) LIKE :name"),
    //forComicsCatalogue
    @NamedQuery(name = "Comics.count", 
            query = "SELECT COUNT(c) FROM Comics c"),
    @NamedQuery(name = "Comics.countFoundByNameAndRating", 
            query = "SELECT COUNT(c) FROM Comics c WHERE LOWER(c.name) LIKE :name "
                    + "AND c.rating BETWEEN :rating AND :rating+1"),
    @NamedQuery(name = "Comics.countFoundByName",
            query = "SELECT COUNT(c) FROM Comics c WHERE  LOWER(c.name) LIKE :name"),
    @NamedQuery(name = "Comics.countFoundByRating",
            query = "SELECT COUNT(c) FROM Comics c WHERE c.rating BETWEEN :rating AND :rating+1"),
    @NamedQuery(name = "Comics.getComicsWithImages", query = "SELECT c FROM Comics c WHERE c.image !=''")})
public class Comics implements Serializable,AjaxComicsCharacter {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comics_comics_id_seq")
    @SequenceGenerator(name = "comics_comics_id_seq", sequenceName = "comics_comics_id_seq")
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rating")
    private Float rating;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comicsId", fetch = FetchType.LAZY)
    private List<Volume> volumeList;
    @OneToMany(mappedBy = "comicsId", fetch = FetchType.LAZY)
    private List<Comments> commentsList;
    @JoinColumn(name = "imprint_id", referencedColumnName = "imprint_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Imprint imprintId;
    @JoinColumn(name = "publisher_id", referencedColumnName = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisherId;

    public Comics() {
    }

    public Comics(Integer comicsId) {
        this.Id = comicsId;
    }

    public Comics(Integer comicsId, String name) {
        this.Id = comicsId;
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

    public List<Comments> getCommentsList() {
        return commentsList;
    }

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
        return "com.comicszone.entitynetbeans.Comics[ comicsId=" + Id + " ]";
    }
    
}
