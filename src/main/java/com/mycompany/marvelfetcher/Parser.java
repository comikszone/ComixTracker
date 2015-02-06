/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.marvelfetcher;

import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Phoenix1092
 */
public class Parser {
    
    public static String parseNode(JSONArray jArr, int i, String node) { //for getting comics and character title(name), description and other 1-JSON-lvl stuff
        
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
    
    public static String parseSubNode(JSONArray jArr, int i, String node, String subNode) { //for thumbnails
        
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
    
    public static String parseSubArr(JSONArray jArr, int i, String arrNode, int k, String subArrNode) { //for date
        
        try {
            return jArr.getJSONObject(i).getJSONArray(arrNode).getJSONObject(k).getString(subArrNode).replace("'", "\"");
        }
        catch(JSONException e) {
            return "null";
        }
    };
    
    public static String parseSubArrNode(JSONArray jArr, int i, String node, String array, int k, String subNode) { //for comics list in character JSON
        try{
            return jArr.getJSONObject(i).getJSONObject(node).getJSONArray(array).getJSONObject(k).getString(subNode);
        }
        catch(JSONException e) {
            return "null";
        }        
    };
}
