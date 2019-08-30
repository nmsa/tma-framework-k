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
public class QualityModelPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "qualityModelId", nullable = false)
    private int qualityModelId;
    @Basic(optional = false)
    @Column(name = "metricId", nullable = false)
    private int metricId;

    public QualityModelPK() {
    }

    public QualityModelPK(int qualityModelId, int metricId) {
        this.qualityModelId = qualityModelId;
        this.metricId = metricId;
    }

    public int getQualityModelId() {
        return qualityModelId;
    }

    public void setQualityModelId(int qualityModelId) {
        this.qualityModelId = qualityModelId;
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
        hash += (int) qualityModelId;
        hash += (int) metricId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QualityModelPK)) {
            return false;
        }
        QualityModelPK other = (QualityModelPK) object;
        if (this.qualityModelId != other.qualityModelId) {
            return false;
        }
        if (this.metricId != other.metricId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.QualityModelPK[ qualityModelId=" + qualityModelId + ", metricId=" + metricId + " ]";
    }
    
}
