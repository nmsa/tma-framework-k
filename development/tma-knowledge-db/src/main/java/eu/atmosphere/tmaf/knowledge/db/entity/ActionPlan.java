/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tmaf.knowledge.db.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author nmsa
 */
@Entity
@Table(name = "ActionPlan")
@NamedQueries({
    @NamedQuery(name = "ActionPlan.findAll", query = "SELECT a FROM ActionPlan a"),
    @NamedQuery(name = "ActionPlan.findByPlanId", query = "SELECT a FROM ActionPlan a WHERE a.actionPlanPK.planId = :planId"),
    @NamedQuery(name = "ActionPlan.findByActionId", query = "SELECT a FROM ActionPlan a WHERE a.actionPlanPK.actionId = :actionId"),
    @NamedQuery(name = "ActionPlan.findByExecutionOrder", query = "SELECT a FROM ActionPlan a WHERE a.executionOrder = :executionOrder"),
    @NamedQuery(name = "ActionPlan.findByStatus", query = "SELECT a FROM ActionPlan a WHERE a.status = :status")})
public class ActionPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActionPlanPK actionPlanPK;
    @Column(name = "executionOrder")
    private Integer executionOrder;
    @Column(name = "status")
    private Integer status;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "actionPlan")
    private ConfigurationData configurationData;
    @JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Plan plan;
    @JoinColumn(name = "actionId", referencedColumnName = "actionId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Action action;

    public ActionPlan() {
    }

    public ActionPlan(ActionPlanPK actionPlanPK) {
        this.actionPlanPK = actionPlanPK;
    }

    public ActionPlan(int planId, int actionId) {
        this.actionPlanPK = new ActionPlanPK(planId, actionId);
    }

    public ActionPlanPK getActionPlanPK() {
        return actionPlanPK;
    }

    public void setActionPlanPK(ActionPlanPK actionPlanPK) {
        this.actionPlanPK = actionPlanPK;
    }

    public Integer getExecutionOrder() {
        return executionOrder;
    }

    public void setExecutionOrder(Integer executionOrder) {
        this.executionOrder = executionOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ConfigurationData getConfigurationData() {
        return configurationData;
    }

    public void setConfigurationData(ConfigurationData configurationData) {
        this.configurationData = configurationData;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actionPlanPK != null ? actionPlanPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActionPlan)) {
            return false;
        }
        ActionPlan other = (ActionPlan) object;
        if ((this.actionPlanPK == null && other.actionPlanPK != null) || (this.actionPlanPK != null && !this.actionPlanPK.equals(other.actionPlanPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.ActionPlan[ actionPlanPK=" + actionPlanPK + " ]";
    }
    
}
