/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import java.util.List;

/**
 *
 * @author GuronPavorro
 */
public interface SlideshowFacade {
    public List<Comics> get12Best();
}
