/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.marvelfetcher;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Phoenix1092
 */
public class Requester {
    
    public static String buildRequest(String entity, int limit, int offset) {   //Receives subdomain name (comics, character, etc), fetch limit and starting number
        
        String publicKey = "527811a07f39623030a8c9f82a753791";          //Swap these for yours if are able, please.
        String privateKey = "8798990b7f0835aa20c193e12057ffcb201ec782";
        long timeStamp = System.currentTimeMillis();
        String orderBy = "title";
        String hash = DigestUtils.md5Hex(timeStamp + privateKey + publicKey);
        return String.format("http://gateway.marvel.com/v1/public/%s?ts=%d&apikey=%s&hash=%s&limit=%d&orderBy=%s&offset=%d",entity, timeStamp, publicKey, hash, limit, orderBy, offset);
    };
}
