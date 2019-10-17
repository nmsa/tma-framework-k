/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.dto;

import java.util.ArrayList;
import java.util.List;
import eu.atmosphere.tma.admin.util.Constants;

/**
 * This class stores the information of an Action entry in the Action table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Action extends DataObject {

    private int partnerId;
    private int actionId;
    private int actuatorId;
    private int resourceId;
    private String actionName;
    private String resourceName;
    private List<Configuration> configurations;

    public boolean invalidInputs() {
        if (this.actionName == null || this.actionName.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] ActionName isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "ActionName is empty, please enter the name of the Action";
            return true;
        }

        if (this.actuatorId <= 0) {
            this.errorLogger = "[ATMOSPHERE] Actuator isn't valid, actuatorId value is 0 or negative";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Actuator choosen is invalid, actuatorId value is 0 or negative";
            return true;
        }

        if (this.resourceId <= 0) {
            this.errorLogger = "[ATMOSPHERE] Resource isn't valid, resourceId value is 0 or negative";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Resource choosen is invalid, resourceId value is 0 or negative";
            return true;
        }

        if (this.actuatorId / 1000 != this.resourceId / 1000) {
            this.errorLogger = "[ATMOSPHERE] PartnerId of actuator is diferent from the resource.";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "PartnerId of actuator is diferent from the resource. Choose a resource/actuator combination with the same partnerId";
            return true;
        }

        return false;
    }

    public Action() {
        this.partnerId = -1;
        this.configurations = new ArrayList<>();
    }

    public Action(int partnerId, int actionId, int actuatorId, int resourceId, String actionName, String resourceName, List<Configuration> configuration) {
        this(partnerId, actuatorId, resourceId, actionName, resourceName, configuration);
        this.actionId = actionId;
    }

    public Action(int partnerId, int actuatorId, int resourceId, String actionName, String resourceName, List<Configuration> configuration) {
        this(partnerId, actuatorId, resourceId, actionName, configuration);
        this.resourceName = resourceName;
    }

    public Action(int actuatorId, int resourceId, String actionName) {
        this(-1, actuatorId, resourceId, actionName, new ArrayList<>());
    }

    public Action(int partnerId, int actuatorId, int resourceId, String actionName, List<Configuration> configuration) {
        this.partnerId = partnerId;
        this.actuatorId = actuatorId;
        this.resourceId = resourceId;
        this.actionName = actionName;
        this.configurations = configuration;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getActionId() {
        return actionId;
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
        this.configurations.add(conf);
    }

    public List<Configuration> getConfigurations() {
        return configurations;
    }

    public void setConfiguration(List<Configuration> configuration) {
        this.configurations = configuration;
    }
}
