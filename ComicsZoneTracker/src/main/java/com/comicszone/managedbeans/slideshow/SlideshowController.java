///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.comicszone.managedbeans.slideshow;
//
//import com.comicszone.dao.ComicsDaoImpl;
//import com.comicszone.dao.SlideshowFacade;
//import java.io.Serializable;
//import java.util.List;
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.RequestScoped;
//import javax.faces.bean.SessionScoped;
//import javax.faces.bean.ViewScoped;
//import com.comicszone.entitynetbeans.Comics;
//import javax.ejb.EJB;
//
///**
// * 
// * @author Alexander Pyatakov
// */
//
//@ManagedBean(name="slideshowController")
//@ViewScoped
//public class SlideshowController implements Serializable{
//    
//    private List<Comics> comicsList;
//    private SlideshowFacade slideshowFacade;
//    
//    public SlideshowController() {
//    }
//        
//    @PostConstruct
//    public void fillComicsList() {
//       slideshowFacade = new ComicsDaoImpl();
//       comicsList = slideshowFacade.get12Best();
//    }
//    
//    public List<Comics> getComicsList() {
//        return comicsList;
//    }
//
//}
