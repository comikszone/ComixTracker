/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.marvelfetcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import us.monoid.web.Resty;
import org.json.*;

/**
 *
 * @author Phoenix1092
 */
public class fetcher {
    
    public static String parse(JSONArray jArr, int i, String node) { //for getting comics and character title(name), description and other 1-JSON-lvl stuff
        
        try {
            if (node.equals("title") || node.equals("description") || node.equals("name")) 
                return jArr.getJSONObject(i).getString(node).replace("'", "\"");
            else 
                return "null";
        }
        catch(JSONException e) {
            switch (node) {
                case "title": //Who knows? 8]
                    return "Title missing";
                case "description":
                    return "Description missing";
                case "name":
                    return "Name missing";
                default:
                    return "null";
            }
        }
    };
    
    public static String parse(JSONArray jArr, int i, String node, String subNode) { //for thumbnails
        
        try {
            return jArr.getJSONObject(i).getJSONObject(node).getString(subNode).replace("'", "\"");
        }
        catch(JSONException e) {
            switch (subNode) {
                case "path":  
                    return "null";  //Needs to be changed for dummy-picture, when it's ready
                case "extension":
                    return "jpg";
                default:
                    return "null";
            }            
        }
    };
    
    public static String parse(JSONArray jArr, int i, String arrNode, int k, String subArrNode) { //for date
        
        try {
            return jArr.getJSONObject(i).getJSONArray(arrNode).getJSONObject(k).getString(subArrNode).replace("'", "\"");
        }
        catch(JSONException e) {
            return "null";
        }
    };
    
    public static String parse(JSONArray jArr, int i, String node, String array, int k, String subNode) { //for comics list in character JSON
        try{
            return jArr.getJSONObject(i).getJSONObject(node).getJSONArray(array).getJSONObject(k).getString(subNode);
        }
        catch(JSONException e) {
            return "null";
        }        
    };
    
    protected static String request(String entity, int limit, int offset) {   //Receives subdomain name (comics, character, etc), fetch limit and starting number
        
        String publicKey = "527811a07f39623030a8c9f82a753791";          //Swap these for yours if are able, please.
        String privateKey = "8798990b7f0835aa20c193e12057ffcb201ec782";
        long timeStamp = System.currentTimeMillis();    //Some controls
        String orderBy = "title";
        String hash = DigestUtils.md5Hex(timeStamp + privateKey + publicKey);
        return String.format("http://gateway.marvel.com/v1/public/%s?ts=%d&apikey=%s&hash=%s&limit=%d&orderBy=%s&offset=%d",entity, timeStamp, publicKey, hash, limit, orderBy, offset);
    };
    
    public static String toTitle(String s, char c) { //Changes comics name to db view of comics or volume
        
        if (c=='c') {
            if (s.contains("("))
                return StringUtils.substringBefore(s, "(");
            else if (s.contains("#"))
                return StringUtils.substringBefore(s, "#");
            else
                return s;
        }
        else {
            if (s.contains("#"))
                return StringUtils.substringBefore(s, "#");
            else 
                return s;
        }          
    };
    
    public static void fetchComics() throws IOException { //well, it is what it is
        int limit = 2;
        int offset = 0;
        File comics = new File("comics.txt");
        FileWriter comicswrt = new FileWriter(comics,true);
        String comicsResponse = new Resty().text(request("comics",limit,offset)).toString();
        JSONObject comicsObj = new JSONObject(comicsResponse);
        JSONArray comicsArr = comicsObj.getJSONObject("data").getJSONArray("results");
        
        for (int i=0; i<comicsArr.length(); i++) {
            if (parse(comicsArr,i,"title").toLowerCase().contains("poster")) //Some data parsing
                continue;
            else {
                comicswrt.append("INSERT INTO comics (name, description, img, source) SELECT '"+toTitle(parse(comicsArr,i,"title"), 'c')+"', '"+parse(comicsArr,i,"description")+"', '"+parse(comicsArr,i,"thumbnail","path")+"."+parse(comicsArr,i,"thumbnail","extension")+"', 'Marvel' WHERE NOT EXISTS (SELECT name FROM comics WHERE name = '"+toTitle(parse(comicsArr,i,"title"), 'c')+"');\r\n");
                comicswrt.append("INSERT INTO volume (name, description, img, source, comics_id) SELECT '"+toTitle(parse(comicsArr,i,"title"), 'v')+"', '"+parse(comicsArr,i,"description")+"', '"+parse(comicsArr,i,"thumbnail","path")+"."+parse(comicsArr,i,"thumbnail","extension")+"', 'Marvel', currval('comics_comics_id_seq') WHERE NOT EXISTS (SELECT name FROM volume WHERE name = '"+toTitle(parse(comicsArr,i,"title"), 'v')+"');\r\n");
                comicswrt.append("INSERT INTO issue (name, description, img, rel_date, source, volume_id) SELECT '"+parse(comicsArr,i,"title")+"', '"+parse(comicsArr,i,"description")+"', '"+parse(comicsArr,i,"thumbnail","path")+"."+parse(comicsArr,i,"thumbnail","extension")+"', TO_DATE('"+parse(comicsArr,i,"dates",0,"date").substring(0, 10)+"','YYYY-MM-DD'), 'Marvel', currval('volume_volume_id_seq') WHERE NOT EXISTS (SELECT name FROM issue WHERE name = '"+parse(comicsArr,i,"title")+"');\r\n");
                comicswrt.flush();
            }                
        }
    };
    
    public static void fetchChars() throws IOException { //seriously, it's class name
        int limit = 2;
        int offset = 0;
        File chars = new File("chars.txt");
        FileWriter charswrt = new FileWriter(chars,true);
        String charsResponse = new Resty().text(request("characters",limit,offset)).toString();
        JSONObject charsObj = new JSONObject(charsResponse);
        JSONArray charsArr = charsObj.getJSONObject("data").getJSONArray("results");
        
        for (int i=0; i<charsArr.length();i++) { //Yet some more data parsing
            charswrt.append("INSERT INTO character (name, description, img, source) SELECT '"+parse(charsArr,i,"name")+"', '"+parse(charsArr,i,"description")+"', '"+parse(charsArr,i,"thumbnail","path")+"."+parse(charsArr,i,"thumbnail","extension")+"', 'Marvel' WHERE NOT EXISTS (SELECT name FROM character WHERE name = '"+parse(charsArr,i,"name")+"');\r\n");
            charswrt.flush();
            for (int k=0; k<charsArr.getJSONObject(i).getJSONObject("comics").getJSONArray("items").length(); k++) {  //Matching chars with comics
                charswrt.append("INSERT INTO charrefs (char_id, issue_id) SELECT c.char_id, i.issue_id FROM character c, issue i WHERE c.char_id=currvalue('char_char_id_seq') AND i.name='"+parse(charsArr, i, "comics", "items", k, "name")+"'");
                charswrt.flush();
            }
            for (int k=0; k<charsArr.getJSONObject(i).getJSONObject("stories").getJSONArray("items").length(); k++) {  //Matching chars with stories
                charswrt.append("INSERT INTO charrefs (char_id, issue_id) SELECT c.char_id, i.issue_id FROM character c, issue i WHERE c.char_id=currvalue('char_char_id_seq') AND i.name='"+parse(charsArr, i, "comics", "items", k, "name")+"'");
                charswrt.flush();
            }
            for (int k=0; k<charsArr.getJSONObject(i).getJSONObject("series").getJSONArray("items").length(); k++) {  //Matching chars with series
                charswrt.append("INSERT INTO charrefs (char_id, issue_id) SELECT c.char_id, i.issue_id FROM character c, issue i WHERE c.char_id=currvalue('char_char_id_seq') AND i.name='"+parse(charsArr, i, "comics", "items", k, "name")+"'");
                charswrt.flush();
            }
        }
        //These are for connection check mostly
        //charswrt.append(charsObj.toString());
        //charswrt.flush();
    };
    
    public static void main(String[] args) throws IOException { //the one and only
        //Call methods needed (in most cases - fetchChars or fetchComics)
        fetchChars();
        fetchComics();
    };
};