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
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * 
 * 
 * @author Jorge Luiz <jorgem@unicamp.br>
 * @author Breno de França <breno@ic.unicamp.br>
 * @author José Pereira <josep@dei.uc.pt>
 * @author Nuno Antunes <nmsa@dei.uc.pt>
 */
@Entity
@Table(name = "Configuration")
@NamedQueries({
    @NamedQuery(name = "Configuration.findAll", query = "SELECT c FROM Configuration c"),
    @NamedQuery(name = "Configuration.findByConfigurationId", query = "SELECT c FROM Configuration c WHERE c.configurationPK.configurationId = :configurationId"),
    @NamedQuery(name = "Configuration.findByActionId", query = "SELECT c FROM Configuration c WHERE c.configurationPK.actionId = :actionId"),
    @NamedQuery(name = "Configuration.findByKeyName", query = "SELECT c FROM Configuration c WHERE c.keyName = :keyName"),
    @NamedQuery(name = "Configuration.findByDomain", query = "SELECT c FROM Configuration c WHERE c.domain = :domain")})
public class Configuration implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConfigurationPK configurationPK;
    @Column(name = "keyName")
    private String keyName;
    @Column(name = "domain")
    private String domain;
    @JoinColumn(name = "actionId", referencedColumnName = "actionId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Action action;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configuration")
    private Collection<ConfigurationData> configurationDataCollection;

    public Configuration() {
    }

    public Configuration(ConfigurationPK configurationPK) {
        this.configurationPK = configurationPK;
    }

    public Configuration(int configurationId, int actionId) {
        this.configurationPK = new ConfigurationPK(configurationId, actionId);
    }

    public ConfigurationPK getConfigurationPK() {
        return configurationPK;
    }

    public void setConfigurationPK(ConfigurationPK configurationPK) {
        this.configurationPK = configurationPK;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Collection<ConfigurationData> getConfigurationDataCollection() {
        return configurationDataCollection;
    }

    public void setConfigurationDataCollection(Collection<ConfigurationData> configurationDataCollection) {
        this.configurationDataCollection = configurationDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configurationPK != null ? configurationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configuration)) {
            return false;
        }
        Configuration other = (Configuration) object;
        if ((this.configurationPK == null && other.configurationPK != null) || (this.configurationPK != null && !this.configurationPK.equals(other.configurationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Configuration[ configurationPK=" + configurationPK + " ]";
    }
    
}
