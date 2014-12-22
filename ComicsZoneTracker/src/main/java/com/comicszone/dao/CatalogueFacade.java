/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author yesch_000
 */
@Local
public interface CatalogueFacade {
    List<Comics> findByNameAndRatingStartsWith(Integer maxResult, String name, Double rating);
}
