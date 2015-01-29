package com.comicszone.dao.contentdao;

import com.comicszone.dao.AbstractFacade;
import com.comicszone.dao.ComicsFacade;
import com.comicszone.entitynetbeans.Content;
import com.comicszone.entitynetbeans.ContentType;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author alexander
 */
@Stateless
public class ContentDao extends AbstractFacade<Content> {
    
    @EJB
    ComicsFacade comicsFacade;
    
    public ContentDao() {
        super(Content.class);
    }
    
    @PersistenceContext(unitName = "com.mycompany_ComicsZoneTracker_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Content> findByChecking(Boolean checked) {
        TypedQuery<Content> comicsQuery = 
                em.createNamedQuery("Comics.findByChecking", Content.class);
        comicsQuery.setParameter("isChecked", checked);
        TypedQuery<Content> volumeQuery = 
                em.createNamedQuery("Volume.findByChecking", Content.class);
        volumeQuery.setParameter("isChecked", checked);
        TypedQuery<Content> issueQuery = 
                em.createNamedQuery("Issue.findByChecking", Content.class);
        issueQuery.setParameter("isChecked", checked);
        List<Content> result = comicsQuery.getResultList();
        result.addAll(volumeQuery.getResultList());
        result.addAll(issueQuery.getResultList());
        return result;
    }
    
    public void setChecked(Content item) {
        Content editingItem = null;
        ContentType type = item.getContentType();
        Integer id = item.getId();
        switch(type) {
            case Comics:
                editingItem = comicsFacade.find(id);
                if (editingItem == null) {
                    return;
                }
                editingItem.setIsChecked(Boolean.TRUE);
                return;
                
            case Issue:
                return;
                
            case Volume:
        }
    }
    
    public void delete(Content item) {
        remove(item);
    }
    
    public void setName(Content item) {
        Content editingItem = null;
        ContentType type = item.getContentType();
        Integer id = item.getId();
        String name = item.getName();
        switch(type) {
            case Comics:
                editingItem = comicsFacade.find(id);
                if (editingItem == null) {
                    return;
                }
                editingItem.setName(name);
                return;
                
            case Issue:
                return;
                
            case Volume:
        }
    }
    
    public void setImage(Content item) {
        Content editingItem = null;
        ContentType type = item.getContentType();
        Integer id = item.getId();
        String image = item.getImage();
        switch(type) {
            case Comics:
                editingItem = comicsFacade.find(id);
                if (editingItem == null) {
                    return;
                }
                editingItem.setImage(image);
                return;
                
            case Issue:
                return;
                
            case Volume:
        }
    }
    
    public void setDescription(Content item) {
        Content editingItem = null;
        ContentType type = item.getContentType();
        Integer id = item.getId();
        String description = item.getDescription();
        switch(type) {
            case Comics:
                editingItem = comicsFacade.find(id);
                if (editingItem == null) {
                    return;
                }
                editingItem.setDescription(description);
                return;
                
            case Issue:
                return;
                
            case Volume:
        }
    }
    
    public void editContent(Content item) {
        Content editingItem = null;
        ContentType type = item.getContentType();
        Integer id = item.getId();
        switch(type) {
            case Comics:
                editingItem = comicsFacade.find(id);
                if (editingItem == null) {
                    return;
                }
                editingItem.setDescription(item.getDescription());
                editingItem.setImage(item.getImage());
                editingItem.setName(item.getName());
                editingItem.setIsChecked(item.getIsChecked());
                return;
                
            case Volume:
                return;
                
            case Issue:
        }
    }
 }
