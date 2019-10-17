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
 * This class stores the information of a Probe entry in the Probe table.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
public class Probe extends DataObject {

    private int partnerId;
    private int probeId;
    private String probeName;
    private String password;
    private String salt;
    private String token;
    private long tokenExpiration;
    private String tokenExpirationString;

    public Probe() {
        this.partnerId = -1;
    }

    public Probe(int partnerId, int probeId, String probeName, String password, String salt, String token, long tokenExpiration) {
        this(partnerId, probeName, password, salt, token, tokenExpiration);
        this.probeId = probeId;
    }

    public Probe(int partnerId, String probeName, String password, String salt, String token, long tokenExpiration) {
    	this(partnerId, probeName, salt, token);
        this.password = password;
    }

    public Probe(String probeName, String password, String salt, String token, long tokenExpiration) {
    	this(-1, probeName, password, salt, token, tokenExpiration);
    }

    public Probe(int probeId, String probeName, String salt, String token, String tokenExpirationString) {
    	this(-1, probeName, salt, token);
    	this.probeId = probeId;
        this.tokenExpirationString = tokenExpirationString;
    }

    public Probe(int partnerId, String probeName, String salt, String token) {
        this.partnerId = partnerId;
        this.probeName = probeName;
        this.salt = salt;
        this.token = token;
    }

    public boolean invalidInputs() {

        if (this.probeName == null || this.probeName.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] ProbeName isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "ProbeName is empty, please enter the name of the Probe";
            return true;
        }

        if (this.password == null || this.password.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Password isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Password is empty, please enter the password of the Probe";
            return true;
        }

        if (this.salt == null || this.salt.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Salt isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Salt is empty, please enter the salt of the Probe";
            return true;
        }

        if (this.token == null || this.token.compareTo("") == 0) {
            this.errorLogger = "[ATMOSPHERE] Token isn't valid, either NULL or an empty spring";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "Token is empty, please enter the token of the Probe";
            return true;
        }

        if (this.tokenExpiration <= System.currentTimeMillis()) {
            this.errorLogger = "[ATMOSPHERE] TokenExpiration isn't valid, date is inferior to current time";
            this.statusCode = Constants.HTTPBADREQUEST;
            this.errorMessage = "TokenExpiration is invalid, please enter a date after today";
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

    public int getProbeId() {
        return probeId;
    }

    public void setProbeId(int probeId) {
        this.probeId = probeId;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(long tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    public String getTokenExpirationString() {
        return tokenExpirationString;
    }

    public void setTokenExpirationString(String tokenExpirationString) {
        this.tokenExpirationString = tokenExpirationString;
    }
}
