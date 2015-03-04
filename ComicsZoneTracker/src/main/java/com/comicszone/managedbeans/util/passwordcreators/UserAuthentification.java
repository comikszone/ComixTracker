package com.comicszone.managedbeans.util.passwordcreators;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserAuthentification {

    static public void authUser(String nickname, String password, HttpServletRequest request) throws ServletException {
        request.login(nickname, password);
    }

    static public void logoutUser(FacesContext context) throws ServletException {
//        context.getExternalContext().invalidateSession();
        ExternalContext extContext = context.getExternalContext();
        extContext.getSessionMap().clear();
//        HttpSession session = ((HttpSession)extContext.getSession(true));
//        session.invalidate();
        ((HttpServletRequest) extContext.getRequest()).logout();
    }
}
