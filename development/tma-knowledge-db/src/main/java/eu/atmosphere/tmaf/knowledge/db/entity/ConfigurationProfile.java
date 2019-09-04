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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "ConfigurationProfile")
@NamedQueries({
    @NamedQuery(name = "ConfigurationProfile.findAll", query = "SELECT c FROM ConfigurationProfile c"),
    @NamedQuery(name = "ConfigurationProfile.findByConfigurationProfileID", query = "SELECT c FROM ConfigurationProfile c WHERE c.configurationProfileID = :configurationProfileID"),
    @NamedQuery(name = "ConfigurationProfile.findByProfileName", query = "SELECT c FROM ConfigurationProfile c WHERE c.profileName = :profileName")})
public class ConfigurationProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "configurationProfileID")
    private Integer configurationProfileID;
    @Basic(optional = false)
    @Column(name = "profileName")
    private String profileName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configurationProfile")
    private Collection<Preference> preferenceCollection;

    public ConfigurationProfile() {
    }

    public ConfigurationProfile(Integer configurationProfileID) {
        this.configurationProfileID = configurationProfileID;
    }

    public ConfigurationProfile(Integer configurationProfileID, String profileName) {
        this.configurationProfileID = configurationProfileID;
        this.profileName = profileName;
    }

    public Integer getConfigurationProfileID() {
        return configurationProfileID;
    }

    public void setConfigurationProfileID(Integer configurationProfileID) {
        this.configurationProfileID = configurationProfileID;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public Collection<Preference> getPreferenceCollection() {
        return preferenceCollection;
    }

    public void setPreferenceCollection(Collection<Preference> preferenceCollection) {
        this.preferenceCollection = preferenceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configurationProfileID != null ? configurationProfileID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigurationProfile)) {
            return false;
        }
        ConfigurationProfile other = (ConfigurationProfile) object;
        if ((this.configurationProfileID == null && other.configurationProfileID != null) || (this.configurationProfileID != null && !this.configurationProfileID.equals(other.configurationProfileID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.ConfigurationProfile[ configurationProfileID=" + configurationProfileID + " ]";
    }
    
}
