/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao.ratingdao;

import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.Users;

/**
 *
 * @author ajpyatakov
 */
public interface RatingInterface {
    
    public void rateContent(Content content, Users user, Float rating);
       
}
