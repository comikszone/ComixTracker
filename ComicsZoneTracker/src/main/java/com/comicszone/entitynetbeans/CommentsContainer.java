/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.util.List;

/**
 *
 * @author alexander
 */
public interface CommentsContainer {
    public List<Comments> getCommentsList();
    public void setCommentsList(List<Comments> commentsList);
}
