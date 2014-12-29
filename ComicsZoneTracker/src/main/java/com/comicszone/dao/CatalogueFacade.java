/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entitynetbeans.Comics;
import java.util.List;
import javax.ejb.Local;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Eschenko_DA
 */
@Local
public interface CatalogueFacade {
    List<Comics> findAllForCatalogue(Integer maxResult, String sortField, SortOrder sortOrder);
    List<Comics> findByNameAndRating(Integer maxResult, String name, Double rating);
    List<Comics> findByRating(Integer maxResult, Double rating);
    List<Comics> findByName(Integer maxResult, String name);
    long getComicsCount();
    long getComicsCountFoundByNameAndRating(String name, Double rating);
    long getComicsCountFoundByName(String name);
    long getComicsCountFoundByRating(Double rating);
}
