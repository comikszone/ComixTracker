/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "imprint")
@NamedQueries({
    @NamedQuery(name = "Imprint.findAll", query = "SELECT i FROM Imprint i"),
    @NamedQuery(name = "Imprint.findByImprintId", query = "SELECT i FROM Imprint i WHERE i.imprintId = :imprintId"),
    @NamedQuery(name = "Imprint.findByName", query = "SELECT i FROM Imprint i WHERE i.name = :name")})
public class Imprint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "imprint_imprint_id_seq")
    @SequenceGenerator(name = "imprint_imprint_id_seq", sequenceName = "imprint_imprint_id_seq", allocationSize = 1)
    @Basic(optional = false)
    @Column(name = "imprint_id")
    private Integer imprintId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "imprintId", fetch = FetchType.LAZY)
    private List<Comics> comicsList;

    public Imprint() {
    }

    public Imprint(Integer imprintId) {
        this.imprintId = imprintId;
    }
    
    public Imprint(String name) {
        this.name = name;
    }

    public Imprint(Integer imprintId, String name) {
        this.imprintId = imprintId;
        this.name = name;
    }

    public Integer getImprintId() {
        return imprintId;
    }

    public void setImprintId(Integer imprintId) {
        this.imprintId = imprintId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Comics> getComicsList() {
        return comicsList;
    }

    public void setComicsList(List<Comics> comicsList) {
        this.comicsList = comicsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imprintId != null ? imprintId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imprint)) {
            return false;
        }
        Imprint other = (Imprint) object;
        if ((this.imprintId == null && other.imprintId != null) || (this.imprintId != null && !this.imprintId.equals(other.imprintId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.comicszone.entity.Imprint[ imprintId=" + imprintId + " ]";
    }
    
}
