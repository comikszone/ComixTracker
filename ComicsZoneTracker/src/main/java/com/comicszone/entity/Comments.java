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
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "comments")
@NamedQueries({
    @NamedQuery(name = "Comments.findAll", query = "SELECT c FROM Comments c"),
    @NamedQuery(name = "Comments.findByCommentId", query = "SELECT c FROM Comments c WHERE c.commentId = :commentId"),
    @NamedQuery(name = "Comments.findByText", query = "SELECT c FROM Comments c WHERE c.text = :text")})
public class Comments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "comments_comment_id_seq")
    @SequenceGenerator(name = "comments_comment_id_seq", sequenceName = "comments_comment_id_seq", 
            allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "comment_id")
    private Integer commentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @Column(name = "comment_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commentTime;
    @JoinColumn(name = "comics_id", referencedColumnName = "comics_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comics comicsId;
    @JoinColumn(name = "issue_id", referencedColumnName = "issue_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Issue issueId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userId;
    @JoinColumn(name = "volume_id", referencedColumnName = "volume_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Volume volumeId;

    public Comments() {
    }

    public Comments(Integer commentId) {
        this.commentId = commentId;
    }

    public Comments(Integer commentId, String text) {
        this.commentId = commentId;
        this.text = text;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Volume getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(Volume volumeId) {
        this.volumeId = volumeId;
    }
    
    public Date getCommentTime() {
        return commentTime;
    }
    
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comments)) {
            return false;
        }
        Comments other = (Comments) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Comments[ commentId=" + commentId + " ]";
    }
    
}
