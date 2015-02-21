/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;

/**
 *
 * @author alexander
 */
public interface Content extends Serializable {

    public Integer getId();
    public void setId(Integer id);
    public ContentType getContentType();
    public String getName();
    public void setName(String name);
    public String getImage();
    public void setImage(String image);
    public String getDescription();
    public void setDescription(String description);
    public String getExtraInfo();
    public Boolean getIsChecked();
    public void setIsChecked(Boolean isChecked);
}
