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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "Plan", catalog = "tmak", schema = "")
@NamedQueries({
    @NamedQuery(name = "Plan.findAll", query = "SELECT p FROM Plan p"),
    @NamedQuery(name = "Plan.findByPlanId", query = "SELECT p FROM Plan p WHERE p.planId = :planId"),
    @NamedQuery(name = "Plan.findByStatus", query = "SELECT p FROM Plan p WHERE p.status = :status")})
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "planId", nullable = false)
    private Integer planId;
    @Column(name = "status")
    private Integer status;
    @JoinColumns({
        @JoinColumn(name = "metricId", referencedColumnName = "metricId", nullable = false),
        @JoinColumn(name = "valueTime", referencedColumnName = "valueTime", nullable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private MetricData metricData;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plan", fetch = FetchType.LAZY)
    private Collection<ActionPlan> actionPlanCollection;

    public Plan() {
    }

    public Plan(Integer planId) {
        this.planId = planId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public MetricData getMetricData() {
        return metricData;
    }

    public void setMetricData(MetricData metricData) {
        this.metricData = metricData;
    }

    public Collection<ActionPlan> getActionPlanCollection() {
        return actionPlanCollection;
    }

    public void setActionPlanCollection(Collection<ActionPlan> actionPlanCollection) {
        this.actionPlanCollection = actionPlanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planId != null ? planId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plan)) {
            return false;
        }
        Plan other = (Plan) object;
        if ((this.planId == null && other.planId != null) || (this.planId != null && !this.planId.equals(other.planId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Plan[ planId=" + planId + " ]";
    }
    
}
