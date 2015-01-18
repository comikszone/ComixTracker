/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.catalogue;

/**
 *
 * @author Eschenko_DA
 */
public class Rating {

    public Rating(Integer value, String image) {
        this.value = value;
        this.image = image;
    }
    
    private Integer value;
    private String image;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
}
