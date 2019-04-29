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
@Table(name = "QualityModel", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "QualityModel.findAll", query = "SELECT q FROM QualityModel q"),
    @NamedQuery(name = "QualityModel.findByQualityModelId", query = "SELECT q FROM QualityModel q WHERE q.qualityModelPK.qualityModelId = :qualityModelId"),
    @NamedQuery(name = "QualityModel.findByMetricId", query = "SELECT q FROM QualityModel q WHERE q.qualityModelPK.metricId = :metricId"),
    @NamedQuery(name = "QualityModel.findByModelName", query = "SELECT q FROM QualityModel q WHERE q.modelName = :modelName"),
    @NamedQuery(name = "QualityModel.findByModelDescriptionReference", query = "SELECT q FROM QualityModel q WHERE q.modelDescriptionReference = :modelDescriptionReference"),
    @NamedQuery(name = "QualityModel.findByBusinessThreshold", query = "SELECT q FROM QualityModel q WHERE q.businessThreshold = :businessThreshold")})
public class QualityModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected QualityModelPK qualityModelPK;
    @Column(name = "modelName", length = 10)
    private String modelName;
    @Column(name = "modelDescriptionReference")
    private Integer modelDescriptionReference;
    @Column(name = "businessThreshold", length = 10)
    private String businessThreshold;
    @JoinColumn(name = "metricId", referencedColumnName = "metricId", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Metric metric;

    public QualityModel() {
    }

    public QualityModel(QualityModelPK qualityModelPK) {
        this.qualityModelPK = qualityModelPK;
    }

    public QualityModel(int qualityModelId, int metricId) {
        this.qualityModelPK = new QualityModelPK(qualityModelId, metricId);
    }

    public QualityModelPK getQualityModelPK() {
        return qualityModelPK;
    }

    public void setQualityModelPK(QualityModelPK qualityModelPK) {
        this.qualityModelPK = qualityModelPK;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getModelDescriptionReference() {
        return modelDescriptionReference;
    }

    public void setModelDescriptionReference(Integer modelDescriptionReference) {
        this.modelDescriptionReference = modelDescriptionReference;
    }

    public String getBusinessThreshold() {
        return businessThreshold;
    }

    public void setBusinessThreshold(String businessThreshold) {
        this.businessThreshold = businessThreshold;
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
        hash += (qualityModelPK != null ? qualityModelPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QualityModel)) {
            return false;
        }
        QualityModel other = (QualityModel) object;
        if ((this.qualityModelPK == null && other.qualityModelPK != null) || (this.qualityModelPK != null && !this.qualityModelPK.equals(other.qualityModelPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.QualityModel[ qualityModelPK=" + qualityModelPK + " ]";
    }
    
}
