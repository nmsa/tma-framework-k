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
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class DataPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "probeId")
    private int probeId;
    @Basic(optional = false)
    @Column(name = "descriptionId")
    private int descriptionId;
    @Basic(optional = false)
    @Column(name = "resourceId")
    private int resourceId;
    @Basic(optional = false)
    @Column(name = "valueTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueTime;

    public DataPK() {
    }

    public DataPK(int probeId, int descriptionId, int resourceId, Date valueTime) {
        this.probeId = probeId;
        this.descriptionId = descriptionId;
        this.resourceId = resourceId;
        this.valueTime = valueTime;
    }

    public int getProbeId() {
        return probeId;
    }

    public void setProbeId(int probeId) {
        this.probeId = probeId;
    }

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Date getValueTime() {
        return valueTime;
    }

    public void setValueTime(Date valueTime) {
        this.valueTime = valueTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) probeId;
        hash += (int) descriptionId;
        hash += (int) resourceId;
        hash += (valueTime != null ? valueTime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataPK)) {
            return false;
        }
        DataPK other = (DataPK) object;
        if (this.probeId != other.probeId) {
            return false;
        }
        if (this.descriptionId != other.descriptionId) {
            return false;
        }
        if (this.resourceId != other.resourceId) {
            return false;
        }
        if ((this.valueTime == null && other.valueTime != null) || (this.valueTime != null && !this.valueTime.equals(other.valueTime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.DataPK[ probeId=" + probeId + ", descriptionId=" + descriptionId + ", resourceId=" + resourceId + ", valueTime=" + valueTime + " ]";
    }
    
}
