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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "ConfigurationData", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "ConfigurationData.findAll", query = "SELECT c FROM ConfigurationData c"),
    @NamedQuery(name = "ConfigurationData.findByPlanId", query = "SELECT c FROM ConfigurationData c WHERE c.configurationDataPK.planId = :planId"),
    @NamedQuery(name = "ConfigurationData.findByActionId", query = "SELECT c FROM ConfigurationData c WHERE c.configurationDataPK.actionId = :actionId"),
    @NamedQuery(name = "ConfigurationData.findByValue", query = "SELECT c FROM ConfigurationData c WHERE c.value = :value")})
public class ConfigurationData implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConfigurationDataPK configurationDataPK;
    @Column(name = "value", length = 1024)
    private String value;
    @JoinColumns({
        @JoinColumn(name = "planId", referencedColumnName = "planId", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "actionId", referencedColumnName = "actionId", nullable = false, insertable = false, updatable = false)})
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private ActionPlan actionPlan;
    @JoinColumn(name = "actionId", referencedColumnName = "actionId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Configuration configuration;

    public ConfigurationData() {
    }

    public ConfigurationData(ConfigurationDataPK configurationDataPK) {
        this.configurationDataPK = configurationDataPK;
    }

    public ConfigurationData(int planId, int actionId) {
        this.configurationDataPK = new ConfigurationDataPK(planId, actionId);
    }

    public ConfigurationDataPK getConfigurationDataPK() {
        return configurationDataPK;
    }

    public void setConfigurationDataPK(ConfigurationDataPK configurationDataPK) {
        this.configurationDataPK = configurationDataPK;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ActionPlan getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(ActionPlan actionPlan) {
        this.actionPlan = actionPlan;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configurationDataPK != null ? configurationDataPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurationData)) {
            return false;
        }
        ConfigurationData other = (ConfigurationData) object;
        if ((this.configurationDataPK == null && other.configurationDataPK != null) || (this.configurationDataPK != null && !this.configurationDataPK.equals(other.configurationDataPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.ConfigurationData[ configurationDataPK=" + configurationDataPK + " ]";
    }
    
}
