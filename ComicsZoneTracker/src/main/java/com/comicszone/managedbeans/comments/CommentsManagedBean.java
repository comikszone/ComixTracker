package com.comicszone.managedbeans.comments;

import com.comicszone.dao.commentsdao.CommentsDao;
import com.comicszone.dao.commentsdao.CommentsDao.CommentToType;
import com.comicszone.entitynetbeans.Comments;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import org.primefaces.component.inputtextarea.InputTextarea;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class CommentsManagedBean {
    @EJB
    CommentsDao commentsDao;
    
    private String currentUserNickname;
    private List<Comments> comments;
    private Integer id;
    private CommentToType commentToType;
    private String selectedCommentText;
    private Comments editingComment;
    private String redirect;
    private static final String WRONG_COMMENT = "Your comment is too short.";
    private static final String NOT_AUTHORIZED = "Please authorize to perform action.";
    private static final String WRONG_USER = "You cannot perform this action.";
    private static final String ACCESS_ERROR = "Access error.";
    private static final String CONTENT_ERROR = "Content error";
    private FacesMessage facesMessageWrongComment;
    private FacesMessage facesMessageNotAuthorized;
    private FacesMessage facesMessageWrongUser;
    
    public void addComment() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        setCurrentUserNickname(externalContext);
        if (currentUserNickname == null) {
            facesContext.addMessage(null, facesMessageNotAuthorized);
            return;
        }
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        InputTextarea inputText = null;
        inputText = (InputTextarea) uiViewRoot.
                findComponent("commentAdderForm:commentAdderNewComment");
        String comment = (String)inputText.getValue();
        if (comment == null || comment.isEmpty()) {
            facesContext.addMessage(null, facesMessageWrongComment);
            return;
        }
        boolean added = commentsDao.addComment(comment, 
                currentUserNickname, commentToType, id);
        if (added) {
            inputText.setValue("");
            comments = commentsDao.getCommentsTo(id, commentToType);
        }
    }
    
    public void editComment() {
        FacesContext facesContext = FacesContext.getCurrentInstance(); 
        ExternalContext externalContext = facesContext.getExternalContext();
        setCurrentUserNickname(externalContext);
        if (currentUserNickname == null) 
        {
            facesContext.addMessage(null, facesMessageNotAuthorized);
            editingComment = null;
            selectedCommentText = null;
            return;
        }
        if (!currentUserNickname.equals(editingComment.getUserId().getNickname()) &&
                !externalContext.isUserInRole("admin")) 
        {
            facesContext.addMessage(null, facesMessageWrongUser);
            editingComment = null;
            selectedCommentText = null;
            return;
        }
        //Map<String, String> params = externalContext.getRequestParameterMap();
        //String editedText = params.get("editForm:editedComment");
        if (selectedCommentText == null || selectedCommentText.isEmpty()) {
            facesContext.addMessage(null, facesMessageWrongComment);
            editingComment = null;
            selectedCommentText = null;
            return;
        }
        boolean edited = commentsDao.editComment(editingComment.getCommentId(), 
                selectedCommentText, id, commentToType);
        editingComment = null;
        selectedCommentText = null;
        if (edited) {
            comments = commentsDao.getCommentsTo(id, commentToType);
        }
    }
    
    public void deleteComment(Comments comment) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        setCurrentUserNickname(externalContext);
        if (currentUserNickname == null) {
            facesContext.addMessage(null, facesMessageNotAuthorized);
            return;
        }
        if (!comment.getUserId().getNickname().equals(currentUserNickname) &&
                !externalContext.isUserInRole("admin")) 
        {
            facesContext.addMessage(null, facesMessageWrongUser);
            return;
        }
        commentsDao.deleteComment(comment.getCommentId(), id, commentToType);
        comments = commentsDao.getCommentsTo(id, commentToType);
    }
    
    public List<Comments> getComments() {
        return comments;
    }
    
    @PostConstruct
    private void init() {
        facesMessageWrongComment = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                CONTENT_ERROR, WRONG_COMMENT);
        facesMessageNotAuthorized = new FacesMessage(FacesMessage.SEVERITY_INFO,
                ACCESS_ERROR, NOT_AUTHORIZED);
        facesMessageWrongUser = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                ACCESS_ERROR, WRONG_USER);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        //identify user
        setCurrentUserNickname(externalContext);
        //get id of comics, volume or issue
        Map<String, String> parameterMap = externalContext.getRequestParameterMap();
        String idParameter = parameterMap.get("id");
        id = Integer.parseInt(idParameter);
        //comics, volume or issue?
        FaceletContext faceletContext = (FaceletContext) facesContext.
                getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
        String type = (String)faceletContext.getAttribute("type");
        setCommentToType(type);
        comments = commentsDao.getCommentsTo(id, commentToType);
    }
    
    public boolean isCommentEditable(Comments comment) {
        return comment.getUserId().getNickname().equals(currentUserNickname) ||
                FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin");
    } 
    
    private void setCommentToType(String type) {
        final String comics = "comics", volume = "volume", issue = "issue";
        if (type.equals(issue)) {
            commentToType = CommentToType.ISSUE;
        }
        else if (type.equals(volume)) {
            commentToType = CommentToType.VOLUME;
        }
        else if (type.equals(comics)) {
            commentToType = CommentToType.COMICS;
        }
    }
    
    public void editionStarted(Comments comment) {
        editingComment = comment;
    }
    
    private void setCurrentUserNickname(ExternalContext externalContext) {
        if (!externalContext.isUserInRole("user")) {
            currentUserNickname = null;
            return;
        }
        String nick = externalContext.getRemoteUser();
        if (!nick.equals(currentUserNickname)) {
            currentUserNickname = nick;
        }
    }
    
    public String getSelectedCommentText() {
        return selectedCommentText;
    }
    
    public void setSelectedCommentText(String selectedCommentText) {
        this.selectedCommentText = selectedCommentText;
    }
}
