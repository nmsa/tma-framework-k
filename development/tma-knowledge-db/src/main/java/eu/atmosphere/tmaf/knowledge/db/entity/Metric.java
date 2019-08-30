/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author nmsa
 */
@Entity
@Table(name = "Metric")
@NamedQueries({
    @NamedQuery(name = "Metric.findAll", query = "SELECT m FROM Metric m"),
    @NamedQuery(name = "Metric.findByMetricId", query = "SELECT m FROM Metric m WHERE m.metricId = :metricId"),
    @NamedQuery(name = "Metric.findByMetricName", query = "SELECT m FROM Metric m WHERE m.metricName = :metricName"),
    @NamedQuery(name = "Metric.findByBlockLevel", query = "SELECT m FROM Metric m WHERE m.blockLevel = :blockLevel"),
    @NamedQuery(name = "Metric.findByWeight", query = "SELECT m FROM Metric m WHERE m.weight = :weight")})
public class Metric implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "metricId")
    private Integer metricId;
    @Column(name = "metricName")
    private String metricName;
    @Column(name = "blockLevel")
    private Integer blockLevel;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Double weight;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metric")
    private Collection<QualityModel> qualityModelCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metric")
    private Collection<CompositeAttribute> compositeAttributeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metric1")
    private Collection<CompositeAttribute> compositeAttributeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metric")
    private Collection<Preference> preferenceCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metric")
    private Collection<LeafAttribute> leafAttributeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "metric")
    private Collection<MetricData> metricDataCollection;

    public Metric() {
    }

    public Metric(Integer metricId) {
        this.metricId = metricId;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }

    public Integer getBlockLevel() {
        return blockLevel;
    }

    public void setBlockLevel(Integer blockLevel) {
        this.blockLevel = blockLevel;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Collection<QualityModel> getQualityModelCollection() {
        return qualityModelCollection;
    }

    public void setQualityModelCollection(Collection<QualityModel> qualityModelCollection) {
        this.qualityModelCollection = qualityModelCollection;
    }

    public Collection<CompositeAttribute> getCompositeAttributeCollection() {
        return compositeAttributeCollection;
    }

    public void setCompositeAttributeCollection(Collection<CompositeAttribute> compositeAttributeCollection) {
        this.compositeAttributeCollection = compositeAttributeCollection;
    }

    public Collection<CompositeAttribute> getCompositeAttributeCollection1() {
        return compositeAttributeCollection1;
    }

    public void setCompositeAttributeCollection1(Collection<CompositeAttribute> compositeAttributeCollection1) {
        this.compositeAttributeCollection1 = compositeAttributeCollection1;
    }

    public Collection<Preference> getPreferenceCollection() {
        return preferenceCollection;
    }

    public void setPreferenceCollection(Collection<Preference> preferenceCollection) {
        this.preferenceCollection = preferenceCollection;
    }

    public Collection<LeafAttribute> getLeafAttributeCollection() {
        return leafAttributeCollection;
    }

    public void setLeafAttributeCollection(Collection<LeafAttribute> leafAttributeCollection) {
        this.leafAttributeCollection = leafAttributeCollection;
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
        hash += (metricId != null ? metricId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Metric)) {
            return false;
        }
        Metric other = (Metric) object;
        if ((this.metricId == null && other.metricId != null) || (this.metricId != null && !this.metricId.equals(other.metricId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Metric[ metricId=" + metricId + " ]";
    }
    
}
