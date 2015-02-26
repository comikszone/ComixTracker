package com.comicszone.managedbeans.userbeans;

import com.comicszone.dao.userdao.UserDataFacade;
import com.comicszone.entitynetbeans.Users;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ProfileUserManagedBean implements Serializable {

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

    private boolean isValidEmail(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public Object getAvatar() {
        if (user.getAvatar() == null) {
            if (user.getAvatarUrl() == null || user.getAvatarUrl().equals("")) {
                System.out.println(user.getAvatarUrl());
                return "/resources/images/default_user_photo.png";
            }
            return user.getAvatarUrl();
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(user.getAvatar()));
    }

    public void loadImage(FileUploadEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();

        UploadedFile image = event.getFile();
        try {
            InputStream is = image.getInputstream();

            byte[] imageArray = new byte[is.available()];
            is.read(imageArray);

            user.setAvatar(imageArray);
        } catch (IOException ex) {
            context.addMessage(null,
                    new FacesMessage("Error:", "Can't get input stream!;"));
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ProfileUserManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        context.addMessage(null,
                new FacesMessage("Success:", "File has been upload;"));
    }

    public void saveChanges() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!isValidEmail(user.getEmail())) {
            context.addMessage(null,
                    new FacesMessage("Error:", "Email isn't correct!"));
            return;
        }
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
