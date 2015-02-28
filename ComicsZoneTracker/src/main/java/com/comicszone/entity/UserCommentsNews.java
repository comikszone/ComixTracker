/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "user_comments_news")
@NamedQueries({
    @NamedQuery(name = "UserCommentsNews.findAll", query = "SELECT n FROM UserCommentsNews n"),
    @NamedQuery(name = "UserCommentsNews.findById", query = "SELECT n FROM UserCommentsNews n WHERE n.id = :id")})
public class UserCommentsNews implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_comments_news_news_id_seq")
    @SequenceGenerator(name = "user_comments_news_news_id_seq", sequenceName = "user_comments_news_news_id_seq", 
            allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "news_id")
    private Integer id;
    @JoinColumn(name = "comics_id", referencedColumnName = "comics_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comics comicsId;
    @JoinColumn(name = "issue_id", referencedColumnName = "issue_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issueId;
    @JoinColumn(name = "volume_id", referencedColumnName = "volume_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Volume volumeId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Users userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "viewed")
    private Boolean viewed;
    @Column(name = "last_seen")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSeen;
    
    public UserCommentsNews() {
        
    }
    
    public UserCommentsNews(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    
    public Comics getComicsId() {
        return comicsId;
    }

    public void setComicsId(Comics comicsId) {
        this.comicsId = comicsId;
    }

    public Issue getIssueId() {
        return issueId;
    }

    public void setIssueId(Issue issueId) {
        this.issueId = issueId;
    }

    public Volume getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Volume volumeId) {
        this.volumeId = volumeId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Boolean getViewed() {
        return viewed;
    }

    public void setViewed(Boolean viewed) {
        this.viewed = viewed;
    }

    public Date getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Date lastSeen) {
        this.lastSeen = lastSeen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserCommentsNews)) {
            return false;
        }
        UserCommentsNews other = (UserCommentsNews) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entity.UserCommentsNews[ id=" + id + " ]";
    }
    
}
