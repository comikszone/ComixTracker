/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.webservice;

import com.comicszone.dao.CharacterFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.entitynetbeans.AjaxComicsCharacter;
import com.comicszone.entitynetbeans.Comics;
import com.comicszone.entitynetbeans.Character;
import com.comicszone.entitynetbeans.Imprint;
import com.comicszone.entitynetbeans.Issue;
import com.comicszone.entitynetbeans.Publisher;
import com.comicszone.entitynetbeans.Realm;
import com.comicszone.entitynetbeans.Volume;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

/**
 *
 * @author ArsenyPC
 */
@Stateless
@LocalBean
@Path("/search")
//@Produces({"text/xml", "application/json"})
public class JsonProducer {
    @EJB
    private ComicsFacade comicsFacade;
    @EJB
    private VolumeFacade volumeFacade;
    @EJB
    private IssueFacade issueFacade;
    @EJB
    private CharacterFacade characterFacade;
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    private String getJsonListAjaxComicsCharacter(List<? extends AjaxComicsCharacter> listAj)
    {
        JSONArray array=new JSONArray();
        for (AjaxComicsCharacter aj: listAj)
        {
            Map map=new HashMap();
            map.put("id", aj.getId());
            map.put("name", aj.getName());
            array.add(map);
        }
        return array.toJSONString();
    }
    
    @Path("/comics/name/{name}")
    @GET
//    @Produces("application/json")
    public String getJsonComicsStartsWith(@PathParam("name") String name) {
        TypedQuery<Comics> query =em.createNamedQuery("Comics.findByNameStartsWith", Comics.class);
        query.setParameter("name", name.toLowerCase()+"%");
        List<Comics> comicsList= query.getResultList();
        return getJsonListAjaxComicsCharacter(comicsList);
    }
    
    @Path("/comics")
    @GET
    @Produces("application/json")
    public String getJsonAllIdNameComics() {
        List<Comics> comicsList=comicsFacade.findAll();
        return getJsonListAjaxComicsCharacter(comicsList);
    }
    
    @Path("/comics/id/{id}")
    @GET
//    @Produces("application/json")
    public String getJsonComicsById(@PathParam("id") String id) throws IOException {
        Comics comics=comicsFacade.find(Integer.parseInt(id));
        if (comics==null)
            return "";
        Map map=new HashMap();
        List<Volume> volumes=comics.getVolumeList();
        JSONArray array=new JSONArray();
        for (Volume v:volumes)
        {
            Map mapVolume=new HashMap();
            mapVolume.put("volumeId", v.getId());
            mapVolume.put("name", v.getName());
            array.add(mapVolume);
        }
        Publisher publisher=comics.getPublisherId();
        Map mapPublisher=new HashMap();
        mapPublisher.put("publisherId", publisher.getPublisherId());
        mapPublisher.put("name", publisher.getName());
        Imprint imprint=comics.getImprintId();
        Map mapImprint=new HashMap();
        mapImprint.put("imprintId", imprint.getImprintId());
        mapImprint.put("name", imprint.getName());
        StringWriter out = new StringWriter();
        map.put("id", comics.getId());
        map.put("name", comics.getName());
        map.put("description", comics.getDescription());
        map.put("startDate", comics.getStartDate());
        map.put("endDate", comics.getEndDate());
        map.put("image", comics.getImage());
        map.put("imprintId", mapImprint);
        map.put("inProgress", comics.getInProgress());
        map.put("publisherId", mapPublisher);
        map.put("rating", comics.getRating());
        map.put("votes", comics.getVotes());
//            for (Field field : comics.getClass().getDeclaredFields()) {
//                field.setAccessible(true);
//                Object value = field.get(comics); 
//                map.put(field.getName(), value);
//            }
        map.put("volumeList", array);
        JSONValue.writeJSONString(map, out);
        return out.toString();
   }

    @Path("/volume/id/{id}")
    @GET
//    @Produces("application/json")
    public String getJsonVolumeById(@PathParam("id") String id) throws IOException {
        Volume volume=volumeFacade.find(Integer.parseInt(id));
        if (volume==null)
            return "";
        Map map=new HashMap();
        List<Issue> issues=volume.getIssueList();
        JSONArray array=new JSONArray();
        for (Issue issue:issues)
        {
            Map mapIssue=new HashMap();
            mapIssue.put("issueId", issue.getId());
            mapIssue.put("name", issue.getName());
            array.add(mapIssue);
        }
        Comics comics=volume.getComicsId();
        Map mapComics=new HashMap();
        mapComics.put("comicsId", comics.getId());
        mapComics.put("name", comics.getName());
        StringWriter out = new StringWriter();
        map.put("volumeId", volume.getId());
        map.put("description", volume.getDescription());
        map.put("img", volume.getImage());
        map.put("name", volume.getName());
        map.put("votes", volume.getVotes());
        map.put("rating", volume.getRating());
        map.put("comicsId", mapComics);
        map.put("issueList", array);
        JSONValue.writeJSONString(map, out);
        return out.toString();
   }
   
    @Path("/issue/id/{id}")
    @GET
//    @Produces("application/json")
    public String getJsonIssueById(@PathParam("id") String id) throws IOException {
            Issue issue=issueFacade.find(Integer.parseInt(id));
            if (issue==null)
                return "";
            Map map=new HashMap();
            Volume volume=issue.getVolumeId();
            Map mapVolume=new HashMap();
            mapVolume.put("volumeId", volume.getId());
            mapVolume.put("name", volume.getName());
            StringWriter out = new StringWriter();
            map.put("issueId", issue.getId());
            map.put("description", issue.getDescription());
            map.put("img", issue.getImage());
            map.put("name", issue.getName());
            map.put("rating", issue.getRating());
            map.put("relDate", issue.getRelDate());
            map.put("votes", issue.getVotes());
            map.put("volumeId", mapVolume);
            JSONValue.writeJSONString(map, out);
            return out.toString();
       }
    
    @Path("/characters")
    @GET
//    @Produces("application/json")
    public String getJsonAllIdNameCharacters(){
        List<Character> characters=characterFacade.findAll();
        return getJsonListAjaxComicsCharacter(characters);
    }
    
    @Path("/characters/name/{name}")
    @GET
//    @Produces("application/json")
    public String getJsonCharactersStartsWith(@PathParam("name") String name) {
        TypedQuery<Character> query =em.createNamedQuery("Character.findByNameStartsWith",Character.class);
        query.setParameter("name", name.toLowerCase()+"%");
        List<Character> comicsList= query.getResultList();
        return getJsonListAjaxComicsCharacter(comicsList);
    }
    
    @Path("/characters/id/{id}")
    @GET
//    @Produces("application/json")
    public String getJsonCharactersById(@PathParam("id") String id) throws IOException {
            Character character=characterFacade.find(Integer.parseInt(id));
            if (character==null)
                return "";
            List<Issue> issues=character.getIssueList();
            JSONArray array=new JSONArray();
            for (Issue issue:issues)
            {
                Map mapIssue=new HashMap();
                mapIssue.put("issueId", issue.getId());
                mapIssue.put("name", issue.getName());
                array.add(mapIssue);
            }
            Publisher publisher=character.getPublisherId();
            Map mapPublisher=new HashMap();
            mapPublisher.put("publisherId", publisher.getPublisherId());
            mapPublisher.put("name", publisher.getName());
            Realm realm=character.getRealmId();
            Map mapRealm=new HashMap();
            mapRealm.put("realmId", realm.getRealmId());
            mapRealm.put("name", realm.getName());
            StringWriter out = new StringWriter();
            Map map=new HashMap();
            map.put("id", character.getId());
            map.put("description", character.getDescription());
            map.put("image", character.getImage());
            map.put("name", character.getName());
            map.put("realName", character.getRealName());
            map.put("publisherId", mapPublisher);
            map.put("realmId", mapRealm);
            map.put("issueList", array);
            JSONValue.writeJSONString(map, out);
            return out.toString();
       }
    
}
