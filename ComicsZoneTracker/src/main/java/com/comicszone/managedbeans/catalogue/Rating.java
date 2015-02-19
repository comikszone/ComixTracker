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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 71 * hash + (this.image != null ? this.image.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rating other = (Rating) obj;
        if (this.value != other.value && (this.value == null || !this.value.equals(other.value))) {
            return false;
        }
        if ((this.image == null) ? (other.image != null) : !this.image.equals(other.image)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rating{" + "value=" + value + ", image=" + image + '}';
    }
}
