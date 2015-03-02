/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.tracking;

import com.comicszone.dao.IssueFacade;
import com.comicszone.entity.Issue;
import com.comicszone.entity.Users;

/**
 *
 * @author GuronPavorro
 */
public interface TrackingInterface {
    public void markAsRead(Users user, Issue issueToMark);
    public void unMark(Users user, Issue issueToUnmark);
    public IssueFacade getIssueFacade();
}
