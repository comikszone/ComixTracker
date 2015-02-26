/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "volume")
@NamedQueries({
    @NamedQuery(name = "Volume.findAll", query = "SELECT v FROM Volume v"),
    @NamedQuery(name = "Volume.findByVolumeId", query = "SELECT v FROM Volume v WHERE v.volumeId = :volumeId"),
    @NamedQuery(name = "Volume.findByName", query = "SELECT v FROM Volume v WHERE v.name = :name"),
    @NamedQuery(name = "Volume.findByDescription", query = "SELECT v FROM Volume v WHERE v.description = :description"),
    @NamedQuery(name = "Volume.findByImg", query = "SELECT v FROM Volume v WHERE v.img = :img"),
    @NamedQuery(name = "Volume.findByRating", query = "SELECT v FROM Volume v WHERE v.rating = :rating"),
    @NamedQuery(name = "Volume.findByVotes", query = "SELECT v FROM Volume v WHERE v.votes = :votes"),
    @NamedQuery(name = "Volume.findByChecking", query = "SELECT v FROM Volume v WHERE v.isChecked = :isChecked ORDER BY v.volumeId"),
    @NamedQuery(name = "Comics.findBySource", query = "SELECT c FROM Comics c WHERE c.source = :source"),
    //for news
    @NamedQuery(name = "Volume.getVolumesWithNewCommentsAfterUser", query = "SELECT DISTINCT v FROM Volume v INNER JOIN v.commentsList vl WHERE vl.commentTime > (SELECT MAX(vvl.commentTime) FROM  Volume vv INNER JOIN vv.commentsList vvl WHERE vv.volumeId = v.volumeId AND vvl.userId = :userId)"),
    @NamedQuery(name = "Volume.getMaxCommentDateForUser", query = "SELECT MAX(vl.commentTime) FROM  Volume v INNER JOIN v.commentsList vl WHERE v.volumeId = :Id AND vl.userId = :userId"), 
    @NamedQuery(name = "Volume.getCountOfNewCommentsForUser", query = "SELECT COUNT(vl.commentId) FROM Volume v INNER JOIN v.commentsList vl WHERE v.volumeId = :Id AND vl.commentTime > (SELECT MAX(vvl.commentTime) FROM  Volume vv INNER JOIN vv.commentsList vvl WHERE vv.volumeId = v.volumeId AND vvl.userId = :userId)"),
    @NamedQuery(name = "Volume.getCommentsAfterDateToVolume", query = "SELECT DISTINCT vl FROM Volume v INNER JOIN v.commentsList vl WHERE v.volumeId = :Id AND vl.commentTime > :date ORDER BY vl.commentTime")})
public class Volume implements Serializable, CommentsContainer, Content {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volume")
    private List<Uvrating> uvratingList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "volume_volume_id_seq")
    @SequenceGenerator(name = "volume_volume_id_seq", sequenceName = "volume_volume_id_seq")
    @Basic(optional = false)
    @Column(name = "volume_id")
    private Integer volumeId;
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
    @Column(name = "is_checked")
    private Boolean isChecked;
    @Column(name = "source")
    private String source;
    @JoinColumn(name = "comics_id", referencedColumnName = "comics_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Comics comicsId;
    @OneToMany(mappedBy = "volumeId", fetch = FetchType.LAZY)
    @OrderBy("commentId")
    private List<Comments> commentsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volumeId", fetch = FetchType.LAZY)
    private List<Issue> issueList;
    private final static ContentType CONTENT_TYPE = ContentType.Volume;

    public Volume() {
    }

    public Volume(Integer volumeId) {
        this.volumeId = volumeId;
    }

    public Volume(Integer volumeId, String name) {
        this.volumeId = volumeId;
        this.name = name;
    }

    public Integer getId() {
        return volumeId;
    }

    public void setId(Integer volumeId) {
        this.volumeId = volumeId;
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
        return img;
    }

    public void setImage(String image) {
        this.img = image;
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

    public Comics getComicsId() {
        return comicsId;
    }

    public void setComicsId(Comics comicsId) {
        this.comicsId = comicsId;
    }

    @Override
    public List<Comments> getCommentsList() {
        return commentsList;
    }

    @Override
    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
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
        hash += (volumeId != null ? volumeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Volume)) {
            return false;
        }
        Volume other = (Volume) object;
        if ((this.volumeId == null && other.volumeId != null) || (this.volumeId != null && !this.volumeId.equals(other.volumeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Volume[ volumeId=" + volumeId + " ]";
    }

    @Override
    public ContentType getContentType() {
        return CONTENT_TYPE;
    }

    @Override
    public String getExtraInfo() {
        return "Comics: " + comicsId.getName();
    }

    @XmlTransient
    @JsonIgnore
    public List<Uvrating> getUvratingList() {
        return uvratingList;
    }

    public void setUvratingList(List<Uvrating> uvratingList) {
        this.uvratingList = uvratingList;
    }
    
}
