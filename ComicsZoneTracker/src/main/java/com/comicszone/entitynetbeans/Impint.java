/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comicszone.entitynetbeans;

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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author ArsenyPC
 */
@Entity
@Table(name = "\"IMPINT\"")
@NamedQueries({
    @NamedQuery(name = "Impint.findAll", query = "SELECT i FROM Impint i"),
    @NamedQuery(name = "Impint.findByImprintId", query = "SELECT i FROM Impint i WHERE i.imprintId = :imprintId"),
    @NamedQuery(name = "Impint.findByName", query = "SELECT i FROM Impint i WHERE i.name = :name")})
public class Impint implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Impint() {
    }

    public Impint(Integer imprintId) {
        this.imprintId = imprintId;
    }

    public Impint(Integer imprintId, String name) {
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
        if (!(object instanceof Impint)) {
            return false;
        }
        Impint other = (Impint) object;
        if ((this.imprintId == null && other.imprintId != null) || (this.imprintId != null && !this.imprintId.equals(other.imprintId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.netcracker.entitynetbeans.Impint[ imprintId=" + imprintId + " ]";
    }
    
}
