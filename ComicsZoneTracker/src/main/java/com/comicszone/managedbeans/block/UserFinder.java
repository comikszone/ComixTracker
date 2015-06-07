/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.block;

import com.comicszone.dao.user.UserBlockFacade;
import com.comicszone.entity.Users;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;

/**
 *
 * @author alexander
 */
@Named("userFinder")
@Eager
@ApplicationScoped
public class UserFinder implements Serializable {
    
    @EJB
    private UserBlockFacade userBlockDao;
    
    public Users find(Integer id) {
        return userBlockDao.find(id);
    }
}
