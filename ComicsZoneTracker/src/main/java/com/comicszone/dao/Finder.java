/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

//import com.netcracker.entity.Comics;
import com.comicszone.entity.Content;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ArsenyPC
 */
//@Stateless
@Local
//@Remote
//@PostConstruct
public interface Finder {
    public List<? extends Content> findByNameStartsWith(String name);
    public Content find (Object id);
}
