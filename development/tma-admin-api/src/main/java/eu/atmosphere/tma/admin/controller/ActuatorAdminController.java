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
package eu.atmosphere.tma.admin.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import eu.atmosphere.tma.admin.util.DatabaseManager;
import eu.atmosphere.tma.admin.dto.Actuator;
import eu.atmosphere.tma.admin.util.Constants;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related to the actuator.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */
@CrossOrigin
@RestController
public class ActuatorAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActuatorAdminController.class);

    @PostMapping("/addactuator")
    public ResponseEntity<Map> addActuator(@RequestParam("address") String[] address,
            @RequestParam("file") MultipartFile[] publicKeyFile,
            @RequestParam("partnerId") String[] partnerIdString,
            HttpServletResponse response) {

        int partnerId;

        // Validate the entered address, since it is possible to insert more than
        // one address it checks every address that was inserted.
        if (address.length == 0) {
            LOGGER.warn("[ATMOSPHERE] Didn't receive an address");
            return AdminController.genericResponseEntity(Constants.HTTPUNSUPPORTEDMEDIATYPE, Constants.WARNING, "Wasn't given an address, please insert an address");
        }
        if (publicKeyFile.length == 0) {
            LOGGER.warn("[ATMOSPHERE] Didn't receive a file");
            return AdminController.genericResponseEntity(Constants.HTTPUNSUPPORTEDMEDIATYPE, Constants.WARNING, "Wasn't given a file, please insert a file");
        }
        if (partnerIdString.length == 0) {
            LOGGER.warn("[ATMOSPHERE] Didn't receive a partnerId");
            return AdminController.genericResponseEntity(Constants.HTTPUNSUPPORTEDMEDIATYPE, Constants.WARNING, "Wasn't given a partner id, please insert the partner id");
        }

        for (String input : address) {
            if (AdminController.isInputNotValid(input)) {
                LOGGER.warn("[ATMOSPHERE] Address input has special characters, may be dangerous to the system. Refusing the request");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the address. Don't use characters like *, +, etc..");
            }
        }

        for (String input : partnerIdString) {
            if (AdminController.isInputNotValid(input)) {
                LOGGER.warn("[ATMOSPHERE] PartnerIdString input has special characters, may be dangerous to the system. Refusing the request");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the partner Id. Don't use characters like *, +, etc..");
            }
        }

        try {
            partnerId = Integer.parseInt(partnerIdString[0]);
        } catch (NumberFormatException nfex) {
            LOGGER.error("[ATMOSPHERE] Invalid PartnerId - PartnerId given is not a number", nfex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Partner id given isn't a number");
        }

        //Validate the address
        try {
            URL url = new URL(address[0]);
        } catch (MalformedURLException murle) {
            LOGGER.error("[ATMOSPHERE] Invalid Address - Address given is an invalid URL", murle);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Address given isn't a valid URL");
        }

        byte[] pubKey;
        try {
            pubKey = publicKeyFile[0].getBytes();
        } catch (IOException ioe) {
            LOGGER.error("[ATMOSPHERE] Public key file corrupted", ioe);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Public key file corrupted");
        }
        if (pubKey.length > Constants.MAXKEYSYZE) {
            LOGGER.error("[ATMOSPHERE] Public key file with over " + Constants.MAXKEYSYZE + "bytes");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Public key file with over" + Constants.MAXKEYSYZE + "bytes");
        }

        Actuator newActuator = new Actuator(address[0], pubKey);
        newActuator.setPartnerId(partnerId);

        //creates a new actuator
        DatabaseManager database = new DatabaseManager();

        switch (database.isActuatorAddressRepeated(address[0])) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Actuator adress given is already used");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Actuator adress given is already used, please choose another address");
            default:
                LOGGER.warn("[ATMOSPHERE] Code was changed and the feature wasn't completly implemented");
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "Problem with the server, feature not completly implemented");
        }

        int actuatorId = database.saveNewActuator(newActuator);

        if (actuatorId == -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Actuator created");
    }

    @GetMapping("/getactuators")
    public ResponseEntity<Map> getActuators(HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ArrayList<Actuator> actuators = database.getActuators();

        if (actuators == null) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Actuator>> actuatorsJson = new HashMap<>();
        actuatorsJson.put("actuators", actuators);

        return new ResponseEntity<>(
                actuatorsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }

}
