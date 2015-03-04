/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.Serializable;

/**
 *
 * @author alexander
 */
public interface Content extends Serializable, NamedImage {
    
    public String getDescription();
    public void setDescription(String description);
    public Float getRating();
    public void setRating(Float rating);
    public String getExtraInfo();
    public Boolean getIsChecked();
    public void setIsChecked(Boolean isChecked);
    public String getSource();
    public void setSource(String source);
}
