/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;
import org.omnifaces.cdi.Startup;

/**
 *
 * @author Eschenko_DA
 */
@Named("comicsFinderManagedBean")
@Startup
public class ComicsFinderController implements Serializable {
    
    private List<Rating> ratings;
    
    @PostConstruct
    public void init() {
        ratings = new ArrayList();
        ratings.add(new Rating(0,"/resources/images/ratings/AllRatings.jpg"));
        ratings.add(new Rating(1,"/resources/images/ratings/1.jpg"));
        ratings.add(new Rating(2,"/resources/images/ratings/2.jpg"));
        ratings.add(new Rating(3,"/resources/images/ratings/3.jpg"));
        ratings.add(new Rating(4,"/resources/images/ratings/4.jpg"));
    }
    
    public Rating getRating(Integer value) {
        for(Rating currentRating : ratings) {
            if (currentRating.getValue().equals(value)) {
                return currentRating;
            }
        }
        return null;
    }
    
    public List<Rating> getRatings() {
        return ratings;
    }
    
}
