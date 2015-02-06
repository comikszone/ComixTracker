/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.marvelfetcher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import us.monoid.web.Resty;

/**
 *
 * @author Phoenix1092
 */
public class Fetcher {
    
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
    
    public static void fetchComics(int limit, int offset) throws IOException { //well, it is what it is
        
        File comics = new File("comics.txt");
        FileWriter comicswrt = new FileWriter(comics,true);
        String comicsResponse = new Resty().text(Requester.buildRequest("comics",limit,offset)).toString();
        JSONObject comicsObj = new JSONObject(comicsResponse);
        JSONArray comicsArr = comicsObj.getJSONObject("data").getJSONArray("results");
        
        for (int i=0; i<comicsArr.length(); i++) {
            if (Parser.parseNode(comicsArr,i,"title").toLowerCase().contains("poster")) //no need for posters here
                continue;
            else { //some data parsing
                comicswrt.append("INSERT INTO comics (name, description, img, source) SELECT '"+toTitle(Parser.parseNode(comicsArr,i,"title"), 'c')+"', '"+Parser.parseNode(comicsArr,i,"description")+"', '"+Parser.parseSubNode(comicsArr,i,"thumbnail","path")+"."+Parser.parseSubNode(comicsArr,i,"thumbnail","extension")+"', 'Marvel' WHERE NOT EXISTS (SELECT name FROM comics WHERE name = '"+toTitle(Parser.parseNode(comicsArr,i,"title"), 'c')+"');\r\n");
                comicswrt.append("INSERT INTO volume (name, description, img, source, comics_id) SELECT '"+toTitle(Parser.parseNode(comicsArr,i,"title"), 'v')+"', '"+Parser.parseNode(comicsArr,i,"description")+"', '"+Parser.parseSubNode(comicsArr,i,"thumbnail","path")+"."+Parser.parseSubNode(comicsArr,i,"thumbnail","extension")+"', 'Marvel', currval('comics_comics_id_seq') WHERE NOT EXISTS (SELECT name FROM volume WHERE name = '"+toTitle(Parser.parseNode(comicsArr,i,"title"), 'v')+"');\r\n");
                comicswrt.append("INSERT INTO issue (name, description, img, rel_date, source, volume_id) SELECT '"+Parser.parseNode(comicsArr,i,"title")+"', '"+Parser.parseNode(comicsArr,i,"description")+"', '"+Parser.parseSubNode(comicsArr,i,"thumbnail","path")+"."+Parser.parseSubNode(comicsArr,i,"thumbnail","extension")+"', TO_DATE('"+Parser.parseSubArr(comicsArr,i,"dates",0,"date").substring(0, 10)+"','YYYY-MM-DD'), 'Marvel', currval('volume_volume_id_seq') WHERE NOT EXISTS (SELECT name FROM issue WHERE name = '"+Parser.parseNode(comicsArr,i,"title")+"');\r\n");
                comicswrt.flush();
            }               
        }
    };
    
    public static void fetchChars(int limit, int offset) throws IOException { //seriously, it's method name
        
        File chars = new File("chars.txt");
        FileWriter charswrt = new FileWriter(chars,true);
        String charsResponse = new Resty().text(Requester.buildRequest("characters",limit,offset)).toString();
        JSONObject charsObj = new JSONObject(charsResponse);
        JSONArray charsArr = charsObj.getJSONObject("data").getJSONArray("results");
        
        for (int i=0; i<charsArr.length();i++) { //Yet some more data parsing
            charswrt.append("INSERT INTO character (name, description, img, source) SELECT '"+Parser.parseNode(charsArr,i,"name")+"', '"+Parser.parseNode(charsArr,i,"description")+"', '"+Parser.parseSubNode(charsArr,i,"thumbnail","path")+"."+Parser.parseSubNode(charsArr,i,"thumbnail","extension")+"', 'Marvel' WHERE NOT EXISTS (SELECT name FROM character WHERE name = '"+Parser.parseNode(charsArr,i,"name")+"');\r\n");
            charswrt.flush();
            for (int k=0; k<charsArr.getJSONObject(i).getJSONObject("comics").getJSONArray("items").length(); k++) {  //Matching chars with comics
                charswrt.append("INSERT INTO charrefs (char_id, issue_id) SELECT c.char_id, i.issue_id FROM character c, issue i WHERE c.char_id=currvalue('char_char_id_seq') AND i.name='"+Parser.parseSubArrNode(charsArr, i, "comics", "items", k, "name")+"'");
                charswrt.flush();
            }
            for (int k=0; k<charsArr.getJSONObject(i).getJSONObject("stories").getJSONArray("items").length(); k++) {  //Matching chars with stories
                charswrt.append("INSERT INTO charrefs (char_id, issue_id) SELECT c.char_id, i.issue_id FROM character c, issue i WHERE c.char_id=currvalue('char_char_id_seq') AND i.name='"+Parser.parseSubArrNode(charsArr, i, "comics", "items", k, "name")+"'");
                charswrt.flush();
            }
            for (int k=0; k<charsArr.getJSONObject(i).getJSONObject("series").getJSONArray("items").length(); k++) {  //Matching chars with series
                charswrt.append("INSERT INTO charrefs (char_id, issue_id) SELECT c.char_id, i.issue_id FROM character c, issue i WHERE c.char_id=currvalue('char_char_id_seq') AND i.name='"+Parser.parseSubArrNode(charsArr, i, "comics", "items", k, "name")+"'");
                charswrt.flush();
            }
        }
    };
}
