/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.friends;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Eschenko_DA
 */
public class LazyFriendsDataModel extends LazyDataModel<Users> {
    
   /* private final UserFriendsFacade friendsFacade;
    
    private List<Users> datasource;
    
    public LazyFriendsDataModel(UserFriendsFacade friendsFacade) {
        datasource = new ArrayList<Users>();
        this.friendsFacade = friendsFacade;
    }

    @Override
    public Users getRowData(String rowKey) {
        for(Users friend : datasource) {
            if(friend.getUserId().toString().equals(rowKey))
                return friend;
        }
        return null;
    }
 
    @Override
    public Object getRowKey(Users friend) {
        return friend;
    }
    
    @Override
    public List<Users> load(int first, int pageSize, String sortField, 
            SortOrder sortOrder, Map<String,Object> filters) {
        
        List<Users> data = new ArrayList<Users>();
        List<Users> resultUsers;
        
        Iterator<String> itFilter = filters.keySet().iterator();
        if(itFilter.hasNext()) {
            
            String nickNameColumn = itFilter.next();
            String nickNameValue = filters.get(nickNameColumn).toString();
            
            resultUsers = friendsFacade.getUsersWithNicknameStartsWith(first+pageSize,nickNameValue);
            this.setRowCount((int)friendsFacade.getComicsCountFoundByName(nextColumnValue));
            }
            else if (nextColumn.equals(columnComicsRating) && !itFilter.hasNext()) {
                Double rating = Double.valueOf(nextColumnValue);
                resultComics = friendsFacade.findByRating(first+pageSize, 
                        rating,sortField,sortOrder);
                this.setRowCount((int)friendsFacade.getComicsCountFoundByRating(rating));
            }
            else
            {
                String columnName;
                String columnRating;
                if (nextColumn.equals(columnComicsName)) {
                    columnName = nextColumn;
                    columnRating = itFilter.next();
                }
                else {
                    columnRating = nextColumn;
                    columnName = itFilter.next();
                }
                
                String columnNameValue = filters.get(columnName).toString();
                Double columnRatingValue = Double.valueOf(filters.get(columnRating).toString());
                
                
                resultComics = friendsFacade.findByNameAndRating(first+pageSize,
                        columnNameValue,columnRatingValue,sortField,sortOrder);
                long count = friendsFacade.getComicsCountFoundByNameAndRating(columnNameValue, columnRatingValue);
                this.setRowCount((int)count);
            }
            
        }
        else {
            resultComics = friendsFacade.findAllForCatalogue(first+pageSize,sortField,sortOrder);
            this.setRowCount((int)(friendsFacade.getComicsCount()));
        }
        
        data.addAll(resultComics);
        datasource = data;
        
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
    }*/
}
