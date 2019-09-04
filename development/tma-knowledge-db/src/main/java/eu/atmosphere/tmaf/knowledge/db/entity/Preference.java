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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "Preference")
@NamedQueries({
    @NamedQuery(name = "Preference.findAll", query = "SELECT p FROM Preference p"),
    @NamedQuery(name = "Preference.findByConfigurationProfileID", query = "SELECT p FROM Preference p WHERE p.preferencePK.configurationProfileID = :configurationProfileID"),
    @NamedQuery(name = "Preference.findByMetricId", query = "SELECT p FROM Preference p WHERE p.preferencePK.metricId = :metricId"),
    @NamedQuery(name = "Preference.findByWeight", query = "SELECT p FROM Preference p WHERE p.weight = :weight"),
    @NamedQuery(name = "Preference.findByThreshold", query = "SELECT p FROM Preference p WHERE p.threshold = :threshold")})
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PreferencePK preferencePK;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Double weight;
    @Column(name = "threshold")
    private Double threshold;
    @JoinColumn(name = "configurationProfileID", referencedColumnName = "configurationProfileID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ConfigurationProfile configurationProfile;
    @JoinColumn(name = "metricId", referencedColumnName = "metricId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Metric metric;

    public Preference() {
    }

    public Preference(PreferencePK preferencePK) {
        this.preferencePK = preferencePK;
    }

    public Preference(int configurationProfileID, int metricId) {
        this.preferencePK = new PreferencePK(configurationProfileID, metricId);
    }

    public PreferencePK getPreferencePK() {
        return preferencePK;
    }

    public void setPreferencePK(PreferencePK preferencePK) {
        this.preferencePK = preferencePK;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public ConfigurationProfile getConfigurationProfile() {
        return configurationProfile;
    }

    public void setConfigurationProfile(ConfigurationProfile configurationProfile) {
        this.configurationProfile = configurationProfile;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (preferencePK != null ? preferencePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Preference)) {
            return false;
        }
        Preference other = (Preference) object;
        if ((this.preferencePK == null && other.preferencePK != null) || (this.preferencePK != null && !this.preferencePK.equals(other.preferencePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Preference[ preferencePK=" + preferencePK + " ]";
    }
    
}
