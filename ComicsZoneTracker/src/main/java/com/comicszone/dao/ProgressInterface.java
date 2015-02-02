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
 * @author GuronPavorro
 */
@Local
public interface ProgressInterface {
    List<Comics> findByUserInProgress(Integer userId);
    Long getTotalIssueCount(Integer comicsId);
    Long getMarkedIssueCount(Integer comicsId);
}
