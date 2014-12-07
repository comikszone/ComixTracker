/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@RequestScoped
public class ComicsView {
    @EJB
    private ComicsFacade comicsFacade;

    @EJB
    private CharacterFacade characterFacade;
    /**
     * Creates a new instance of ComicsView
     */
    private Comics comics;
    private Finder finder; 
    public ComicsView() {
        this.comics=new Comics();
    }

    public Comics getComics() {
        return comics;
    }
     public int getNumberOfComics(){
         finder=characterFacade;
       return finder.findByNameStartsWith("MOI").get(0).getId();
    }
}
