/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.message;

import com.comicszone.entitynetbeans.Messages;
import com.comicszone.dao.MessagesFacade;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author ArsenyPC
 */
public class LazyMessagesDataModel extends LazyDataModel<Messages> {
    
    private MessagesFacade messagesFacade;
    
    private Integer senderId;
    private Integer receiverId;
    private Integer ownerId;

    public LazyMessagesDataModel() {
    }

    
    public LazyMessagesDataModel(MessagesFacade messagesFacade, Integer senderId, Integer receiverId, Integer ownerId) {
//        System.err.println("*********LazyMessagesModel()********* senderId="+senderId+" receiverid="+receiverId+" ownerId="+ownerId+" messagesFacade="+messagesFacade);
        this.messagesFacade = messagesFacade;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.ownerId = ownerId;
    }

    public void setMessagesFacade(MessagesFacade messagesFacade) {
        this.messagesFacade = messagesFacade;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
    
    @Override
    public List<Messages> load(int first, int pageSize, String sortField, 
            SortOrder sortOrder, Map<String,Object> filters) {
        System.err.println("load invoked!!!!!!!!!!!!!!");
        if (sortField==null) 
        {
            sortOrder=SortOrder.DESCENDING;
        }
        List<Messages> data = new ArrayList<Messages>();
        List<Messages> resultMessages=null;
        
        Iterator<String> itFilter = filters.keySet().iterator();
        if (itFilter.hasNext())
        {
            String textColumn=itFilter.next();
            String textValue=filters.get(textColumn).toString();
            resultMessages=messagesFacade.getMessagesByIdSenderAndReceiverContainText(senderId, receiverId, ownerId, first, pageSize, sortOrder, textValue);
            this.setRowCount((int)messagesFacade.getMessagesCountContainText(senderId, receiverId, ownerId, textValue));
        }
        else
        {
            resultMessages=messagesFacade.getMessagesByIdSenderAndReceiver(senderId, receiverId, ownerId,first,pageSize,sortOrder);
            this.setRowCount((int)messagesFacade.getMessagesCount(senderId, receiverId, ownerId));
        }
        return resultMessages;
    }
}
