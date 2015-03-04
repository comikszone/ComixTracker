/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

//import com.netcracker.entity.Comics;
import com.comicszone.entity.NamedImage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ArsenyPC
 */
@Local
public interface Finder {
    public List<? extends NamedImage> findByNameStartsWith(String name);
    public NamedImage find (Object id);
}