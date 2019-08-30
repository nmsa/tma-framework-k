/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "CompositeAttribute", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "CompositeAttribute.findAll", query = "SELECT c FROM CompositeAttribute c"),
    @NamedQuery(name = "CompositeAttribute.findByParentMetric", query = "SELECT c FROM CompositeAttribute c WHERE c.compositeAttributePK.parentMetric = :parentMetric"),
    @NamedQuery(name = "CompositeAttribute.findByChildMetric", query = "SELECT c FROM CompositeAttribute c WHERE c.compositeAttributePK.childMetric = :childMetric"),
    @NamedQuery(name = "CompositeAttribute.findByAttributeAggregationOperator", query = "SELECT c FROM CompositeAttribute c WHERE c.attributeAggregationOperator = :attributeAggregationOperator")})
public class CompositeAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CompositeAttributePK compositeAttributePK;
    @Column(name = "attributeAggregationOperator")
    private Integer attributeAggregationOperator;
    @JoinColumn(name = "parentMetric", referencedColumnName = "metricId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Metric metric;
    @JoinColumn(name = "childMetric", referencedColumnName = "metricId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Metric metric1;

    public CompositeAttribute() {
    }

    public CompositeAttribute(CompositeAttributePK compositeAttributePK) {
        this.compositeAttributePK = compositeAttributePK;
    }

    public CompositeAttribute(int parentMetric, int childMetric) {
        this.compositeAttributePK = new CompositeAttributePK(parentMetric, childMetric);
    }

    public CompositeAttributePK getCompositeAttributePK() {
        return compositeAttributePK;
    }

    public void setCompositeAttributePK(CompositeAttributePK compositeAttributePK) {
        this.compositeAttributePK = compositeAttributePK;
    }

    public Integer getAttributeAggregationOperator() {
        return attributeAggregationOperator;
    }

    public void setAttributeAggregationOperator(Integer attributeAggregationOperator) {
        this.attributeAggregationOperator = attributeAggregationOperator;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public Metric getMetric1() {
        return metric1;
    }

    public void setMetric1(Metric metric1) {
        this.metric1 = metric1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (compositeAttributePK != null ? compositeAttributePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompositeAttribute)) {
            return false;
        }
        CompositeAttribute other = (CompositeAttribute) object;
        if ((this.compositeAttributePK == null && other.compositeAttributePK != null) || (this.compositeAttributePK != null && !this.compositeAttributePK.equals(other.compositeAttributePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.CompositeAttribute[ compositeAttributePK=" + compositeAttributePK + " ]";
    }
    
}
