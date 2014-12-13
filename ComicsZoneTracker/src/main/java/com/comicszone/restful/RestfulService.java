/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.mainsearch.autocomplete;

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.AjaxComicsCharacter;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Character;
import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author ArsenyPC
 */
@Path("/search")
@Produces({"text/xml", "application/json"})
@Stateless
public class RestfulService {
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private CharacterFacade characterFacade;
//    private static Logger logger;
//    @EJB
//    private ComicsFacade comicsFacade;

    
    @Path("/comics/{name}")
    @GET
    @Produces("application/json")
    public List<Comics> getComics(@PathParam("name") String name) {
//        logger.info(comicsFacade.toString());
        return comicsFacade.findByNameStartsWith(name);
    }
    @Path("/comics")
    @GET
    @Produces("application/json")
    public List<Comics> selectAllComics() {
    return comicsFacade.findAll();
    }
    
    @Path("/characters/{name}")
    @GET
    @Produces("application/json")
    public List<Character> getCharacters(@PathParam("name") String name) {
//        logger.info(comicsFacade.toString());
        return characterFacade.findByNameStartsWith(name);
    }
    @Path("/characters")
    @GET
    @Produces("application/json")
    public List<Character> selectAllCharacters() {
    return characterFacade.findAll();
    }
}
