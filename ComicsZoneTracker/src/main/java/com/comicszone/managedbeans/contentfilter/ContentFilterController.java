package com.comicszone.managedbeans.contentfilter;

import com.comicszone.dao.ComicsFacade;
import com.comicszone.dao.IssueFacade;
import com.comicszone.dao.VolumeFacade;
import com.comicszone.dao.content.ContentFacade;
import com.comicszone.entity.Comics;
import com.comicszone.entity.Content;
import com.comicszone.entity.Issue;
import com.comicszone.entity.Volume;
import java.io.Serializable;
import java.util.List;
import java.util.Vector;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author alexander
 */
@ManagedBean
@SessionScoped
public class ContentFilterController implements Serializable {
    
    @EJB
    private ContentFacade contentDao;
    
    @EJB
    private ComicsFacade comicsFacade;
    
    @EJB
    private IssueFacade issueFacade;
    
    @EJB
    private VolumeFacade volumeFacade;
    
    private List<Comics> comicsContent;
    private List<Issue> issueContent;
    private List<Volume> volumeContent;
    private List<Content> approvedContent;
    private final static String APPROVED = "This item has been approved by other admin.";
    private final static String DELETED = "This item has been deleted by other admin.";
    private final static String CONTENT_ERROR = "Content error";
    private FacesMessage approvedMessage;
    private FacesMessage deletedMessage;
    
    @PostConstruct
    private void init() {
       updateContent();
       approvedContent = new Vector<Content>();
       approvedMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, CONTENT_ERROR, APPROVED);
       deletedMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, CONTENT_ERROR, DELETED);
    }
    
    public List<Comics> getComicsContent() {
        return comicsContent;
    }
    
    public List<Issue> getIssueContent() {
        return issueContent;
    }
    
    public List<Volume> getVolumeContent() {
        return volumeContent;
    }
    
    public List<Content> getApprovedContent() {
        return approvedContent;
    }
    
    public void approve(Content item) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!contentDao.exists(item)) {
            facesContext.addMessage(null, deletedMessage);
        }
        else {
            if (contentDao.isChecked(item)) {
                facesContext.addMessage(null, approvedMessage);
            }
            else {
                contentDao.setChecked(item);
                approvedContent.add(item);
            }
        }
        updateContent();
    }
    
    public void delete(Content item) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!contentDao.exists(item)) {
            facesContext.addMessage(null, deletedMessage);
        }
        else {
            if (contentDao.isChecked(item)) {
                facesContext.addMessage(null, approvedMessage);
            }
            else {
                contentDao.delete(item);
            }
        }
        updateContent();
    }
    
    public void onCellEdit(Content item) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (!contentDao.exists(item)) {
            facesContext.addMessage(null, deletedMessage);
        }
        else {
            if (contentDao.isChecked(item)) {
                facesContext.addMessage(null, approvedMessage);
            }
            else {
                contentDao.editContent(item);
            }
        }
        updateContent();
    }
    
    public void updateContent() {
        comicsContent = comicsFacade.findByChecking(false);
        issueContent = issueFacade.findByChecking(false);
        volumeContent = volumeFacade.findByChecking(false);
    }
}
