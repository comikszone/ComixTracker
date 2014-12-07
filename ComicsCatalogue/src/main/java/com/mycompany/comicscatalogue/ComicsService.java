/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.comicscatalogue;

import com.mycompany.comicscatalogue.entity.Comics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @author yesch_000
 */
@ManagedBean(name = "comicsService")
@ApplicationScoped
public class ComicsService implements Serializable {
    
    private final static String[] names;
    private final static String[] images;
    private final static Double[] ratings;
    private final static int size = 9;
    
    static {
        names = new String[size];
        names[0] = "Detective Comics";
        names[1] = "Action Comics";
        names[2] = "Batman";
        names[3] = "Batman Vol 2";
        names[4] = "Deadpool";
        names[5] = "Detective Comics Vol 2";
        names[6] = "Green Arrow";
        names[7] = "Green Lantern";
        names[8] = "Iron Man";
        
        images = new String[size];
        images[0] = "Detective_Comics_1.jpg";
        images[1] = "Action_Comics_1.jpg";
        images[2] = "Batman_1.jpg";
        images[3] = "Batman_Vol_2_1.jpg";
        images[4] = "Deadpool_Vol_2_9.jpg";
        images[5] = "Detective_Comics_Vol_2_1.jpg";
        images[6] = "Green_Arrow_Vol_5_1.jpg";
        images[7] = "Green_Lantern_Vol_5_1.jpg";
        images[8] = "Iron_Man_Vol_5_1.jpg";
        
        ratings = new Double[size];
        for (int i = 0; i < size; i++) {
            ratings[i] = ComicsService.getRandomRating();
        }
    }
    
    public ComicsService(){
    }

    public List<Comics> createComics() {
        List<Comics> list = new ArrayList<>();
        for (int i = 0; i < size; i++)
            list.add(new Comics(i+1,names[i],getRandomRating(), images[i],"desc"));
        return list;
    } 
        
    public static double getRandomRating() {
        return Math.random()*5.0;
    }
    
    public List<Double> getRatings() {
        return Arrays.asList(ratings);
    }
    
}
