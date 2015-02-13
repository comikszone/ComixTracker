/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.dao.contentdao.ContentFacade;
import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.ContentType;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Users;
import com.comicszone.entitynetbeans.Volume;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author GuronPavorro
 */
@Stateless
@Path("/reading")
public class ReadingFacade {
    @EJB
    private UserDataFacade userFacade;
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private VolumeFacade volumeFacade;
    @EJB
    private IssueFacade issueFacade;
    @EJB
    private ContentFacade contentFacade;
    
    /**
     * @return the userFacade
     */
    public UserDataFacade getUserFacade() {
        return userFacade;
    }

    /**
     * @param userFacade the userFacade to set
     */
    public void setUserFacade(UserDataFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * @return the issueFacade
     */
    public IssueFacade getIssueFacade() {
        return issueFacade;
    }

    /**
     * @param issueFacade the issueFacade to set
     */
    public void setIssueFacade(IssueFacade issueFacade) {
        this.issueFacade = issueFacade;
    }
        
    public void markAsRead(Users user, Issue issueToMark) {
        user.getIssueList().add(issueToMark);
        getUserFacade().edit(user);
        issueToMark.getUsersList().add(user);
        getIssueFacade().edit(issueToMark);
    }
    
    public void unMark(Users user, Issue issueToUnmark) {
        user.getIssueList().remove(issueToUnmark);
        issueToUnmark.getUsersList().remove(user);
        getUserFacade().edit(user);
        getIssueFacade().edit(issueToUnmark);
    }
    
    @PUT
    @Path("/add/{contentType}/{contentId}")
    public Response readContent(@PathParam("contentType") ContentType contentType,
                                @PathParam("contentId") Integer contentId,
                                @Context SecurityContext context) {
        
        if (!context.isUserInRole("user")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();        
        }
        
        String username = context.getUserPrincipal().getName();
        Users user = userFacade.getUserWithNickname(username);
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
       
        List<Issue> issueList = new ArrayList<Issue>();
        if (contentType.equals(ContentType.Comics)) {
            issueList = issueFacade.findByComics(contentId);
        }
        else if (contentType.equals(ContentType.Volume)) {
                issueList = volumeFacade.find(contentId).getIssueList();               
        }
        else if (contentType.equals(ContentType.Issue)) {
            Issue issue = issueFacade.find(contentId);
            if (issue == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            if (user.getIssueList().contains(issue) && issue.getUsersList().contains(user))
                return Response.status(Response.Status.CONFLICT).build();
            markAsRead(user, issue);
            return Response.status(Response.Status.OK).build();
        } 
        else return Response.status(Response.Status.BAD_REQUEST).build();
        
        if (issueList == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        issueList.removeAll(issueFacade.findMarkedByUserAndComics(contentId, user.getUserId()));
        if (issueList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        else {
            for (Issue issue: issueList) {
                markAsRead(user, issue);
            }
        }
        return Response.status(Response.Status.OK).build(); 
    }
    
    @DELETE
    @Path("/unmark/{contentType}/{contentId}")
    public Response unMarkContent(@PathParam("contentType") ContentType contentType,
                                  @PathParam("contentId") Integer contentId,
                                  @Context SecurityContext context) {
                if (!context.isUserInRole("user")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();        
        }
        
        if (!context.isUserInRole("user")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();        
        }
        
        String username = context.getUserPrincipal().getName();
        Users user = userFacade.getUserWithNickname(username);
        if (user == null) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
       
        List<Issue> issueList = new ArrayList<Issue>();
        if (contentType.equals(ContentType.Comics)) {
            issueList = issueFacade.findByComics(contentId);
        }
        else if (contentType.equals(ContentType.Volume)) {
                issueList = volumeFacade.find(contentId).getIssueList();               
        }
        else if (contentType.equals(ContentType.Issue)) {
            Issue issue = issueFacade.find(contentId);
            if (issue == null)
                return Response.status(Response.Status.NOT_FOUND).build();
            if (user.getIssueList().contains(issue) && issue.getUsersList().contains(user))
                return Response.status(Response.Status.CONFLICT).build();
            unMark(user, issue);
            return Response.status(Response.Status.OK).build();
        } 
        else return Response.status(Response.Status.BAD_REQUEST).build();
        
        if (issueList == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        
        issueList.retainAll(issueFacade.findMarkedByUserAndComics(contentId, user.getUserId()));
        if (issueList.isEmpty())
            return Response.status(Response.Status.NO_CONTENT).build();
        else {
            for (Issue issue: issueList) {
                unMark(user, issue);
            }
        }
        return Response.status(Response.Status.OK).build(); 
    }
}
