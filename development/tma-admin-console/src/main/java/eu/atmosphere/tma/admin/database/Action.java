package eu.atmosphere.tma.admin.database;

import java.util.ArrayList;
import java.util.List;

public class Action {

    private int actuatorId;
    private int resourceId;
    private String actionName;
    private List<Configuration> configuration;

    public Action() {
        this.configuration = new ArrayList<Configuration>();
    }

    public Action(int actuatorId, int resourceId, String actionName) {
        this.configuration = new ArrayList<Configuration>();
        this.actuatorId = actuatorId;
        this.resourceId = resourceId;
        this.actionName = actionName;
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

    public void addConfiguration(Configuration conf) {
        this.configuration.add(conf);
    }

    public List<Configuration> getConfiguration() {
        return configuration;
    }
}
