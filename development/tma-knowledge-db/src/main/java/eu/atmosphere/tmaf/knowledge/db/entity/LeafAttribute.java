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
@Table(name = "LeafAttribute", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "LeafAttribute.findAll", query = "SELECT l FROM LeafAttribute l"),
    @NamedQuery(name = "LeafAttribute.findByDescriptionId", query = "SELECT l FROM LeafAttribute l WHERE l.leafAttributePK.descriptionId = :descriptionId"),
    @NamedQuery(name = "LeafAttribute.findByMetricId", query = "SELECT l FROM LeafAttribute l WHERE l.leafAttributePK.metricId = :metricId"),
    @NamedQuery(name = "LeafAttribute.findByMetricAggregationOperator", query = "SELECT l FROM LeafAttribute l WHERE l.metricAggregationOperator = :metricAggregationOperator"),
    @NamedQuery(name = "LeafAttribute.findByNumSamples", query = "SELECT l FROM LeafAttribute l WHERE l.numSamples = :numSamples"),
    @NamedQuery(name = "LeafAttribute.findByWeight", query = "SELECT l FROM LeafAttribute l WHERE l.weight = :weight"),
    @NamedQuery(name = "LeafAttribute.findByNormalizationMethod", query = "SELECT l FROM LeafAttribute l WHERE l.normalizationMethod = :normalizationMethod"),
    @NamedQuery(name = "LeafAttribute.findByNormalizationKind", query = "SELECT l FROM LeafAttribute l WHERE l.normalizationKind = :normalizationKind")})
public class LeafAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LeafAttributePK leafAttributePK;
    @Column(name = "metricAggregationOperator")
    private Integer metricAggregationOperator;
    @Column(name = "numSamples")
    private Integer numSamples;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight", precision = 22)
    private Double weight;
    @Column(name = "normalizationMethod", length = 10)
    private String normalizationMethod;
    @Column(name = "normalizationKind", length = 10)
    private String normalizationKind;
    @JoinColumn(name = "descriptionId", referencedColumnName = "descriptionId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Description description;
    @JoinColumn(name = "metricId", referencedColumnName = "metricId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Metric metric;

    public LeafAttribute() {
    }

    public LeafAttribute(LeafAttributePK leafAttributePK) {
        this.leafAttributePK = leafAttributePK;
    }

    public LeafAttribute(int descriptionId, int metricId) {
        this.leafAttributePK = new LeafAttributePK(descriptionId, metricId);
    }

    public LeafAttributePK getLeafAttributePK() {
        return leafAttributePK;
    }

    public void setLeafAttributePK(LeafAttributePK leafAttributePK) {
        this.leafAttributePK = leafAttributePK;
    }

    public Integer getMetricAggregationOperator() {
        return metricAggregationOperator;
    }

    public void setMetricAggregationOperator(Integer metricAggregationOperator) {
        this.metricAggregationOperator = metricAggregationOperator;
    }

    public Integer getNumSamples() {
        return numSamples;
    }

    public void setNumSamples(Integer numSamples) {
        this.numSamples = numSamples;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getNormalizationMethod() {
        return normalizationMethod;
    }

    public void setNormalizationMethod(String normalizationMethod) {
        this.normalizationMethod = normalizationMethod;
    }

    public String getNormalizationKind() {
        return normalizationKind;
    }

    public void setNormalizationKind(String normalizationKind) {
        this.normalizationKind = normalizationKind;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
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
        hash += (leafAttributePK != null ? leafAttributePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeafAttribute)) {
            return false;
        }
        LeafAttribute other = (LeafAttribute) object;
        if ((this.leafAttributePK == null && other.leafAttributePK != null) || (this.leafAttributePK != null && !this.leafAttributePK.equals(other.leafAttributePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.LeafAttribute[ leafAttributePK=" + leafAttributePK + " ]";
    }
    
}
