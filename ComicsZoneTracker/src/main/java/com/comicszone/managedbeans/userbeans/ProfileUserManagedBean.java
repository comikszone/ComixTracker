package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ProfileUserManagedBean implements Serializable {

    private UploadedFile image;
    private Users user;
    @EJB
    UserDataFacade userDAO;

    public String getName() {
        return user.getName();
    }

    public void setName(String name) {
        user.setName(name);
    }

    public String getNickname() {
        return user.getNickname();
    }

    public int getSex() {
        return user.getSex();
    }

    public void setSex(int sex) {
        user.setSex(sex);
    }

    public String getBirthday() {
        if (user.getBirthday() == null) {
            return "";
        }
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        System.out.println(df.format(user.getBirthday()));
        return df.format(user.getBirthday());
    }

    public void setBirthday(String birthday) {
        if (birthday.length() == 0) {
            user.setBirthday(null);
            return;
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try {
            user.setBirthday(df.parse(birthday));
        } catch (ParseException ex) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage("Error:", "Can't convert date of birthday"));
        }
    }

    public String getEmail() {
        return user.getEmail();
    }

    public void setEmail(String email) {
        user.setEmail(email);
    }

    public Object getAvatar() {
        if (user.getAvatar() == null) {
            return user.getAvatarUrl();
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(user.getAvatar()));
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (image.getSize() == 0) {
            return;
        }
        if (!isCurrectFileName(image.getContentType())) {
            context.addMessage(null,
                    new FacesMessage("Error:", "Incorrect type of file;"));
            return;
        }
        user.setAvatar(image.getContents());
        this.image = image;

        context.addMessage(null,
                new FacesMessage("Success:", "File has been upload;"));
    }

    private boolean isCurrectFileName(String filename) {
        return filename.indexOf("image") == 0;
    }

    public void saveChanges() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            userDAO.edit(user);
            updateCurrentUserManagedBean();
            context.addMessage(null,
                    new FacesMessage("Success:", "Changes has been saved!"));
        } catch (EJBException ex) {
            context.addMessage(null,
                    new FacesMessage("Error:", "Can't update user profile!"));
        } 
    }

    public void resetChanges() {
        loadCurrentUserInfo();
    }

    private void updateCurrentUserManagedBean() {
        CurrentUserManagedBean manBean = ((CurrentUserManagedBean) FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("currentUserManagedBean"));
        manBean.setCurrentUser();
    }

    @PostConstruct
    private void loadCurrentUserInfo() {
        try {
            user = (Users) ((CurrentUserManagedBean) FacesContext
                    .getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .get("currentUserManagedBean"))
                    .getCurrentUser()
                    .clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ProfileUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
