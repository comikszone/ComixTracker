/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Comics;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean(name="comicsController")
@SessionScoped
public class ComicsController {
//        private static final Logger logger;

//    static
//    {
//        FileHandler handler= null;
//        try
//        {
//            handler = new FileHandler("c:\\Users\\Арсений\\Documents\\Учеба\\NetCracker\\PROJECT\\ComixTracker\\ComicsZoneTracker\\log\\logging.log");
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        logger=Logger.getLogger(ComicsController.class.getName());
//        logger.addHandler(handler);
//        SimpleFormatter formatter=new SimpleFormatter();
//        handler.setFormatter(formatter);
//    }
    @EJB
    private ComicsFacade comicsFacade;

//    @PostConstruct
//    public void init()
//    {
//        logger.info("init()");
//        comicsFacade=new ComicsFacade();
//    }
//    
    private Comics comics;
    private Integer comicsId;

    public Integer getComicsId() {
//        logger.info("getComicsId"+comicsId);
        return comicsId;
    }

    public void setComicsId(Integer comicsId) {
//        logger.info("setComicsId"+comicsId);
        this.comicsId = comicsId;
    }
    public void initComics()
    {
//        logger.info("initComics()");
        comics=comicsFacade.find(comicsId);
//        return comics;
    }

    public Comics getComics() {
//        logger.info("getComics"+comics.toString());
        return comics;
    }

    public void setComics(Comics comics) {
//        logger.info("setComics"+comics.toString());
        this.comics = comics;
    }
    
    
}
