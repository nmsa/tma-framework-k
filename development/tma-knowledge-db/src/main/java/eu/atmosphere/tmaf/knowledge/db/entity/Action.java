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
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "Action")
@NamedQueries({
    @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a"),
    @NamedQuery(name = "Action.findByActionId", query = "SELECT a FROM Action a WHERE a.actionId = :actionId"),
    @NamedQuery(name = "Action.findByActionName", query = "SELECT a FROM Action a WHERE a.actionName = :actionName")})
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "actionId")
    private Integer actionId;
    @Column(name = "actionName")
    private String actionName;
    @JoinColumn(name = "resourceId", referencedColumnName = "resourceId")
    @ManyToOne(optional = false)
    private Resource resourceId;
    @JoinColumn(name = "actuatorId", referencedColumnName = "actuatorId")
    @ManyToOne(optional = false)
    private Actuator actuatorId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "action")
    private Collection<Configuration> configurationCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "action")
    private Collection<ActionPlan> actionPlanCollection;

    public Action() {
    }

    public Action(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Resource getResourceId() {
        return resourceId;
    }

    public void setResourceId(Resource resourceId) {
        this.resourceId = resourceId;
    }

    public Actuator getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(Actuator actuatorId) {
        this.actuatorId = actuatorId;
    }

    public Collection<Configuration> getConfigurationCollection() {
        return configurationCollection;
    }

    public void setConfigurationCollection(Collection<Configuration> configurationCollection) {
        this.configurationCollection = configurationCollection;
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
        hash += (actionId != null ? actionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        if ((this.actionId == null && other.actionId != null) || (this.actionId != null && !this.actionId.equals(other.actionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eu.atmosphere.tmaf.knowledge.db.entity.Action[ actionId=" + actionId + " ]";
    }
    
}
