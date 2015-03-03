/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author alexander
 */
@Entity
@Table(name = "user_friends_news")
@NamedQueries({
    @NamedQuery(name = "UserFriendsNews.findAll", query = "SELECT u FROM UserFriendsNews u"),
    @NamedQuery(name = "UserFriendsNews.findByNewsId", query = "SELECT u FROM UserFriendsNews u WHERE u.newsId = :newsId"),
    @NamedQuery(name = "UserFriendsNews.findByViewed", query = "SELECT u FROM UserFriendsNews u WHERE u.viewed = :viewed"),
    @NamedQuery(name = "UserFriendsNews.getByUserAndFriendNote", query = "SELECT u FROM UserFriendsNews u WHERE u.userId = :user AND u.friendsNoteId = :friendsNote"),
    @NamedQuery(name = "UserFriendsNews.getByUserUnviewed", query = "SELECT u FROM UserFriendsNews u WHERE u.userId = :user AND u.viewed = false")})
public class UserFriendsNews implements Serializable {
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users userId;
    @JoinColumn(name = "friends_note_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Friends friendsNoteId;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_friends_news_news_id_seq")
    @SequenceGenerator(name = "user_friends_news_news_id_seq", sequenceName = "user_friends_news_news_id_seq", 
            allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "news_id")
    private Integer newsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "viewed")
    private boolean viewed;

    public UserFriendsNews() {
    }

    public UserFriendsNews(Integer newsId) {
        this.newsId = newsId;
    }

    public UserFriendsNews(Integer newsId, boolean viewed) {
        this.newsId = newsId;
        this.viewed = viewed;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public boolean getViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (newsId != null ? newsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserFriendsNews)) {
            return false;
        }
        UserFriendsNews other = (UserFriendsNews) object;
        if ((this.newsId == null && other.newsId != null) || (this.newsId != null && !this.newsId.equals(other.newsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entity.UserFriendsNews[ newsId=" + newsId + " ]";
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Friends getFriendsNoteId() {
        return friendsNoteId;
    }

    public void setFriendsNoteId(Friends friendsNoteId) {
        this.friendsNoteId = friendsNoteId;
    }
    
}
