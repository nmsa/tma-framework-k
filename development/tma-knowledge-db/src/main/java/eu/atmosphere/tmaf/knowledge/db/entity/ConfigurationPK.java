/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 * ***
 * <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Knowledge - DB
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework
 * License: https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
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
public class ConfigurationPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "configurationId", nullable = false)
    private int configurationId;
    @Basic(optional = false)
    @Column(name = "actionId", nullable = false)
    private int actionId;

    public ConfigurationPK() {
    }

    public ConfigurationPK(int configurationId, int actionId) {
        this.configurationId = configurationId;
        this.actionId = actionId;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
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
        hash += (int) configurationId;
        hash += (int) actionId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurationPK)) {
            return false;
        }
        ConfigurationPK other = (ConfigurationPK) object;
        if (this.configurationId != other.configurationId) {
            return false;
        }
        if (this.actionId != other.actionId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.ConfigurationPK[ configurationId=" + configurationId + ", actionId=" + actionId + " ]";
    }
    
}
