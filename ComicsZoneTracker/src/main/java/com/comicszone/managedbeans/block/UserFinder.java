/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.block;

import com.comicszone.dao.userdao.UserBlockDao;
import com.comicszone.entitynetbeans.Users;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author alexander
 */
@ManagedBean(name = "userFinder", eager = true)
@ApplicationScoped
public class UserFinder implements Serializable {
    
    @EJB
    private UserBlockDao userBlockDao;
    
    public Users find(Integer id) {
        return userBlockDao.find(id);
    }
}
