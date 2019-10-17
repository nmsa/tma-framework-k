/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 ***
 * <p>
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
 * This class stores the information of an Actuator entry in the Actuator table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Actuator extends DataObject {

    private int partnerId;
    private int actuatorId;
    private String address;
    private byte[] pubKeyBytes;
    private String pubKey;

    public Actuator() {
        this.partnerId = -1;
    }

    public Actuator(int partnerId, int actuatorId, String address, String pubKey) {
        this(partnerId, address, pubKey);
        this.actuatorId = actuatorId;
    }

    public Actuator(int partnerId, String address, String pubKey) {
        this.partnerId = partnerId;
        this.address = address;
        this.pubKey = pubKey;
    }

    public Actuator(String address, String pubKey) {
        this(-1, address, pubKey);
    }

    public Actuator(String address, byte[] pubKeyBytes) {
        this.partnerId = -1;
        this.address = address;
        this.pubKeyBytes = pubKeyBytes;
    }

    public boolean invalidInputs() {

        if (this.address == null || this.address.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Address isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Address is empty, please enter the address of the Actuator";
            return true;
        }

        if (this.pubKey == null || this.pubKey.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Public Key isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Public Key is empty, please enter the correct public key file";
            return true;
        }

        try {
            URL url = new URL(this.address);
        } catch (MalformedURLException murle) {
            this.errorLogger = "[ATMOSPHERE] Invalid Address - Address given is an invalid URL";
            this.exception = murle;
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Address given isn't a valid URL";
            return true;
        }

        return false;
    }

    public byte[] getPubKeyBytes() {
        return pubKeyBytes;
    }

    public void setPubKeyBytes(byte[] pubKeyBytes) {
        this.pubKeyBytes = pubKeyBytes;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getActuatorId() {
        return actuatorId;
    }

    public void setActuatorId(int actuatorId) {
        this.actuatorId = actuatorId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

}
