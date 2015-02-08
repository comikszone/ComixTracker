/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.managedbeans.entitycontroller;

import com.comicszone.dao.VolumeFacade;
import com.comicszone.entitynetbeans.Volume;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author aypyatakov
 */

@ManagedBean
@ViewScoped
public class VolumeController implements Serializable {
    @EJB
    private VolumeFacade volumeFacade;
    
    private Volume volume;
    private Integer volumeId;
    
    public VolumeFacade getVolumeFacade() {
        return volumeFacade;
    }
    
    public void setVolumeFacade(VolumeFacade volumeFacade) {
        this.volumeFacade = volumeFacade;
    }
    
    public Volume getVolume() {
        return volume;
    }
    
    public void setVolume(Volume volume) {
        this.volume = volume;
    }
    
    public Integer getVolumeId() {
        return volumeId;
    }
    
    public void setVolumeId(Integer volumeId) {
        this.volumeId = volumeId;
    }
    
    public void init() {
        volume = volumeFacade.find(volumeId);
    }
}
