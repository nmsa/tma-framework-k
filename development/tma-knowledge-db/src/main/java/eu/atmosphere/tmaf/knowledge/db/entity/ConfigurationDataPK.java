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
public class ConfigurationDataPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "planId", nullable = false)
    private int planId;
    @Basic(optional = false)
    @Column(name = "actionId", nullable = false)
    private int actionId;

    public ConfigurationDataPK() {
    }

    public ConfigurationDataPK(int planId, int actionId) {
        this.planId = planId;
        this.actionId = actionId;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) planId;
        hash += (int) actionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurationDataPK)) {
            return false;
        }
        ConfigurationDataPK other = (ConfigurationDataPK) object;
        if (this.planId != other.planId) {
            return false;
        }
        if (this.actionId != other.actionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.ConfigurationDataPK[ planId=" + planId + ", actionId=" + actionId + " ]";
    }
    
}
