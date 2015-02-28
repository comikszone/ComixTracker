/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.comicsPage;

import com.comicszone.entity.Issue;
import com.comicszone.entity.Volume;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author aypyatakov
 */
public class LazyIssueDataModel extends LazyDataModel<Issue> {

    private Volume volume;
    
    public Volume getVolume() {
        return volume;
    }
    
    public void setVolume(Volume volume) {
        this.volume = volume;
    }
    
    public LazyIssueDataModel(Volume volume) {
        this.volume = volume;
    }
    
    @Override
    public List<Issue> load(int first, int pageSize, String sortField, 
            SortOrder sortOrder, Map<String,Object> filters) {
        
        List<Issue> data = volume.getIssueList();
        
        //rowCount
        int dataSize = data.size();
        
        //paginate
        if(dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            }
            catch(IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        }
        else {
            return data;
        }
    }
}
 
