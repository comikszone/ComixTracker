package com.comicszone.dao.news;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.comments.CommentsFacade;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Comments;
import com.comicszone.entity.CommentsContainer;
import com.comicszone.entity.Issue;
import com.comicszone.entity.UserCommentsNews;
import com.comicszone.entity.Users;
import com.comicszone.entity.Volume;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alexander
 */
@Stateless
public class CommentsNewsFacade extends AbstractFacade<UserCommentsNews> {

    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @EJB
    private UserDataFacade userFacade;
    
    @EJB
    private ComicsFacade comicsFacade;
    
    @EJB
    private IssueFacade issueFacade;
    
    @EJB
    private VolumeFacade volumeFacade;

    public CommentsNewsFacade() {
        super(UserCommentsNews.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public UserCommentsNews addNews(CommentsContainer commentsContainer, 
            Users user, Boolean viewed) 
    {
        if (commentsContainer == null || user == null || viewed == null) {
            return null;
        }
        UserCommentsNews newNews = new UserCommentsNews();
        setContainerId(newNews, commentsContainer);
        newNews.setUserId(user);
        newNews.setLastSeen(new Date(System.currentTimeMillis()));
        newNews.setViewed(viewed);
        create(newNews);
        return newNews;
    }
    
    public void setViewed(Integer id, Boolean viewed) {
        UserCommentsNews news = find(id);
        if (news == null) {
            return;
        }
        news.setViewed(viewed);
    }
    
    public void updateNewsDate(Integer id) {
        UserCommentsNews news = find(id);
        if (news == null) {
            return;
        }
        news.setLastSeen(new Date(System.currentTimeMillis()));
    }

    private void setContainerId(UserCommentsNews news, CommentsContainer commentsContainer) {
        switch(commentsContainer.getContentType()) {
            case Comics:
                news.setComicsId((Comics)commentsContainer);
                return;
                
            case Issue:
                news.setIssueId((Issue)commentsContainer);
                return;
                
            case Volume:
                news.setVolumeId((Volume)commentsContainer);
        }
    }
    
    public List<List<?>> getUserNews(Users user) {
        if (user == null) {
            return null;
        }
        List<UserCommentsNews> userNews = user.getCommentsNews();
        //Comics
        //TypedQuery<Comics> newComicsQuery 
        //        = em.createNamedQuery("Comics.getComicsWithNewCommentsAfterUser", Comics.class);
        //newComicsQuery.setParameter("userId", user);
        //List<Comics> newComics = newComicsQuery.getResultList();
        TypedQuery<Date> lastComicsDates = 
                em.createNamedQuery("Comics.getMaxCommentDateForUser", Date.class);
        lastComicsDates.setParameter("userId", user);
        TypedQuery<Long> newCommentsToComicsNumberQuery = 
                em.createNamedQuery("Comics.getCountOfNewCommentsForUser", Long.class);
        newCommentsToComicsNumberQuery.setParameter("userId", user);
        TypedQuery<Comments> newCommentsToComicsQuery = 
                em.createNamedQuery("Comics.getCommentsAfterDateToComics", Comments.class);
        
        //Issues
        //TypedQuery<Issue> newIssuesQuery 
        //        = em.createNamedQuery("Issue.getIssuesWithNewCommentsAfterUser", Issue.class);
        //newIssuesQuery.setParameter("userId", user);
        //List<Issue> newIssues = newIssuesQuery.getResultList();
        TypedQuery<Date> lastIssuesDates = 
                em.createNamedQuery("Issue.getMaxCommentDateForUser", Date.class);
        lastIssuesDates.setParameter("userId", user);
        TypedQuery<Long> newCommentsToIssuesNumberQuery = 
                em.createNamedQuery("Issue.getCountOfNewCommentsForUser", Long.class);
        newCommentsToIssuesNumberQuery.setParameter("userId", user);
        TypedQuery<Comments> newCommentsToIssueQuery = 
                em.createNamedQuery("Issue.getCommentsAfterDateToIssue", Comments.class);
        
        //Volumes
        //TypedQuery<Volume> newVolumesQuery 
        //        = em.createNamedQuery("Volume.getVolumesWithNewCommentsAfterUser", Volume.class);
        //newVolumesQuery.setParameter("userId", user);
        //List<Volume> newVolumes = newVolumesQuery.getResultList();
        TypedQuery<Date> lastVolumesDates = 
                em.createNamedQuery("Volume.getMaxCommentDateForUser", Date.class);
        lastVolumesDates.setParameter("userId", user);
        TypedQuery<Long> newCommentsToVolumesNumberQuery = 
                em.createNamedQuery("Volume.getCountOfNewCommentsForUser", Long.class);
        newCommentsToVolumesNumberQuery.setParameter("userId", user);
        TypedQuery<Comments> newCommentsToVolumeQuery = 
                em.createNamedQuery("Volume.getCommentsAfterDateToVolume", Comments.class);
        
        //preparing for getting results
        List<List<?>> result = new ArrayList<List<?>>(5);
        //int capacity = newComics.size() + newIssues.size() + newVolumes.size();
        List<UserCommentsNews> news = new ArrayList<UserCommentsNews>();
        List<CommentsContainer> containers = new ArrayList<CommentsContainer>();
        List<Long> numberOfNewComments = new ArrayList<Long>();
        List<Date> dateOfLastUserComment = new ArrayList<Date>();
        List<List<Comments>> newComments = new ArrayList<List<Comments>>();
        
        //getting results
        for (UserCommentsNews currentNews : userNews) {
            if (currentNews.getViewed()) {
                continue;
            }
            Comics comics = currentNews.getComicsId();
            Issue issue = currentNews.getIssueId();
            Volume volume = currentNews.getVolumeId();
            if (comics != null) {
                lastComicsDates.setParameter("Id", comics.getId());
                Date lastDate = lastComicsDates.getSingleResult();
                newCommentsToComicsNumberQuery.setParameter("Id", comics.getId());
                newCommentsToComicsQuery.setParameter("Id", comics.getId());
                newCommentsToComicsQuery.setParameter("date", currentNews.getLastSeen());
                int i = 0, lastElement = containers.size() - 1;
                if (lastElement == -1 || lastDate.after(dateOfLastUserComment.get(0))) {
                    containers.add(0, comics);
                    numberOfNewComments.add(0, newCommentsToComicsNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(0, lastDate);
                    newComments.add(0, newCommentsToComicsQuery.getResultList());
                    news.add(0, currentNews);
                    continue;
                }
                if (lastDate.before(dateOfLastUserComment.get(lastElement))) {
                    containers.add(comics);
                    numberOfNewComments.add(newCommentsToComicsNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(lastDate);
                    newComments.add(newCommentsToComicsQuery.getResultList());
                    news.add(currentNews);
                    continue;
                }
                for ( ; i < lastElement; i++) {
                    if (lastDate.before(dateOfLastUserComment.get(i)) && 
                            lastDate.after(dateOfLastUserComment.get(i + 1))) {
                        containers.add(i + 1, comics);
                        numberOfNewComments.add(i + 1, newCommentsToComicsNumberQuery.getSingleResult());
                        dateOfLastUserComment.add(i + 1, lastDate);
                        newComments.add(i + 1, newCommentsToComicsQuery.getResultList());
                        news.add(i + 1, currentNews);
                        break;
                    }
                }
                continue;
            }
        
            if (issue != null) {
                lastIssuesDates.setParameter("Id", issue.getId());
                Date lastDate = lastIssuesDates.getSingleResult();
                newCommentsToIssuesNumberQuery.setParameter("Id", issue.getId());
                newCommentsToIssueQuery.setParameter("Id", issue.getId());
                newCommentsToIssueQuery.setParameter("date", currentNews.getLastSeen());
                int i = 0, lastElement = containers.size() - 1;
                if (lastElement == -1 || lastDate.after(dateOfLastUserComment.get(0))) {
                    containers.add(0, issue);
                    numberOfNewComments.add(0, newCommentsToIssuesNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(0, lastDate);
                    newComments.add(0, newCommentsToIssueQuery.getResultList());
                    news.add(0, currentNews);
                    continue;
                }
                if (lastDate.before(dateOfLastUserComment.get(lastElement))) {
                    containers.add(issue);
                    numberOfNewComments.add(newCommentsToIssuesNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(lastDate);
                    newComments.add(newCommentsToIssueQuery.getResultList());
                    news.add(currentNews);
                    continue;
                }
                for ( ; i < lastElement; i++) {
                    if (lastDate.before(dateOfLastUserComment.get(i)) && 
                            lastDate.after(dateOfLastUserComment.get(i + 1))) {
                        containers.add(i + 1, issue);
                        numberOfNewComments.add(i + 1, newCommentsToIssuesNumberQuery.getSingleResult());
                        dateOfLastUserComment.add(i + 1, lastDate);
                        newComments.add(i + 1, newCommentsToIssueQuery.getResultList());
                        news.add(i + 1, currentNews);
                        break;
                    }
                }
                continue;
            }
        
            if (volume != null) {
                lastVolumesDates.setParameter("Id", volume.getId());
                Date lastDate = lastVolumesDates.getSingleResult();
                newCommentsToVolumesNumberQuery.setParameter("Id", volume.getId());
                newCommentsToVolumeQuery.setParameter("Id", volume.getId());
                newCommentsToVolumeQuery.setParameter("date", currentNews.getLastSeen());
                int i = 0, lastElement = containers.size() - 1;
                if (lastElement == -1 || lastDate.after(dateOfLastUserComment.get(0))) {
                    containers.add(0, volume);
                    numberOfNewComments.add(0, newCommentsToVolumesNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(0, lastDate);
                    newComments.add(0, newCommentsToVolumeQuery.getResultList());
                    news.add(0, currentNews);
                    continue;
                }
                if (lastDate.before(dateOfLastUserComment.get(lastElement))) {
                    containers.add(volume);
                    numberOfNewComments.add(newCommentsToVolumesNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(lastDate);
                    newComments.add(newCommentsToVolumeQuery.getResultList());
                    news.add(currentNews);
                    continue;
                }
                for ( ; i < lastElement; i++) {
                    if (lastDate.before(dateOfLastUserComment.get(i)) && 
                            lastDate.after(dateOfLastUserComment.get(i + 1))) {
                        containers.add(i + 1, volume);
                        numberOfNewComments.add(i + 1, newCommentsToVolumesNumberQuery.getSingleResult());
                        dateOfLastUserComment.add(i + 1, lastDate);
                        newComments.add(i + 1, newCommentsToVolumeQuery.getResultList());
                        news.add(i + 1, currentNews);
                        break;
                    }
                }
            }
        }
        result.add(containers);
        result.add(numberOfNewComments);
        result.add(dateOfLastUserComment);
        result.add(newComments);
        result.add(news);
        return result;
    }
    
    public void setViewed(String userNickname, Integer commentsContainerId, 
            CommentsFacade.CommentToType type, Boolean viewed) 
    {
        if (userNickname == null || commentsContainerId == null) {
            return;
        }
        Users user = userFacade.getUserWithNickname(userNickname);
        if (user == null) {
            return;
        }
        UserCommentsNews news = null;
        TypedQuery<UserCommentsNews> newsQuery = null;
        switch(type) {
            case COMICS:
                Comics comics = comicsFacade.find(commentsContainerId);
                if (comics == null) {
                    return;
                }
                newsQuery = em.createNamedQuery("Comics.getCommentNewsForUserAndComics", 
                                UserCommentsNews.class);
                newsQuery.setParameter("comics", comics);
                newsQuery.setParameter("user", user);
                break;
                
            case ISSUE:
                Issue issue = issueFacade.find(commentsContainerId);
                if (issue == null) {
                    return;
                }
                newsQuery = em.createNamedQuery("Issue.getCommentNewsForUserAndIssue", 
                                UserCommentsNews.class);
                newsQuery.setParameter("issue", issue);
                newsQuery.setParameter("user", user);
                break;
                
            case VOLUME:
                Volume volume = volumeFacade.find(commentsContainerId);
                if (volume == null) {
                    return;
                }
                newsQuery = em.createNamedQuery("Volume.getCommentNewsForUserAndVolume", 
                                UserCommentsNews.class);
                newsQuery.setParameter("volume", volume);
                newsQuery.setParameter("user", user);
                break;
        }
        try {
            news = newsQuery.getSingleResult();
            if (news == null) {
                return;
            }
            news.setViewed(viewed);
        }
        catch(Exception ex) {
            
        }
    }
    
    public void updateNewsDate(String userNickname, Integer commentsContainerId, 
            CommentsFacade.CommentToType type) 
    {
        if (userNickname == null || commentsContainerId == null) {
            return;
        }
        Users user = userFacade.getUserWithNickname(userNickname);
        if (user == null) {
            return;
        }
        UserCommentsNews news = null;
        TypedQuery<UserCommentsNews> newsQuery = null;
        switch(type) {
            case COMICS:
                Comics comics = comicsFacade.find(commentsContainerId);
                if (comics == null) {
                    return;
                }
                newsQuery = em.createNamedQuery("Comics.getCommentNewsForUserAndComics", 
                                UserCommentsNews.class);
                newsQuery.setParameter("comics", comics);
                newsQuery.setParameter("user", user);
                break;
                
            case ISSUE:
                Issue issue = issueFacade.find(commentsContainerId);
                if (issue == null) {
                    return;
                }
                newsQuery = em.createNamedQuery("Issue.getCommentNewsForUserAndIssue", 
                                UserCommentsNews.class);
                newsQuery.setParameter("issue", issue);
                newsQuery.setParameter("user", user);
                break;
                
            case VOLUME:
                Volume volume = volumeFacade.find(commentsContainerId);
                if (volume == null) {
                    return;
                }
                newsQuery = em.createNamedQuery("Volume.getCommentNewsForUserAndVolume", 
                                UserCommentsNews.class);
                newsQuery.setParameter("volume", volume);
                newsQuery.setParameter("user", user);
                break;
        }
        try {
            news = newsQuery.getSingleResult();
            if (news == null) {
                return;
            }
            news.setLastSeen(new Date(System.currentTimeMillis()));
        }
        catch(Exception ex) {
            
        }
    }
}
