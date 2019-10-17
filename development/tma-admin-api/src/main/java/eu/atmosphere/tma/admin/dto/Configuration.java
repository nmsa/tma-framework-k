/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 ***
 * <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin API
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.dto;

import eu.atmosphere.tma.admin.util.Constants;

/**
 * This class stores the information of a Configuration entry in the
 * Configuration table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Configuration extends DataObject {

    private int partnerId;
    private int configurationId;
    private int actionId;
    private String keyName;
    private String domain;

    public boolean invalidInputs() {

        if (this.actionId <= 0) {
            this.errorLogger = "[ATMOSPHERE] ActionId isn't valid, it is either 0 or a negative ";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Action selected isn't valid, please select a valid Action";
            return true;
        }

        if (this.keyName == null || this.keyName.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] KeyName isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "KeyName is empty, please enter the keyName of the Configuration";
            return true;
        }

        if (this.domain == null || this.domain.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Domain isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Domain is empty, please enter the domain of the Configuration";
            return true;
        }

        return false;
    }

    public Configuration() {
        this.partnerId = -1;
    }

    public Configuration(int partnerId, int configurationId, int actionId, String keyName, String domain) {
        this(partnerId, actionId, keyName, domain);
        this.configurationId = configurationId;
    }

    public Configuration(int partnerId, int actionId, String keyName, String domain) {
        this.partnerId = partnerId;
        this.actionId = actionId;
        this.keyName = keyName;
        this.domain = domain;
    }

    public Configuration(int actionId, String keyName, String domain) {
        this(-1, actionId, keyName, domain);
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
