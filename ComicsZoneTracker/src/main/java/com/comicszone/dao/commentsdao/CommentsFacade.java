package com.comicszone.dao.commentsdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.CommentsNewsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.userdao.UserBlockFacade;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Comments;
import com.comicszone.entitynetbeans.CommentsContainer;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.UserCommentsNews;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.entitynetbeans.Volume;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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
    
    @EJB
    private CommentsNewsFacade newsFacade;
    
    public CommentsFacade() {
        super(Comments.class);
    }
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @DELETE
    @Path("/delete/commentId/{id}")
    public Response deleteComment(@PathParam("id") Integer id, 
            @Context SecurityContext context) 
    {
        if (!context.isUserInRole("user")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String username = context.getUserPrincipal().getName();
        Comments comment = find(id);
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Users user = comment.getUserId();
        
        if (!comment.getUserId().getNickname().equals(username) && 
                !context.isUserInRole("admin")) 
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        CommentsContainer container = null;
        if (comment.getComicsId() != null) {
            container = comment.getComicsId();
        }
        else if (comment.getIssueId() != null) {
            container = comment.getIssueId();
        }
        else if (comment.getVolumeId() != null) {
            container = comment.getVolumeId();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        remove(comment);
        user.getCommentsList().remove(comment);
        container.getCommentsList().remove(comment);
        newsFacade.setViewed(container.getCommentsNews().
                get(container.getCommentsNews().size() - 1).getId(), Boolean.TRUE);
        return Response.status(Response.Status.OK).build();
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
        comment.getUserId().getCommentsList().remove(comment);
        newsFacade.setViewed(commentsContainer.getCommentsNews().
                get(commentsContainer.getCommentsNews().size() - 1).getId(), Boolean.TRUE);
        return true;
    }
    
    @PUT
    @Path("/add/{commentsContainerType}/id/{id}")
    @Consumes("application/json")
    public Response addComment(Comments newComment, 
            @PathParam("commentsContainerType") String containerType, 
            @PathParam("id") Integer id,
            @Context SecurityContext context) 
    {
        if (!context.isUserInRole("user")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String username = context.getUserPrincipal().getName();
        Users user = userDao.getUserWithNickname(username);
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        String type = containerType.toLowerCase();
        CommentsContainer container = null;
        if ("comics".equals(type)) {
            container = comicsFacade.find(id);
            if (container == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            newComment.setComicsId((Comics)container);
        }
        else if ("issue".equals(type)) {
            container = issueFacade.find(id);
            if (container == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            newComment.setIssueId((Issue)container);
        }
        else if ("volume".equals(type)) {
            container = volumeFacade.find(id);
            if (container == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            newComment.setVolumeId((Volume)container);
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        newComment.setUserId(user);
        newComment.setCommentTime(new Date(System.currentTimeMillis()));
        create(newComment);
        container.getCommentsList().add(newComment);
        user.getCommentsList().add(newComment);
        //for news
        List<UserCommentsNews> news = container.getCommentsNews();
        int newsSize = news.size();
        Boolean exists = Boolean.FALSE;
        for (int i = 0; i < newsSize; i++) {
            UserCommentsNews currentNews = news.get(i);
            if (!currentNews.getUserId().equals(user)) {
                newsFacade.setViewed(currentNews.getId(), Boolean.FALSE);
            }
            else {
                exists = Boolean.TRUE;
                newsFacade.setViewed(currentNews.getId(), Boolean.TRUE);
                newsFacade.updateNewsDate(currentNews.getId());
            }
        }
        if (!exists) {
            UserCommentsNews newNews = newsFacade.addNews(container, 
                    user, Boolean.TRUE);
            news.add(newNews);
            user.getCommentsNews().add(newNews);
        }
        return Response.status(Response.Status.OK).build();
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
        author.getCommentsList().add(newComment);
        //news
        List<UserCommentsNews> news = commentsContainer.getCommentsNews();
        int newsSize = news.size();
        Boolean exists = Boolean.FALSE;
        for (int i = 0; i < newsSize; i++) {
            UserCommentsNews currentNews = news.get(i);
            if (!currentNews.getUserId().equals(author)) {
                newsFacade.setViewed(currentNews.getId(), Boolean.FALSE);
            }
            else {
                exists = Boolean.TRUE;
                newsFacade.setViewed(currentNews.getId(), Boolean.TRUE);
                newsFacade.updateNewsDate(currentNews.getId());
            }
        }
        if (!exists) {
            UserCommentsNews newNews = newsFacade.addNews(commentsContainer, 
                    author, Boolean.TRUE);
            news.add(newNews);
            author.getCommentsNews().add(newNews);
        }
        return true;
    }
    
    @PUT
    @Path("/edit")
    @Consumes("application/json")
    public Response editComment(Comments commentToEdit, 
            @Context SecurityContext context) 
    {
        if (!context.isUserInRole("user")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String username = context.getUserPrincipal().getName();
        Comments comment = find(commentToEdit.getCommentId());
        if (comment == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Users user = comment.getUserId();
        
        if (!comment.getUserId().getNickname().equals(username) && 
                !context.isUserInRole("admin")) 
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        comment.setText(commentToEdit.getText());
        return Response.status(Response.Status.OK).build();
    }
    
    public boolean editComment(Integer commentId, String newText) 
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
    @Path("/get/{commentsContainerType}/id/{commentsContainerId}")
    public String getCommentsTo(
            @PathParam("commentsContainerType") String type,
            @PathParam("commentsContainerId") String id) 
    {
        String typeLowerCase = type.toLowerCase();
        Integer containerId;
        try {
            containerId = Integer.parseInt(id);
        }
        catch(NumberFormatException ex) {
            return "";
        }
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
        String idAttribute = "commentId", authorAttribute = "author", 
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
    
    private void setContainerId(UserCommentsNews news, 
            CommentsContainer commentsContainer, CommentToType type) 
    {
        switch(type) {
            case COMICS:
                news.setComicsId((Comics)commentsContainer);
                return;
                
            case ISSUE:
                news.setIssueId((Issue)commentsContainer);
                return;
                
            case VOLUME:
                news.setVolumeId((Volume)commentsContainer);
        }
    }
}