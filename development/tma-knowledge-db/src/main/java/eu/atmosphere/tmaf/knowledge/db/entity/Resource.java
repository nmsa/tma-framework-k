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
@Table(name = "Resource")
@NamedQueries({
    @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
    @NamedQuery(name = "Resource.findByResourceId", query = "SELECT r FROM Resource r WHERE r.resourceId = :resourceId"),
    @NamedQuery(name = "Resource.findByResourceName", query = "SELECT r FROM Resource r WHERE r.resourceName = :resourceName"),
    @NamedQuery(name = "Resource.findByResourceType", query = "SELECT r FROM Resource r WHERE r.resourceType = :resourceType"),
    @NamedQuery(name = "Resource.findByResourceAddress", query = "SELECT r FROM Resource r WHERE r.resourceAddress = :resourceAddress")})
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "resourceId")
    private Integer resourceId;
    @Column(name = "resourceName")
    private String resourceName;
    @Column(name = "resourceType")
    private String resourceType;
    @Column(name = "resourceAddress")
    private String resourceAddress;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resourceId")
    private Collection<Action> actionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resource")
    private Collection<Data> dataCollection;
    @OneToMany(mappedBy = "resourceId")
    private Collection<MetricData> metricDataCollection;

    public Resource() {
    }

    public Resource(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceAddress() {
        return resourceAddress;
    }

    public void setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
    }

    public Collection<Action> getActionCollection() {
        return actionCollection;
    }

    public void setActionCollection(Collection<Action> actionCollection) {
        this.actionCollection = actionCollection;
    }

    public Collection<Data> getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(Collection<Data> dataCollection) {
        this.dataCollection = dataCollection;
    }

    public Collection<MetricData> getMetricDataCollection() {
        return metricDataCollection;
    }

    public void setMetricDataCollection(Collection<MetricData> metricDataCollection) {
        this.metricDataCollection = metricDataCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resourceId != null ? resourceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource other = (Resource) object;
        if ((this.resourceId == null && other.resourceId != null) || (this.resourceId != null && !this.resourceId.equals(other.resourceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Resource[ resourceId=" + resourceId + " ]";
    }
    
}
