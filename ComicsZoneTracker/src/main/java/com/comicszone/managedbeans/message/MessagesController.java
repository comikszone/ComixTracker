package com.comicszone.managedbeans.message;

import com.comicszone.dao.MessagesFacade;
import com.comicszone.dao.user.UserDataFacade;
import com.comicszone.entity.Messages;
import com.comicszone.entity.Users;
import com.comicszone.managedbeans.userbeans.CurrentUserController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.model.LazyDataModel;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author ArsenyPC
 */
@ManagedBean
@ViewScoped
public class MessagesController implements Serializable {
    @EJB
    MessagesFacade messagesFacade;
    @EJB
    UserDataFacade userDataFacade;
    private FacesMessage facesMessageWrongMessage;
    private Users currentUser;
    private Integer friendId;
    private Users friendUser;
    private LazyDataModel<Messages> dataModel;
    private boolean showMessageAdder;
    private boolean showMessages;
    private int activeIndex=-1;
    public void addMessage() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot uiViewRoot = facesContext.getViewRoot();
        InputTextarea inputText = null;
        inputText = (InputTextarea) uiViewRoot.findComponent(":form:messagesAdderNewMessage");
        String messageText = (String)inputText.getValue();
        if (messageText == null || messageText.isEmpty()) {
            facesContext.addMessage(null, facesMessageWrongMessage);
            return;
        }
        currentUser = userDataFacade.getUserWithNickname(currentUser.getNickname());
        Messages message=new Messages();
        message.setSender(currentUser);
        friendUser = userDataFacade.find(friendId);
        message.setReceiver(friendUser);
        message.setText(messageText);
//        message.setTitle("aaa");
        message.setShowToSender(Boolean.TRUE);
        message.setShowToReceiver(Boolean.TRUE);
        Date date = new Date(System.currentTimeMillis());
        message.setMsgTime(date);
        currentUser.addMessageToMessagesList(message);
        userDataFacade.edit(currentUser);
        inputText.setValue("");
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
//	eventBus.publish("/subscriber", htmlMessage);
        eventBus.publish(message.getReceiver().getUserId());
    }

    
    public void deleteMessage(Messages message) {
        if (message.getSender().equals(currentUser))
        {
            message.setShowToSender(Boolean.FALSE);
        }
        else
        {
            message.setShowToReceiver(Boolean.FALSE);
        }
        messagesFacade.edit(message);
    }
    
    public List<Messages> getMessages() {
        return messagesFacade.getMessagesByIdSenderAndReceiver(currentUser.getUserId(), friendId,currentUser.getUserId());
    }
    
    public String getFormatedDataTime(Messages message) {
        return new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(message.getMsgTime());
    }
    
    @PostConstruct
    public void init() {
        try {
          currentUser = (Users) ((CurrentUserController) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .get("currentUserController"))
                    .getCurrentUser()
                    .clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MessagesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        System.err.println("selected friend id="+friendId);
//        showMessageAdder=true;
//        showMessages=true;
        dataModel=new  LazyMessagesDataModel(messagesFacade, currentUser.getUserId(), friendId, currentUser.getUserId());
        this.friendId = friendId;
    }
    public void setFriendIdWithActiveIndex(Integer friendId, int activeIndex)
    {
        this.activeIndex=activeIndex;
        if (activeIndex==0)
        {
            showMessageAdder=true;
            System.out.println("************activeIndex===0**************");
        }
        showMessages=true;
        setFriendId(friendId);
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public Users getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(Users friendUser) {
        this.friendUser = friendUser;
    }

    public LazyDataModel<Messages> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<Messages> dataModel) {
        this.dataModel = dataModel;
    }

    public boolean isShowMessageAdder() {
        return showMessageAdder;
    }

    public void setShowMessageAdder(boolean showMessageAdder) {
        this.showMessageAdder = showMessageAdder;
    }

    public boolean isShowMessages() {
        return showMessages;
    }

    public void setShowMessages(boolean showMessages) {
        this.showMessages = showMessages;
    }
    public void friendSelected(Integer id)
    {
        friendId=id;
        showMessageAdder=true;
        showMessages=true;
    }
    public void followerSelected(Integer id)
    {
        friendId=id;
        showMessageAdder=false;
        showMessages=true;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }
    public void preRender(Users user)
    {
        if (user!=null)
        {
            setFriendIdWithActiveIndex(user.getUserId(), 0);
        }
    }
    
}