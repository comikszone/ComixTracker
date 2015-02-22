package com.comicszone.dao.newsdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.entitynetbeans.Friends;
import com.comicszone.entitynetbeans.UserFriendsNews;
import com.comicszone.entitynetbeans.Users;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alexander
 */
@Stateless
public class FriendsNewsFacade extends AbstractFacade<UserFriendsNews> {
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    public static final String ADDED = "ADDED";
    public static final String DELETED = "DELETED";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public FriendsNewsFacade() {
        super(UserFriendsNews.class);
    }
    
    public List<List<?>> getUserNews(Users user) {
        if (user == null) {
            return null;
        }
        List<List<?>> result = new ArrayList<List<?>>(3);
        List<UserFriendsNews> allNews = user.getFriendsNews();
        List<UserFriendsNews> news = new ArrayList<UserFriendsNews>();
        List<Users> otherUsers = new ArrayList<Users>();
        List<String> messages = new ArrayList<String>();
        for (UserFriendsNews friendsNews : allNews) {
            if (!friendsNews.getViewed()) {
                news.add(friendsNews);
                Friends friendsNote = friendsNews.getFriendsNoteId();
                if (user.equals(friendsNote.getUser1())) {
                    otherUsers.add(friendsNote.getUser2());
                }
                else {
                    otherUsers.add(friendsNote.getUser1());
                }
                switch(friendsNews.getFriendsNoteId().getStatus()) {
                    case user1_deleted_user2:
                    case user2_deleted_user1:
                        messages.add(DELETED);
                        break;
                    case user1_subscribed:
                    case user2_subscribed:
                        messages.add(ADDED);
                        break;
                }
            }
        }
        result.add(news);
        result.add(otherUsers);
        result.add(messages);
        return result;
    }
    
    public void setViewed(Integer newsId, boolean viewed) {
        if (newsId == null) {
            return;
        }
        UserFriendsNews news = find(newsId);
        if (news == null) {
            return;
        }
        news.setViewed(viewed);
    }
    
    public void setViewed(Friends friendNote, Users user, boolean viewed) {
        TypedQuery<UserFriendsNews> newsQuery = em.createNamedQuery(
                "UserFriendsNews.getByUserAndFriendNote", UserFriendsNews.class);
        newsQuery.setParameter("user", user);
        newsQuery.setParameter("friendsNote", friendNote);
        UserFriendsNews news = newsQuery.getSingleResult();
        news.setViewed(viewed);
    }
    
    public void createNews(Friends friendNote, Users user, boolean viewed) {
        UserFriendsNews news = new UserFriendsNews();
        news.setFriendsNoteId(friendNote);
        news.setUserId(user);
        news.setViewed(viewed);
        create(news);
        friendNote.getFriendsNews().add(news);
        user.getFriendsNews().add(news);
    }
}