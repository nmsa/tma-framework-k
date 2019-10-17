/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin API
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.atmosphere.tma.admin.util.DatabaseManager;
import eu.atmosphere.tma.admin.dto.Resource;
import eu.atmosphere.tma.admin.util.Constants;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related to the resources.
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
public class ResourceAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceAdminController.class);

    @PostMapping("/addresource")
    public ResponseEntity<Map> addResource(@RequestBody Resource resource, HttpServletResponse response) {

        if (resource.invalidInputs()) {
            return resource.errorHandler(LOGGER);
        }

        //creates a new resource
        DatabaseManager database = new DatabaseManager();

        database.saveNewResource(resource);
        if (resource.getResourceId() == -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Resource created");
    }

    @GetMapping("/getresources")
    public ResponseEntity<Map> getResources(
            @RequestParam(required = true) String actuatorIdString,
            HttpServletResponse response) {

        int actuatorId;

        if (AdminController.isInputNotValid(actuatorIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the actuator id. Don't use characters like *, +, etc..");
        }
        try {
            actuatorId = Integer.parseInt(actuatorIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ActuatorId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Actuator id needs to be a number");
        }

        DatabaseManager database = new DatabaseManager();
        ArrayList<Resource> resources = database.getResources(actuatorId);

        if (resources == null) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Resource>> resourcesJson = new HashMap<>();
        resourcesJson.put("resources", resources);

        return new ResponseEntity<>(
                resourcesJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
}
