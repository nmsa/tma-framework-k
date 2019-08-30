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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "Actuator", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "Actuator.findAll", query = "SELECT a FROM Actuator a"),
    @NamedQuery(name = "Actuator.findByActuatorId", query = "SELECT a FROM Actuator a WHERE a.actuatorId = :actuatorId"),
    @NamedQuery(name = "Actuator.findByAddress", query = "SELECT a FROM Actuator a WHERE a.address = :address"),
    @NamedQuery(name = "Actuator.findByPubKey", query = "SELECT a FROM Actuator a WHERE a.pubKey = :pubKey")})
public class Actuator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "actuatorId", nullable = false)
    private Integer actuatorId;
    @Column(name = "address", length = 1024)
    private String address;
    @Column(name = "pubKey", length = 1024)
    private String pubKey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "actuatorId", fetch = FetchType.LAZY)
    private Collection<Action> actionCollection;

    public Actuator() {
    }

    public Actuator(Integer actuatorId) {
        this.actuatorId = actuatorId;
    }

    public Integer getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(Integer actuatorId) {
        this.actuatorId = actuatorId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public Collection<Action> getActionCollection() {
        return actionCollection;
    }

    public void setActionCollection(Collection<Action> actionCollection) {
        this.actionCollection = actionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actuatorId != null ? actuatorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Actuator)) {
            return false;
        }
        Actuator other = (Actuator) object;
        if ((this.actuatorId == null && other.actuatorId != null) || (this.actuatorId != null && !this.actuatorId.equals(other.actuatorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Actuator[ actuatorId=" + actuatorId + " ]";
    }
    
}
