/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.comicscatalogue.entity;

/**
 *
 * @author yesch_000
 */
public class Comics {
    
    private Integer id;
    private String name;
    private Double rating;
    private String image;
    private String description;
    
    public Comics(Integer id, String name,Double rating, String image, String description) {
        this.name = name;
        this.image = image;
        this.rating = rating;
        this.description = description;
        this.id = id;
    }
    
    
    public Integer getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getImage() {
        return image;
    }
    
    public Double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
