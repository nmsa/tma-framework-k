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
public class CompositeAttributePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "parentMetric")
    private int parentMetric;
    @Basic(optional = false)
    @Column(name = "childMetric")
    private int childMetric;

    public CompositeAttributePK() {
    }

    public CompositeAttributePK(int parentMetric, int childMetric) {
        this.parentMetric = parentMetric;
        this.childMetric = childMetric;
    }

    public int getParentMetric() {
        return parentMetric;
    }

    public void setParentMetric(int parentMetric) {
        this.parentMetric = parentMetric;
    }

    public int getChildMetric() {
        return childMetric;
    }

    public void setChildMetric(int childMetric) {
        this.childMetric = childMetric;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) parentMetric;
        hash += (int) childMetric;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompositeAttributePK)) {
            return false;
        }
        CompositeAttributePK other = (CompositeAttributePK) object;
        if (this.parentMetric != other.parentMetric) {
            return false;
        }
        if (this.childMetric != other.childMetric) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.CompositeAttributePK[ parentMetric=" + parentMetric + ", childMetric=" + childMetric + " ]";
    }
    
}
