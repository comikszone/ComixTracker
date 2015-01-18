package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class UserProfileManagedBean {

    private UploadedFile newAvatar;
    Users user;
    @EJB
    UserDataFacade userDAO;

    public UploadedFile getNewAvatar() {
        return newAvatar;
    }

    public void setNewAvatar(UploadedFile newAvatar) {
        this.newAvatar = newAvatar;
    }

    public String getNickname() {
        return user.getNickname();
    }

    public void setNickname(String nickname) {
        user.setNickname(nickname);
    }

    public int getSex() {
        return user.getSex();
    }

    public void setSex(int sex) {
        user.setSex(sex);
    }

    public String getBirthday() {
        if (user.getBirthday() == null) return "";
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        System.out.println(df.format(user.getBirthday()));
        return df.format(user.getBirthday());
    }

    public void setBirthday(String birthday) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            user.setBirthday(df.parse(birthday));
        } catch (ParseException ex) {
            Logger.getLogger(UserProfileManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public StreamedContent getAvatar() {
        if (user.getAvatar() == null) {
            return new DefaultStreamedContent(new ByteArrayInputStream(new byte[0]));
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(user.getAvatar()));
    }

    public void saveChanges() {
        if (newAvatar != null){
            user.setAvatar(newAvatar.getContents());
        }
        userDAO.edit(user);
    }

    @PostConstruct
    public void setCurrentUser() {
        Principal prin = FacesContext.getCurrentInstance()
                .getExternalContext().getUserPrincipal();
        user = userDAO.getUserWithNickname(prin.getName());
    }
}
