package com.comicszone.dao.commentsdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.userdao.UserBlockDao;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Comments;
import com.comicszone.entitynetbeans.CommentsContainer;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.entitynetbeans.Volume;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alexander
 */
@Stateless
public class CommentsDao extends AbstractFacade<Comments> {
    
    @EJB
    private UserBlockDao userDao;
    
    @EJB
    ComicsFacade comicsFacade;
    
    public CommentsDao() {
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
                break;
                
            case VOLUME:
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
}