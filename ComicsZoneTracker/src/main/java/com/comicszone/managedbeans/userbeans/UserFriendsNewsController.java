package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.FriendsFacade;
import com.comicszone.dao.news.FriendsNewsFacade;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.UserFriendsNews;
import com.comicszone.entity.Users;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class UserFriendsNewsController implements Serializable {
    
    @EJB
    private FriendsNewsFacade friendsNewsFacade;
    
    @EJB
    private FriendsFacade friendFacade;
    
    @EJB
    private UserDataFacade userDataFacade;
    
    private List<UserFriendsNews> news;
    private List<Users> otherUsers;
    private List<String> messages;
    private Users user;
    
    @PostConstruct
    private void init() {
        setUserNickname();
        update();
    }

    private void setUserNickname() {
        user = userDataFacade.getUserWithNickname(FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal().getName());
    }
    
    public List<UserFriendsNews> getNews() {
        return news;
    }
    
    public Users getOtherUser(int index) {
        return otherUsers.get(index);
    }
    
    public void setViewed(UserFriendsNews friendsNews) {
        friendsNewsFacade.setViewed(friendsNews.getNewsId(), Boolean.TRUE);
        update();
    }

    private void update() {
        List<List<?>> result = friendsNewsFacade.getUserNews(user);
        news = (List<UserFriendsNews>) result.get(0);
        otherUsers = (List<Users>) result.get(1);
        messages = (List<String>) result.get(2);
    }
    
    public boolean isForAddAsFriend(int index) {
        return messages.get(index).equals(FriendsNewsFacade.ADDED);
    }
    
    public boolean isForDeletion(int index) {
        return messages.get(index).equals(FriendsNewsFacade.DELETED);
    }
    
    public void addToFriends(int index) {
        friendFacade.addToFriends(user, otherUsers.get(index));
        update();
    }
    
    public void unfollow(int index) {
        friendFacade.removeFromFriends(user, otherUsers.get(index));
        update();
    }
}