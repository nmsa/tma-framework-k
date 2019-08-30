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
public class PreferencePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "configurationProfileID")
    private int configurationProfileID;
    @Basic(optional = false)
    @Column(name = "metricId")
    private int metricId;

    public PreferencePK() {
    }

    public PreferencePK(int configurationProfileID, int metricId) {
        this.configurationProfileID = configurationProfileID;
        this.metricId = metricId;
    }

    public int getConfigurationProfileID() {
        return configurationProfileID;
    }

    public void setConfigurationProfileID(int configurationProfileID) {
        this.configurationProfileID = configurationProfileID;
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
        hash += (int) configurationProfileID;
        hash += (int) metricId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PreferencePK)) {
            return false;
        }
        PreferencePK other = (PreferencePK) object;
        if (this.configurationProfileID != other.configurationProfileID) {
            return false;
        }
        if (this.metricId != other.metricId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.PreferencePK[ configurationProfileID=" + configurationProfileID + ", metricId=" + metricId + " ]";
    }
    
}
