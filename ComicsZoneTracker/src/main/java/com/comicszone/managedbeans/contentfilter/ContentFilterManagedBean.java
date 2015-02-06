package com.comicszone.managedbeans.contentfilter;

import com.comicszone.dao.contentdao.ContentFacade;
import com.comicszone.entitynetbeans.Content;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author alexander
 */
@ManagedBean
@ViewScoped
public class ContentFilterManagedBean implements Serializable {
    
    @EJB
    ContentFacade contentDao;
    
    private List<Content> content;
    private Content editingItem;
    
    @PostConstruct
    public void init() {
        content = contentDao.findByChecking(Boolean.FALSE);
    }
    
    public List<Content> getContent() {
        return content;
    }
    
    public void approve(Content item) {
        if (item.getIsChecked()) {
            content = contentDao.findByChecking(Boolean.FALSE);
            return;
        }
        contentDao.setChecked(item);
        content = contentDao.findByChecking(Boolean.FALSE);
    }
    
    public void delete(Content item) {
        contentDao.delete(item);
        content = contentDao.findByChecking(Boolean.FALSE);
    }
    
    public void onCellEdit(Content item) {
        contentDao.editContent(item);
        content = contentDao.findByChecking(Boolean.FALSE);
    }
}
