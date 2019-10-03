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
@Table(name = "Description")
@NamedQueries({
    @NamedQuery(name = "Description.findAll", query = "SELECT d FROM Description d"),
    @NamedQuery(name = "Description.findByDescriptionId", query = "SELECT d FROM Description d WHERE d.descriptionId = :descriptionId"),
    @NamedQuery(name = "Description.findByDataType", query = "SELECT d FROM Description d WHERE d.dataType = :dataType"),
    @NamedQuery(name = "Description.findByDescriptionName", query = "SELECT d FROM Description d WHERE d.descriptionName = :descriptionName"),
    @NamedQuery(name = "Description.findByUnit", query = "SELECT d FROM Description d WHERE d.unit = :unit")})
public class Description implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "descriptionId")
    private Integer descriptionId;
    @Column(name = "dataType")
    private String dataType;
    @Column(name = "descriptionName")
    private String descriptionName;
    @Column(name = "unit")
    private String unit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "description")
    private Collection<Data> dataCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "descriptionId")
    private Collection<LeafAttribute> leafAttributeCollection;

    public Description() {
    }

    public Description(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Integer getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Collection<Data> getDataCollection() {
        return dataCollection;
    }

    public void setDataCollection(Collection<Data> dataCollection) {
        this.dataCollection = dataCollection;
    }

    public Collection<LeafAttribute> getLeafAttributeCollection() {
        return leafAttributeCollection;
    }

    public void setLeafAttributeCollection(Collection<LeafAttribute> leafAttributeCollection) {
        this.leafAttributeCollection = leafAttributeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (descriptionId != null ? descriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Description)) {
            return false;
        }
        Description other = (Description) object;
        if ((this.descriptionId == null && other.descriptionId != null) || (this.descriptionId != null && !this.descriptionId.equals(other.descriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Description[ descriptionId=" + descriptionId + " ]";
    }
    
}
