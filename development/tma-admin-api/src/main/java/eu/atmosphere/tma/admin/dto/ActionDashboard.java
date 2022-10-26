package eu.atmosphere.tma.admin.dto;

import java.util.ArrayList;

/**
 * This class stores the information of an Action entry in the Action table [Based on 'Action' Class].
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class ActionDashboard extends DataObject{
    private int actionId;
    private int actuatorId;
    private int resourceId;
    private String actionName;
    private ArrayList<ConfigurationDashboard> configurations;

    public ActionDashboard(int actionId, int actuatorId, int resourceId, String actionName, ArrayList<ConfigurationDashboard> configurations) {
        this.actionId = actionId;
        this.actuatorId = actuatorId;
        this.resourceId = resourceId;
        this.actionName = actionName;
        this.configurations = configurations;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(int actuatorId) {
        this.actuatorId = actuatorId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public ArrayList<ConfigurationDashboard> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(ArrayList<ConfigurationDashboard> configurations) {
        this.configurations = configurations;
    }
    
    public void addConfiguration(ConfigurationDashboard conf) {
        this.configurations.add(conf);
    }
    
}
