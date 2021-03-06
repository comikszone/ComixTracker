/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.ByteArrayInputStream;
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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByNickname", query = "SELECT u FROM Users u WHERE u.nickname = :nickname"),
    @NamedQuery(name = "Users.findByPass", query = "SELECT u FROM Users u WHERE u.pass = :pass"),
    @NamedQuery(name = "Users.findBySex", query = "SELECT u FROM Users u WHERE u.sex = :sex"),
    @NamedQuery(name = "Users.findByBirthday", query = "SELECT u FROM Users u WHERE u.birthday = :birthday"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email AND u.isSocial=FALSE"),
    @NamedQuery(name = "Users.findByOnline", query = "SELECT u FROM Users u WHERE u.online = :online"),
    @NamedQuery(name = "Users.findByBanned", query = "SELECT u FROM Users u WHERE u.banned = :banned"),
    @NamedQuery(name = "Users.findByNicknameStartsWith", query = "SELECT u FROM Users u WHERE LOWER(u.nickname) LIKE :nickname"),
    @NamedQuery(name = "Users.findByRealNicknameStartsWith", query = "SELECT u FROM Users u WHERE LOWER(u.realNickname) LIKE :realNickname"),
    @NamedQuery(name = "Users.findBannedOrUnbannedByNicknameStartsWith", query = "SELECT u FROM Users u WHERE u.banned = :banned AND LOWER(u.nickname) LIKE :nickname"),
    @NamedQuery(name = "Users.findBannedOrUnbannedByRealNicknameStartsWith", query = "SELECT u FROM Users u WHERE u.banned = :banned AND LOWER(u.realNickname) LIKE :realNickname"),
    @NamedQuery(name = "Users.findByRecoveryPasswordId", query = "SELECT u FROM Users u WHERE u.recoveryPasswordId = :uid AND u.isSocial=FALSE"),
    @NamedQuery(name = "Users.findByNicknameStartsWith", query = "SELECT u FROM Users u WHERE LOWER(u.nickname) LIKE :nickname")})
public class Users implements Serializable {
    @Lob
    @Column(name = "avatar")
    private byte[] avatar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private List<UserTrackingStatus> userTrackingStatusList;
    @JoinTable(name = "user_group", joinColumns = {
        @JoinColumn(name = "nickname", referencedColumnName = "nickname")}, inverseJoinColumns = {
        @JoinColumn(name = "nickname", referencedColumnName = "nickname")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Users> usersList;
    @ManyToMany(mappedBy = "usersList", fetch = FetchType.LAZY)
    private List<Users> usersList1;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(name = "users_user_id_seq", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nickname")
    private String nickname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pass")
    private String pass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sex")
    private int sex;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Недопустимый адрес электронной почты")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @Size(min = 1, max = 2147483647)
    @Column(name = "email")
    private String email;
    @Column(name = "online")
    private Boolean online;
    @Basic(optional = false)
    @NotNull
    @Column(name = "banned")
    private boolean banned;
    @Column(name="name")
    private String name;
    @Column(name="avatar_url")
    private String avatarUrl;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "recovery_password_time")
    private Date recoveryPasswordTime;
    @Column(name = "recovery_password_id")
    private String recoveryPasswordId;
    @Column (name = "real_nickname")
    private String realNickname;
    @Column (name = "is_social")
    private Boolean isSocial;
//    @JoinTable(name = "friends", joinColumns = {
//        @JoinColumn(name = "user2_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
//        @JoinColumn(name = "user1_id", referencedColumnName = "user_id")})
//    @ManyToMany(fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "user1", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Friends> friendsList;
    //@ManyToMany(mappedBy = "usersList", fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "user2", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Friends> friendsList1;
    @JoinTable(name = "progress", joinColumns = {
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")}, inverseJoinColumns = {
        @JoinColumn(name = "issue_id", referencedColumnName = "issue_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Issue> issueList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.LAZY)
    private List<UserGroup> userGroupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<Comments> commentsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Messages> messagesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Messages> messagesList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<UserCommentsNews> commentsNews;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<UserFriendsNews> friendsNews;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.pass = password;
    }

    public Users(Integer userId, String nickname, String pass, int sex, String email, boolean banned) {
        this.userId = userId;
        this.nickname = nickname;
        this.pass = pass;
        this.sex = sex;
        this.email = email;
        this.banned = banned;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public boolean getBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getRecoveryPasswordTime() {
        return recoveryPasswordTime;
    }

    public void setRecoveryPasswordTime(Date recoveryPasswordTime) {
        this.recoveryPasswordTime = recoveryPasswordTime;
    }

    public String getRecoveryPasswordId() {
        return recoveryPasswordId;
    }

    public void setRecoveryPasswordId(String recoveryPasswordId) {
        this.recoveryPasswordId = recoveryPasswordId;
    }

    public String getRealNickname() {
        return realNickname;
    }

    public void setRealNickname(String realNickname) {
        this.realNickname = realNickname;
    }

    public Boolean getIsSocial() {
        return isSocial;
    }

    public void setIsSocial(Boolean isSocial) {
        this.isSocial = isSocial;
    }

    public List<Issue> getIssueList() {
        return issueList;
    }

    public void setIssueList(List<Issue> issueList) {
        this.issueList = issueList;
    }

    public List<UserGroup> getUserGroupList() {
        return userGroupList;
    }

    public void setUserGroupList(List<UserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }

    public List<Comments> getCommentsList() {
        return commentsList;
    }

    public void setCommentsList(List<Comments> commentsList) {
        this.commentsList = commentsList;
    }

    public List<Messages> getMessagesList() {
        return messagesList;
    }

    public void setMessagesList(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    public List<Messages> getMessagesList1() {
        return messagesList1;
    }

    public void setMessagesList1(List<Messages> messagesList1) {
        this.messagesList1 = messagesList1;
    }

    public List<Friends> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<Friends> friendsList) {
        this.friendsList = friendsList;
    }

    public List<Friends> getFriendsList1() {
        return friendsList1;
    }

    public void setFriendsList1(List<Friends> friendsList1) {
        this.friendsList1 = friendsList1;
    }

    public List<UserCommentsNews> getCommentsNews() {
        return commentsNews;
    }

    public void setCommentsNews(List<UserCommentsNews> commentsNews) {
        this.commentsNews = commentsNews;
    }

    public List<UserFriendsNews> getFriendsNews() {
        return friendsNews;
    }

    public void setFriendsNews(List<UserFriendsNews> friendsNews) {
        this.friendsNews = friendsNews;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Users{" + "avatar=" + avatar + ", usersList=" + usersList + ", usersList1=" + usersList1 + ", userId=" + userId + ", nickname=" + nickname + ", pass=" + pass + ", sex=" + sex + ", birthday=" + birthday + ", email=" + email + ", online=" + online + ", banned=" + banned + ", name=" + name + ", avatarUrl=" + avatarUrl + ", recoveryPasswordTime=" + recoveryPasswordTime + ", recoveryPasswordId=" + recoveryPasswordId + ", realNickname=" + realNickname + ", isSocial=" + isSocial + ", friendsList=" + friendsList + ", friendsList1=" + friendsList1 + ", issueList=" + issueList + ", userGroupList=" + userGroupList + ", commentsList=" + commentsList + ", messagesList=" + messagesList + ", messagesList1=" + messagesList1 + '}';
    }





    public void addFriendToFriendsList(Friends friend) {
        friendsList.add(friend);
    }
    
    public void addFriendToFriendsList1(Friends friend) {
        friendsList1.add(friend);
    }
    public void addMessageToMessagesList(Messages message)
    {
        messagesList.add(message);
    }
    
    public void addMessageToMessagesList1(Messages message)
    {
        messagesList1.add(message);
    }

    public void removeFriendFromFriendsList(Friends friend) {
        friendsList.remove(friend);
    }
    
    public void removeFriendFromFriendsList1(Friends friend) {
        friendsList1.remove(friend);
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Users> getUsersList1() {
        return usersList1;
    }

    public void setUsersList1(List<Users> usersList1) {
        this.usersList1 = usersList1;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @XmlTransient
    @JsonIgnore
    public List<UserTrackingStatus> getUserTrackingStatusList() {
        return userTrackingStatusList;
    }

    public void setUserTrackingStatusList(List<UserTrackingStatus> userTrackingStatusList) {
        this.userTrackingStatusList = userTrackingStatusList;
    }

    public String getAvatarUrlOrDefaultAvatar() {
        return (avatarUrl == null || avatarUrl.equals("")) ? 
                "/resources/images/default_user_photo.png" :
                avatarUrl;
    }
}
