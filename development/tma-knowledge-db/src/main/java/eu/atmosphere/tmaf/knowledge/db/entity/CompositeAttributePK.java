/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author nmsa
 */
@Embeddable
public class CompositeAttributePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "parentMetric", nullable = false)
    private int parentMetric;
    @Basic(optional = false)
    @Column(name = "childMetric", nullable = false)
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
