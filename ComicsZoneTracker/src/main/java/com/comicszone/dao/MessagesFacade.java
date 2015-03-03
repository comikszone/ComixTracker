/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.dao;

import com.comicszone.entity.Comics;
import com.comicszone.entity.Messages;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.primefaces.model.SortOrder;

/**
 *
 * @author ArsenyPC
 */
@Stateless
public class MessagesFacade extends AbstractFacade<Messages> {
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessagesFacade() {
        super(Messages.class);
    }
    public List<Messages> getMessagesByIdSenderAndReceiver(Integer senderId, Integer receiverId, Integer ownerId)
    {
        TypedQuery<Messages> query=(TypedQuery<Messages>) em.createNamedQuery("Messages.findByIdSenderAndReceiver");
        query.setParameter("sender", senderId);
        query.setParameter("receiver", receiverId);
        query.setParameter("ownerId", ownerId);
        List<Messages> list= query.getResultList();
//        query.setParameter("sender", receiverId);
//        query.setParameter("receiver", senderId);
//        List<Messages> list1=query.getResultList();
//        list.addAll(list1);
        return list;
    }
    
//    public List<Messages> findAllForMessages(Integer maxResult, String sortField, SortOrder sortOrder)
//    {    
//        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
//        
//        Query query = em.createQuery("SELECT c FROM Comics c ORDER BY " +
//            "CASE WHEN c." + sortField + " IS NULL THEN 1 ELSE 0 END, " + 
//            "c." + sortField + " " + sortOrderString);
//        
//        query.setMaxResults(maxResult);
//        return query.getResultList();
//    }

    public List<Messages> getMessagesByIdSenderAndReceiver(Integer senderId, Integer receiverId, Integer ownerId, int first, int pageSize, SortOrder sortOrder)
    {
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        String queryString="SELECT  m FROM Messages m where (m.sender.userId=:sender or m.sender.userId=:receiver) "
                + "AND (m.receiver.userId=:receiver or m.receiver.userId=:sender) "
                + "AND ((m.sender.userId=:ownerId AND m.showToSender=TRUE) OR (m.receiver.userId=:ownerId "
                + "AND m.showToReceiver=TRUE)) "
                + "ORDER BY m.msgTime "
                +sortOrderString;
        Query query=em.createQuery(queryString);
        query.setParameter("sender", senderId);
        query.setParameter("receiver", receiverId);
        query.setParameter("ownerId", ownerId);
//        query.setParameter("sortOrder", sortOrder);
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Messages> list= query.getResultList();
        System.err.println("~~~~~~~~list= "+list.toString());
        return list;
    }
    public List<Messages> getMessagesByIdSenderAndReceiverContainText(Integer senderId, Integer receiverId, Integer ownerId, int first, int pageSize, SortOrder sortOrder,String text)
    {
        String sortOrderString = sortOrder == SortOrder.ASCENDING ? "ASC" : "DESC";
        String queryString="SELECT  m FROM Messages m where (m.sender.userId=:sender or m.sender.userId=:receiver) "
        + "AND (m.receiver.userId=:receiver or m.receiver.userId=:sender) "
        + "AND ((m.sender.userId=:ownerId AND m.showToSender=TRUE) OR (m.receiver.userId=:ownerId "
        + "AND m.showToReceiver=TRUE)) "
        + "AND LOWER(m.text) LIKE :text "
        + "ORDER BY m.msgTime "
        +sortOrderString;
        Query query=em.createQuery(queryString);
        query.setParameter("sender", senderId);
        query.setParameter("receiver", receiverId);
        query.setParameter("ownerId", ownerId);
        query.setParameter("text", "%"+text.toLowerCase()+"%");
        query.setFirstResult(first);
        query.setMaxResults(pageSize);
        List<Messages> list= query.getResultList();
        System.err.println("%%%%%%list%%%%%%="+list);
        return list;
    }
    public long getMessagesCount(Integer senderId,Integer receiverId,Integer ownerId)
    {
        TypedQuery<Long> query=(TypedQuery<Long>) em.createNamedQuery("Messages.count");
        query.setParameter("sender", senderId);
        query.setParameter("receiver", receiverId);
        query.setParameter("ownerId", ownerId);
        return query.getResultList().get(0);
    }
        public long getMessagesCountContainText(Integer senderId,Integer receiverId,Integer ownerId, String text)
    {
        TypedQuery<Long> query=(TypedQuery<Long>) em.createNamedQuery("Messages.countMessagesContainText");
        query.setParameter("sender", senderId);
        query.setParameter("receiver", receiverId);
        query.setParameter("ownerId", ownerId);
        query.setParameter("text", "%"+text.toLowerCase()+"%");
        return query.getResultList().get(0);
    }
}