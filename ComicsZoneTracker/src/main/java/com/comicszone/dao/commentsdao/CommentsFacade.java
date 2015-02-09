package com.comicszone.dao.commentsdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.userdao.UserBlockFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Comments;
import com.comicszone.entitynetbeans.CommentsContainer;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.entitynetbeans.Volume;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import org.json.simple.JSONArray;

/**
 *
 * @author alexander
 */
@Stateless
@Path("/comments")
public class CommentsFacade extends AbstractFacade<Comments> {
    
    @EJB
    private UserBlockFacade userDao;
    
    @EJB
    private ComicsFacade comicsFacade;
    
    @EJB
    private IssueFacade issueFacade;
    
    @EJB
    private VolumeFacade volumeFacade;
    
    public CommentsFacade() {
        super(Comments.class);
    }
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public boolean deleteComment(Integer commentId, Integer commentToId, 
            CommentToType type) 
    {
        Comments comment = find(commentId);
        if (comment == null) {
            return false;
        }
        remove(comment);
        CommentsContainer commentsContainer = 
                findCommentsContainer(commentToId, type);
        commentsContainer.getCommentsList().remove(comment);
        return true;
    }
    
    public boolean addComment(String commentText, String userNickname, 
            CommentToType type, Integer commentToId) {
        
        if (commentText == null || commentText.isEmpty() 
                || userNickname == null || userNickname.isEmpty()) {
            return false;
        }
        
        Users author = userDao.getUserWithNickname(userNickname);
        if (author == null) {
            return false;
        }
        
        CommentsContainer commentsContainer = 
                findCommentsContainer(commentToId, type);
        if (commentsContainer == null) {
            return false;
        }
        
        Comments newComment = new Comments();
        setContainerId(newComment, commentsContainer, type);
        newComment.setUserId(author);
        newComment.setText(commentText);
        newComment.setCommentTime(new Date(System.currentTimeMillis()));
        create(newComment);
        commentsContainer.getCommentsList().add(newComment);
        return true;
    }
    
    public boolean editComment(Integer commentId, String newText,
            Integer commentToId, CommentToType type) 
    {
        if (newText == null || newText.isEmpty()) {
            return false;
        }
        Comments comment = find(commentId);
        if (comment == null) {
            return false;
        }
        comment.setText(newText);
        return true;
    }
    
    @GET
    @Path("/{commentsContainerType}/id/{commentsContainerId}")
    public String getCommentsTo(
            @PathParam("commentsContainerType") String type,
            @PathParam("commentsContainerId") String id) 
    {
        String typeLowerCase = type.toLowerCase();
        Integer containerId = Integer.parseInt(id);
        List<Comments> comments = null;
        if (typeLowerCase.equals("comics")) {
            comments = getCommentsTo(containerId, CommentToType.COMICS);
        }
        else if (typeLowerCase.equals("issue")) {
            comments = getCommentsTo(containerId, CommentToType.ISSUE);
        }
        else if (typeLowerCase.equals("volume")) {
            comments = getCommentsTo(containerId, CommentToType.VOLUME);
        }
        if (comments == null) {
            return "";
        }
        String idAttribute = "id", authorAttribute = "author", 
                textAttribute = "text", timeAttribute = "time";
        JSONArray json = new JSONArray();
        for (Comments comment : comments) {
            Map mapForJSON = new HashMap();
            mapForJSON.put(idAttribute, comment.getCommentId());
            mapForJSON.put(authorAttribute, comment.getUserId().getUserId());
            mapForJSON.put(textAttribute, comment.getText());
            mapForJSON.put(timeAttribute, comment.getCommentTime());
            json.add(mapForJSON);
        }
        return json.toJSONString();
    }
    
    public List<Comments> getCommentsTo(Integer id, CommentToType type) {
        CommentsContainer commentsContainer = findCommentsContainer(id, type);
        if (commentsContainer == null) {
            return null;
        }
        return commentsContainer.getCommentsList();
    }
    
    public enum CommentToType {
        COMICS, ISSUE, VOLUME
    }
    
    private CommentsContainer findCommentsContainer(Integer id, CommentToType type) {
        CommentsContainer commentsContainer = null;
        switch(type) {
            case COMICS:
                commentsContainer = comicsFacade.find(id);
                break;
                
            case ISSUE:
                commentsContainer = issueFacade.find(id);
                break;
                
            case VOLUME:
                commentsContainer = volumeFacade.find(id);
                break;
        }
        return commentsContainer;
    }
    
    private void setContainerId(Comments comment, 
            CommentsContainer commentsContainer, CommentToType type) 
    {
        switch(type) {
            case COMICS:
                comment.setComicsId((Comics)commentsContainer);
                return;
                
            case ISSUE:
                comment.setIssueId((Issue)commentsContainer);
                return;
                
            case VOLUME:
                comment.setVolumeId((Volume)commentsContainer);
        }
    }
    
    public List<List<?>> getCommentsContainersToUser
        (String userNickname) 
    {
        Users user = userDao.getUserWithNickname(userNickname);
        if (user == null) {
            return null;
        }
        
        //Comics
        TypedQuery<Comics> newComicsQuery 
                = em.createNamedQuery("Comics.getComicsWithNewCommentsAfterUser", Comics.class);
        newComicsQuery.setParameter("userId", user);
        List<Comics> newComics = newComicsQuery.getResultList();
        TypedQuery<Date> lastComicsDates = 
                em.createNamedQuery("Comics.getMaxCommentDateForUser", Date.class);
        lastComicsDates.setParameter("userId", user);
        TypedQuery<Long> newCommentsToComicsNumberQuery = 
                em.createNamedQuery("Comics.getCountOfNewCommentsForUser", Long.class);
        newCommentsToComicsNumberQuery.setParameter("userId", user);
        TypedQuery<Comments> newCommentsToComicsQuery = 
                em.createNamedQuery("Comics.getCommentsAfterDateToComics", Comments.class);
        
        //Issues
        TypedQuery<Issue> newIssuesQuery 
                = em.createNamedQuery("Issue.getIssuesWithNewCommentsAfterUser", Issue.class);
        newIssuesQuery.setParameter("userId", user);
        List<Issue> newIssues = newIssuesQuery.getResultList();
        TypedQuery<Date> lastIssuesDates = 
                em.createNamedQuery("Issue.getMaxCommentDateForUser", Date.class);
        lastIssuesDates.setParameter("userId", user);
        TypedQuery<Long> newCommentsToIssuesNumberQuery = 
                em.createNamedQuery("Issue.getCountOfNewCommentsForUser", Long.class);
        newCommentsToIssuesNumberQuery.setParameter("userId", user);
        TypedQuery<Comments> newCommentsToIssueQuery = 
                em.createNamedQuery("Issue.getCommentsAfterDateToIssue", Comments.class);
        
        //Volumes
        TypedQuery<Volume> newVolumesQuery 
                = em.createNamedQuery("Volume.getVolumesWithNewCommentsAfterUser", Volume.class);
        newVolumesQuery.setParameter("userId", user);
        List<Volume> newVolumes = newVolumesQuery.getResultList();
        TypedQuery<Date> lastVolumesDates = 
                em.createNamedQuery("Volume.getMaxCommentDateForUser", Date.class);
        lastVolumesDates.setParameter("userId", user);
        TypedQuery<Long> newCommentsToVolumesNumberQuery = 
                em.createNamedQuery("Volume.getCountOfNewCommentsForUser", Long.class);
        newCommentsToVolumesNumberQuery.setParameter("userId", user);
        TypedQuery<Comments> newCommentsToVolumeQuery = 
                em.createNamedQuery("Volume.getCommentsAfterDateToVolume", Comments.class);
        
        //preparing for getting results
        List<List<?>> result = new ArrayList<List<?>>(4);
        int capacity = newComics.size() + newIssues.size() + newVolumes.size();
        List<CommentsContainer> containers = new ArrayList<CommentsContainer>(capacity);
        List<Long> numberOfNewComments = new ArrayList<Long>(capacity);
        List<Date> dateOfLastUserComment = new ArrayList<Date>(capacity);
        List<List<Comments>> newComments = new ArrayList<List<Comments>>(capacity);
        
        //getting results
        for (Comics comics : newComics) {
            lastComicsDates.setParameter("Id", comics.getId());
            Date lastDate = lastComicsDates.getSingleResult();
            newCommentsToComicsNumberQuery.setParameter("Id", comics.getId());
            newCommentsToComicsQuery.setParameter("Id", comics.getId());
            newCommentsToComicsQuery.setParameter("date", lastDate);
            int i = 0, lastElement = containers.size() - 1;
            if (lastElement == -1 || lastDate.after(dateOfLastUserComment.get(0))) {
                containers.add(0, comics);
                numberOfNewComments.add(0, newCommentsToComicsNumberQuery.getSingleResult());
                dateOfLastUserComment.add(0, lastDate);
                newComments.add(0, newCommentsToComicsQuery.getResultList());
                continue;
            }
            if (lastDate.before(dateOfLastUserComment.get(lastElement))) {
                containers.add(comics);
                numberOfNewComments.add(newCommentsToComicsNumberQuery.getSingleResult());
                dateOfLastUserComment.add(lastDate);
                newComments.add(newCommentsToComicsQuery.getResultList());
                continue;
            }
            for ( ; i < lastElement; i++) {
                if (lastDate.before(dateOfLastUserComment.get(i)) && 
                        lastDate.after(dateOfLastUserComment.get(i + 1))) {
                    containers.add(i + 1, comics);
                    numberOfNewComments.add(i + 1, newCommentsToComicsNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(i + 1, lastDate);
                    newComments.add(i + 1, newCommentsToComicsQuery.getResultList());
                    break;
                }
            }
        }
        
        for (Issue issue : newIssues) {
            lastIssuesDates.setParameter("Id", issue.getId());
            Date lastDate = lastIssuesDates.getSingleResult();
            newCommentsToIssuesNumberQuery.setParameter("Id", issue.getId());
            newCommentsToIssueQuery.setParameter("Id", issue.getId());
            newCommentsToIssueQuery.setParameter("date", lastDate);
            int i = 0, lastElement = containers.size() - 1;
            if (lastElement == -1 || lastDate.after(dateOfLastUserComment.get(0))) {
                containers.add(0, issue);
                numberOfNewComments.add(0, newCommentsToIssuesNumberQuery.getSingleResult());
                dateOfLastUserComment.add(0, lastDate);
                newComments.add(0, newCommentsToIssueQuery.getResultList());
                continue;
            }
            if (lastDate.before(dateOfLastUserComment.get(lastElement))) {
                containers.add(issue);
                numberOfNewComments.add(newCommentsToIssuesNumberQuery.getSingleResult());
                dateOfLastUserComment.add(lastDate);
                newComments.add(newCommentsToIssueQuery.getResultList());
                continue;
            }
            for ( ; i < lastElement; i++) {
                if (lastDate.before(dateOfLastUserComment.get(i)) && 
                        lastDate.after(dateOfLastUserComment.get(i + 1))) {
                    containers.add(i + 1, issue);
                    numberOfNewComments.add(i + 1, newCommentsToIssuesNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(i + 1, lastDate);
                    newComments.add(i + 1, newCommentsToIssueQuery.getResultList());
                    break;
                }
            }
        }
        
        for (Volume volume : newVolumes) {
            lastVolumesDates.setParameter("Id", volume.getId());
            Date lastDate = lastVolumesDates.getSingleResult();
            newCommentsToVolumesNumberQuery.setParameter("Id", volume.getId());
            newCommentsToVolumeQuery.setParameter("Id", volume.getId());
            newCommentsToVolumeQuery.setParameter("date", lastDate);
            int i = 0, lastElement = containers.size() - 1;
            if (lastElement == -1 || lastDate.after(dateOfLastUserComment.get(0))) {
                containers.add(0, volume);
                numberOfNewComments.add(0, newCommentsToVolumesNumberQuery.getSingleResult());
                dateOfLastUserComment.add(0, lastDate);
                newComments.add(0, newCommentsToVolumeQuery.getResultList());
                continue;
            }
            if (lastDate.before(dateOfLastUserComment.get(lastElement))) {
                containers.add(volume);
                numberOfNewComments.add(newCommentsToVolumesNumberQuery.getSingleResult());
                dateOfLastUserComment.add(lastDate);
                newComments.add(newCommentsToVolumeQuery.getResultList());
                continue;
            }
            for ( ; i < lastElement; i++) {
                if (lastDate.before(dateOfLastUserComment.get(i)) && 
                        lastDate.after(dateOfLastUserComment.get(i + 1))) {
                    containers.add(i + 1, volume);
                    numberOfNewComments.add(i + 1, newCommentsToVolumesNumberQuery.getSingleResult());
                    dateOfLastUserComment.add(i + 1, lastDate);
                    newComments.add(i + 1, newCommentsToVolumeQuery.getResultList());
                    break;
                }
            }
        }
        result.add(containers);
        result.add(numberOfNewComments);
        result.add(dateOfLastUserComment);
        result.add(newComments);
        return result;
    }
}