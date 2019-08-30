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
public class MetricDataPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "metricId")
    private int metricId;
    @Basic(optional = false)
    @Column(name = "valueTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueTime;

    public MetricDataPK() {
    }

    public MetricDataPK(int metricId, Date valueTime) {
        this.metricId = metricId;
        this.valueTime = valueTime;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
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
        hash += (int) metricId;
        hash += (valueTime != null ? valueTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MetricDataPK)) {
            return false;
        }
        MetricDataPK other = (MetricDataPK) object;
        if (this.metricId != other.metricId) {
            return false;
        }
        if ((this.valueTime == null && other.valueTime != null) || (this.valueTime != null && !this.valueTime.equals(other.valueTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.MetricDataPK[ metricId=" + metricId + ", valueTime=" + valueTime + " ]";
    }
    
}
