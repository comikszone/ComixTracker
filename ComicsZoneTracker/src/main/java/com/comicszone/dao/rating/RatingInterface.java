/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.rating;

import com.comicszone.entity.Content;
import com.comicszone.entity.ContentType;
import com.comicszone.entity.Users;

/**
 *
 * @author ajpyatakov
 */
public interface RatingInterface {
    
    public void init(Integer userId, Integer contentId, ContentType type);
    public void rateContent(Content content, Users user, Float rating);
       
}
