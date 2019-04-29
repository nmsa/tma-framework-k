/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nmsa
 */
@Embeddable
public class DataPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "probeId", nullable = false)
    private int probeId;
    @Basic(optional = false)
    @Column(name = "descriptionId", nullable = false)
    private int descriptionId;
    @Basic(optional = false)
    @Column(name = "resourceId", nullable = false)
    private int resourceId;
    @Basic(optional = false)
    @Column(name = "valueTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueTime;

    public DataPK() {
    }

    public DataPK(int probeId, int descriptionId, int resourceId, Date valueTime) {
        this.probeId = probeId;
        this.descriptionId = descriptionId;
        this.resourceId = resourceId;
        this.valueTime = valueTime;
    }

    public int getProbeId() {
        return probeId;
    }

    public void setProbeId(int probeId) {
        this.probeId = probeId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Date getValueTime() {
        return valueTime;
    }

    public void setValueTime(Date valueTime) {
        this.valueTime = valueTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) probeId;
        hash += (int) descriptionId;
        hash += (int) resourceId;
        hash += (valueTime != null ? valueTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataPK)) {
            return false;
        }
        DataPK other = (DataPK) object;
        if (this.probeId != other.probeId) {
            return false;
        }
        if (this.descriptionId != other.descriptionId) {
            return false;
        }
        if (this.resourceId != other.resourceId) {
            return false;
        }
        if ((this.valueTime == null && other.valueTime != null) || (this.valueTime != null && !this.valueTime.equals(other.valueTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.DataPK[ probeId=" + probeId + ", descriptionId=" + descriptionId + ", resourceId=" + resourceId + ", valueTime=" + valueTime + " ]";
    }
    
}
