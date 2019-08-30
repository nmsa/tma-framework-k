/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "Data", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d"),
    @NamedQuery(name = "Data.findByProbeId", query = "SELECT d FROM Data d WHERE d.dataPK.probeId = :probeId"),
    @NamedQuery(name = "Data.findByDescriptionId", query = "SELECT d FROM Data d WHERE d.dataPK.descriptionId = :descriptionId"),
    @NamedQuery(name = "Data.findByResourceId", query = "SELECT d FROM Data d WHERE d.dataPK.resourceId = :resourceId"),
    @NamedQuery(name = "Data.findByValueTime", query = "SELECT d FROM Data d WHERE d.dataPK.valueTime = :valueTime"),
    @NamedQuery(name = "Data.findByValue", query = "SELECT d FROM Data d WHERE d.value = :value")})
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DataPK dataPK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value", precision = 22)
    private Double value;
    @JoinColumn(name = "probeId", referencedColumnName = "probeId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Probe probe;
    @JoinColumn(name = "descriptionId", referencedColumnName = "descriptionId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Description description;
    @JoinColumn(name = "resourceId", referencedColumnName = "resourceId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Resource resource;

    public Data() {
    }

    public Data(DataPK dataPK) {
        this.dataPK = dataPK;
    }

    public Data(int probeId, int descriptionId, int resourceId, Date valueTime) {
        this.dataPK = new DataPK(probeId, descriptionId, resourceId, valueTime);
    }

    public DataPK getDataPK() {
        return dataPK;
    }

    public void setDataPK(DataPK dataPK) {
        this.dataPK = dataPK;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Probe getProbe() {
        return probe;
    }

    public void setProbe(Probe probe) {
        this.probe = probe;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataPK != null ? dataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.dataPK == null && other.dataPK != null) || (this.dataPK != null && !this.dataPK.equals(other.dataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Data[ dataPK=" + dataPK + " ]";
    }
    
}
