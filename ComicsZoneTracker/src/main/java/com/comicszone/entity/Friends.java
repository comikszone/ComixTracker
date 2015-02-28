/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Eschenko_DA
 */
@Entity
@Table(name = "friends")
@NamedQueries({
    @NamedQuery(name = "Friends.findAll", query = "SELECT f FROM Friends f"),
    @NamedQuery(name = "Friends.findByUsers",
            query = "SELECT f FROM Friends f WHERE f.user1 = :user1 AND f.user2 = :user2 OR f.user1 = :user2 AND f.user2 = :user1"),
    @NamedQuery(name = "Friends.findByStatus", query = "SELECT f FROM Friends f WHERE f.status = :status"),
    @NamedQuery(name = "Friends.findFriends",
            query = "SELECT f FROM Friends f "
                    + "WHERE (f.user1 = :user OR f.user2 = :user) "
                    + "AND f.status = com.comicszone.entitynetbeans.FriendshipStatus.friends"),
    @NamedQuery(name = "Friends.findFollowers",
            query = "SELECT f FROM Friends f "
                    + "WHERE f.user1 = :user "
                    + "AND (f.status = com.comicszone.entitynetbeans.FriendshipStatus.user2_subscribed "
                    + "OR f.status = com.comicszone.entitynetbeans.FriendshipStatus.user1_deleted_user2)"
                    + "OR f.user2 = :user "
                    + "AND (f.status = com.comicszone.entitynetbeans.FriendshipStatus.user1_subscribed "
                    + "OR f.status = com.comicszone.entitynetbeans.FriendshipStatus.user2_deleted_user1)"),
    @NamedQuery(name = "Friends.findUnconfirmedFriends", 
            query = "SELECT f FROM Friends f WHERE "
                    + "f.user1 = :user "
                    + "AND (f.status = com.comicszone.entitynetbeans.FriendshipStatus.user1_subscribed "
                    + "OR f.status = com.comicszone.entitynetbeans.FriendshipStatus.user2_deleted_user1)"
                    + "OR f.user2 = :user "
                    + "AND (f.status = com.comicszone.entitynetbeans.FriendshipStatus.user2_subscribed "
                    + "OR f.status = com.comicszone.entitynetbeans.FriendshipStatus.user1_deleted_user2)"),
})
/*@NamedNativeQueries({
    @NamedNativeQuery(name = "Friends.findFriends1", 
            query = "SELECT * FROM friends "
                    + "WHERE (user1_id = ? OR user2_id = ?) "
                    + "AND friendship_status = 'friends'", 
            resultClass = Friends.class),
    @NamedNativeQuery(name = "Friends.findFollowers1", 
            query = "SELECT * FROM friends "
                    + "WHERE user1_id = ? "
                    + "AND friendship_status = 'user2_subscribed' "
                    + "OR user2_id = ? "
                    + "AND friendship_status = 'user1_subscribed'",
            resultClass = Friends.class),
    @NamedNativeQuery(name = "Friends.findUnconfirmedFriends1",
            query = "SELECT * FROM friends WHERE "
                    + "user1_id = ? "
                    + "AND friendship_status = 'user1_subscribed' "
                    + "OR user2_id = ? "
                    + "AND friendship_status = 'user2_subscribed'",
            resultClass = Friends.class)
})*/
public class Friends implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "friends_id_seq")
    @SequenceGenerator(name = "friends_id_seq", sequenceName = "friends_id_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "user1_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users user1;
    @JoinColumn(name = "user2_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users user2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "friendship_status")
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "friendsNoteId", fetch = FetchType.LAZY)
    private List<UserFriendsNews> friendsNews;

    public Friends() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUser1() {
        return user1;
    }

    public void setUser1(Users user1) {
        this.user1 = user1;
    }

    public Users getUser2() {
        return user2;
    }

    public void setUser2(Users user2) {
        this.user2 = user2;
    }
    
    public FriendshipStatus getStatus() {
        return status;
    }
    
    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public List<UserFriendsNews> getFriendsNews() {
        return friendsNews;
    }

    public void setFriendsNews(List<UserFriendsNews> friendsNews) {
        this.friendsNews = friendsNews;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Friends other = (Friends) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Friends{id=" + id + ", status=" + status +", user1=" + user1 + ", user2=" + user2 + '}';
    }

}
