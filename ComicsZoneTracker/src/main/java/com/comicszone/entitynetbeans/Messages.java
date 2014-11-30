/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "messages")
@NamedQueries({
    @NamedQuery(name = "Messages.findAll", query = "SELECT m FROM Messages m"),
    @NamedQuery(name = "Messages.findByMsgId", query = "SELECT m FROM Messages m WHERE m.msgId = :msgId"),
    @NamedQuery(name = "Messages.findByTitle", query = "SELECT m FROM Messages m WHERE m.title = :title"),
    @NamedQuery(name = "Messages.findByText", query = "SELECT m FROM Messages m WHERE m.text = :text")})
public class Messages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "messages_msg_id_seq")
    @SequenceGenerator(name = "messages_msg_id_seq", sequenceName = "messages_msg_id_seq")
    @Basic(optional = false)
    @Column(name = "msg_id")
    private Integer msgId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "text")
    private String text;
    @JoinColumn(name = "sender", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users sender;
    @JoinColumn(name = "receiver", referencedColumnName = "user_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Users receiver;

    public Messages() {
    }

    public Messages(Integer msgId) {
        this.msgId = msgId;
    }

    public Messages(Integer msgId, String title, String text) {
        this.msgId = msgId;
        this.title = title;
        this.text = text;
    }

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public Users getReceiver() {
        return receiver;
    }

    public void setReceiver(Users receiver) {
        this.receiver = receiver;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (msgId != null ? msgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Messages)) {
            return false;
        }
        Messages other = (Messages) object;
        if ((this.msgId == null && other.msgId != null) || (this.msgId != null && !this.msgId.equals(other.msgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entitynetbeans.Messages[ msgId=" + msgId + " ]";
    }
    
}
