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
package eu.atmosphere.tmaf.admin.dto;

import java.net.MalformedURLException;
import java.net.URL;
import eu.atmosphere.tmaf.admin.util.Constants;

/**
 * This class stores the information of a Resource entry in the Resource table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Resource extends DataObject {

    private int partnerId;
    private int resourceId;
    private String resourceName;
    private String resourceType;
    private String resourceAddress;

    public Resource() {
        this.partnerId = -1;
    }

    public Resource(int partnerId, int resourceId, String resourceName, String resourceType, String resourceAddress) {
    	this(partnerId, resourceName, resourceType, resourceAddress);
        this.resourceId = resourceId;
    }

    public Resource(int partnerId, String resourceName, String resourceType, String resourceAddress) {
        this.partnerId = partnerId;
        this.resourceName = resourceName;
        this.resourceType = resourceType;
        this.resourceAddress = resourceAddress;
    }

    public Resource(String resourceName, String resourceType, String resourceAddress) {
    	this(-1, resourceName, resourceType, resourceAddress);
    }

    public boolean invalidInputs() {

        if (this.resourceName == null || this.resourceName.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] ResourceName isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "ResourceName is empty, please enter the name of the Resource";
            return true;
        }

        if (this.resourceType == null || this.resourceType.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] ResourceType isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "ResourceType is empty, please enter the type of the Resource";
            return true;
        }

        if (this.resourceAddress == null || this.resourceAddress.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] ResourceAddress isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "ResourceAddress is empty, please enter the address of the Resource";
            return true;
        }

        try {
            URL url = new URL(this.resourceAddress);
        } catch (MalformedURLException murle) {
            this.errorLogger = "[ATMOSPHERE] Invalid Address - Address given is an invalid URL";
            this.exception = murle;
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Address given isn't a valid URL";
            return true;
        }

        return false;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceAddress() {
        return resourceAddress;
    }

    public void setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
    }

}
