/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author nmsa
 */
@Embeddable
public class LeafAttributePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "descriptionId")
    private int descriptionId;
    @Basic(optional = false)
    @Column(name = "metricId")
    private int metricId;

    public LeafAttributePK() {
    }

    public LeafAttributePK(int descriptionId, int metricId) {
        this.descriptionId = descriptionId;
        this.metricId = metricId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) descriptionId;
        hash += (int) metricId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeafAttributePK)) {
            return false;
        }
        LeafAttributePK other = (LeafAttributePK) object;
        if (this.descriptionId != other.descriptionId) {
            return false;
        }
        if (this.metricId != other.metricId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.LeafAttributePK[ descriptionId=" + descriptionId + ", metricId=" + metricId + " ]";
    }
    
}
