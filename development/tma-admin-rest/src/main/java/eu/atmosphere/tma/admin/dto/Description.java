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

import eu.atmosphere.tma.admin.util.Constants;

/**
 * This class stores the information of a Description entry in the Description
 * table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Description extends DataObject {

    private int partnerId;
    private int descriptionId;
    private String dataType;
    private String descriptionName;
    private String unit;

    public Description() {
        this.partnerId = -1;
    }

    public Description(int partnerId, int descriptionId, String dataType, String descriptionName, String unit) {
    	this(partnerId, dataType, descriptionName, unit);
        this.descriptionId = descriptionId;
    }

    public Description(int partnerId, String dataType, String descriptionName, String unit) {
        this.partnerId = partnerId;
        this.dataType = dataType;
        this.descriptionName = descriptionName;
        this.unit = unit;
    }

    public Description(String dataType, String descriptionName, String unit) {
    	this(-1, dataType, descriptionName, unit);
    }

    public boolean invalidInputs() {

        if (this.dataType == null || this.dataType.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] DataType isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "DataType is empty, please enter the type of the data of the Description";
            return true;
        }

        if (this.descriptionName == null || this.descriptionName.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] DescriptionName isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "DescriptionName is empty, please enter the name of the Description";
            return true;
        }

        if (this.unit == null || this.unit.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Unit isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Unit is empty, please enter the unit of the data of the Description";
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

    public int getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(int descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescriptionName() {
        return descriptionName;
    }

    public void setDescriptionName(String descriptionName) {
        this.descriptionName = descriptionName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
