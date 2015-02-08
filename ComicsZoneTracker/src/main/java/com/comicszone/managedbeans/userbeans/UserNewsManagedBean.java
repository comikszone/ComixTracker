package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.commentsfacade.CommentsFacade;
import com.comicszone.entitynetbeans.Comments;
import com.comicszone.entitynetbeans.CommentsContainer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class UserNewsManagedBean implements Serializable {
    
    @EJB
    private CommentsFacade commentsFacade;
    
    private List<CommentsContainer> newContainers;
    private List<Long> numberOfNewComments;
    private List<Date> dateOfLastUserComments;
    private List<List<Comments>> newComments;
    private List<Boolean> areCommentsShowing;
    private String userNickname;
    private PeriodFormatter formatter;
    private final static String suffix = " passed between your last comment and "
            + "the last comment. Let's view and comment!";
    
    @PostConstruct
    private void init() {
        setUserNickname();
        setPeriodFormatter();
        update();
    }
    
    public List<CommentsContainer> getNewContainers() {
        return newContainers;
    }
    
    public Long getNumberOfNewComments(int index) {
        return numberOfNewComments.get(index);
    }
    
    public List<Comments> getNewComments(int index) {
        return newComments.get(index);
    }
    
    public String getCommentsDifference(int index) {
        List<Comments> comments = newComments.get(index);
        Period passedTime = new Period(dateOfLastUserComments.get(index).getTime(), 
                comments.get(comments.size() - 1).getCommentTime().getTime());
        return passedTime.toString(formatter);
    }
    
    private void setUserNickname() {
        userNickname = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal().getName();
    }
    
    public String redirect(CommentsContainer container) {
        Integer id = container.getId();
        switch (container.getContentType()) {
            case Comics:
                return "/resources/pages/comicsPage.jsf?faces-redirect=true&id=" + id;
            case Issue:
                return "/resources/pages/issuePage.jsf?faces-redirect=true&id=" + id;
            case Volume:
                return "/resources/pages/volumePage.jsf?faces-redirect=true&id=" + id;
        }
        return "/resources/templates/index.jsf";
    }
    
    public void resetShowingComments(int index) {
        areCommentsShowing.set(index, !areCommentsShowing.get(index));
    }
    
    public Boolean areCommentsShowing(int index) {
        return areCommentsShowing.get(index);
    }
    
    private void update() {
        List<List<?>> result = commentsFacade.getCommentsContainersToUser(userNickname);
        newContainers = (List<CommentsContainer>) result.get(0);
        numberOfNewComments = (List<Long>) result.get(1);
        dateOfLastUserComments = (List<Date>) result.get(2);
        newComments = (List<List<Comments>>) result.get(3);
        int size = newComments.size();
        areCommentsShowing = new ArrayList<Boolean>(size);
        for (int i = 0; i < size; i++) {
            areCommentsShowing.add(Boolean.FALSE);
        }
    }
    
    public String getValueOfViewButton(int index) {
        return areCommentsShowing.get(index) ? "Collapse" : "Show comments";
    }

    private void setPeriodFormatter() {
        formatter = new PeriodFormatterBuilder().
                appendYears().appendSuffix(" year", " years").appendSeparator(", ").
                appendMonths().appendSuffix(" month", " months").appendSeparator(", ").
                appendWeeks(). appendSuffix(" week", " weeks").appendSeparator(", ").
                appendDays().appendSuffix(" day", " days").appendSeparator(", ").
                appendHours().appendSuffix(" hour", " hours").appendSeparator(", ").
                appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(", ").
                appendSeconds().appendSuffix(" second", " seconds").
                appendLiteral(suffix).
                toFormatter();
    }
}
