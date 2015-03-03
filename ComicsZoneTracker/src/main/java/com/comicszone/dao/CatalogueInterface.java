/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entity.Comics;
import java.util.List;
import javax.ejb.Local;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Eschenko_DA
 */
@Local
public interface CatalogueInterface {
    List<Comics> findAllForCatalogue(Integer first, Integer pageSize, String sortField, 
            SortOrder sortOrder);
    List<Comics> findByNameAndRating(Integer first, Integer pageSize, String name, 
            Double rating, String sortField, SortOrder sortOrder);
    List<Comics> findByRating(Integer first, Integer pageSize, Double rating, 
            String sortField, SortOrder sortOrder);
    List<Comics> findByName(Integer first, Integer pageSize, String name, 
            String sortField, SortOrder sortOrder);
    long getComicsCount();
    long getComicsCountFoundByNameAndRating(String name, Double rating);
    long getComicsCountFoundByName(String name);
    long getComicsCountFoundByRating(Double rating);
}
