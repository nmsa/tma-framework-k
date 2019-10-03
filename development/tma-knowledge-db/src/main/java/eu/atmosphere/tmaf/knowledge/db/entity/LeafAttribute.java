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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
@Table(name = "LeafAttribute")
@NamedQueries({
    @NamedQuery(name = "LeafAttribute.findAll", query = "SELECT l FROM LeafAttribute l"),
    @NamedQuery(name = "LeafAttribute.findByMetricId", query = "SELECT l FROM LeafAttribute l WHERE l.metricId = :metricId"),
    @NamedQuery(name = "LeafAttribute.findByMetricAggregationOperator", query = "SELECT l FROM LeafAttribute l WHERE l.metricAggregationOperator = :metricAggregationOperator"),
    @NamedQuery(name = "LeafAttribute.findByNumSamples", query = "SELECT l FROM LeafAttribute l WHERE l.numSamples = :numSamples"),
    @NamedQuery(name = "LeafAttribute.findByNormalizationMethod", query = "SELECT l FROM LeafAttribute l WHERE l.normalizationMethod = :normalizationMethod"),
    @NamedQuery(name = "LeafAttribute.findByNormalizationKind", query = "SELECT l FROM LeafAttribute l WHERE l.normalizationKind = :normalizationKind"),
    @NamedQuery(name = "LeafAttribute.findByMinimumThreshold", query = "SELECT l FROM LeafAttribute l WHERE l.minimumThreshold = :minimumThreshold"),
    @NamedQuery(name = "LeafAttribute.findByMaximumThreshold", query = "SELECT l FROM LeafAttribute l WHERE l.maximumThreshold = :maximumThreshold")})
public class LeafAttribute implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "metricId")
    private Integer metricId;
    @Column(name = "metricAggregationOperator")
    private Integer metricAggregationOperator;
    @Column(name = "numSamples")
    private Integer numSamples;
    @Column(name = "normalizationMethod")
    private String normalizationMethod;
    @Column(name = "normalizationKind")
    private String normalizationKind;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "minimumThreshold")
    private Double minimumThreshold;
    @Column(name = "maximumThreshold")
    private Double maximumThreshold;
    @JoinColumn(name = "descriptionId", referencedColumnName = "descriptionId")
    @ManyToOne(optional = false)
    private Description descriptionId;
    @JoinColumn(name = "metricId", referencedColumnName = "metricId", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Metric metric;

    public LeafAttribute() {
    }

    public LeafAttribute(Integer metricId) {
        this.metricId = metricId;
    }

    public Integer getMetricId() {
        return metricId;
    }

    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
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

    public Double getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(Double minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    public Double getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(Double maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }

    public Description getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Description descriptionId) {
        this.descriptionId = descriptionId;
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
        hash += (metricId != null ? metricId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeafAttribute)) {
            return false;
        }
        LeafAttribute other = (LeafAttribute) object;
        if ((this.metricId == null && other.metricId != null) || (this.metricId != null && !this.metricId.equals(other.metricId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.LeafAttribute[ metricId=" + metricId + " ]";
    }

}
