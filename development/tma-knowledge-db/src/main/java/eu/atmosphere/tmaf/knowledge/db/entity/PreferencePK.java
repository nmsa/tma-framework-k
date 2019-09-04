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
 * 
 * 
 * @author Jorge Luiz <jorgem@unicamp.br>
 * @author Breno de França <breno@ic.unicamp.br>
 * @author José Pereira <josep@dei.uc.pt>
 * @author Nuno Antunes <nmsa@dei.uc.pt>
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
