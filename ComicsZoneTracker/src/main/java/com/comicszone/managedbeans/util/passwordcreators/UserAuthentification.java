package com.comicszone.managedbeans.util.passwordcreators;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class UserAuthentification {
    static public void authUser(String nickname, String password, HttpServletRequest request) throws ServletException{
        request.login(nickname, password);
    }
}
