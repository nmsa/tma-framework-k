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
package eu.atmosphere.tmaf.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.atmosphere.tmaf.admin.util.DatabaseManager;
import eu.atmosphere.tmaf.admin.dto.Action;
import eu.atmosphere.tmaf.admin.dto.Configuration;
import eu.atmosphere.tmaf.admin.util.Constants;

/**
 * This class is a Rest Controller.
 * It handles every request made to the server related to the actions.
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
public class ActionAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionAdminController.class);

    @PostMapping("/addaction")
    public ResponseEntity<Map> addAction(@RequestBody Action action, HttpServletResponse response) {

        if (action.invalidInputs()) {
            return action.errorHandler(LOGGER);
        }

        action.setPartnerId(action.getActuatorId() / Constants.IDSPERPARTNER);

        DatabaseManager database = new DatabaseManager();

        switch (database.isActionNameRepeated(action)) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Action name given already exists for the selected actuator");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Action name given already exists for the selected actuator, please choose another name");
            default:
                LOGGER.warn("[ATMOSPHERE] Code was changed and the feature wasn't completly implemented");
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "Problem with the server, feature not implemented completly");
        }

        database.saveNewAction(action);
        if (action.getActionId() == -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }
        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Action created");
    }

    @PostMapping("/addconfiguration")
    public ResponseEntity<Map> addConfiguration(@RequestBody Configuration configuration, HttpServletResponse response) {
        HashMap<String, Object> json = new HashMap<>();

        if (configuration.invalidInputs()) {
            return configuration.errorHandler(LOGGER);
        }

        configuration.setPartnerId(configuration.getActionId() / Constants.IDSPERPARTNER);

        DatabaseManager database = new DatabaseManager();

        switch (database.isConfigurationNameRepeated(configuration)) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Configuration keyName given already exists for the selected action");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Configuration key given already exists for the selected action, please choose another one");
            default:
                LOGGER.warn("[ATMOSPHERE] Code was changed and the feature wasn't completly implemented");
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "Problem with the server, feature not implemented completly");
        }

        database.saveNewConfiguration(configuration);
        if (configuration.getConfigurationId() == -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        json.put("status", "" + Constants.HTTPSUCESSCREATED);
        json.put("message", "Configuration created");
        json.put("messageType", "success");
        json.put("configurationId", configuration.getConfigurationId());
        return new ResponseEntity<>(json, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping("/configureactions")
    public ResponseEntity<Map> configureActions(@RequestParam(required = true) String actuatorIdString,
            @RequestBody Map<String, Object> actionsJson,
            HttpServletResponse response) {
        DatabaseManager database;

        ArrayList<Action> actions;

        //Validate the entered address, since it is possible to insert more than
        //one address it checks every address that was inserted.
        Integer actuatorId;
        if (AdminController.isInputNotValid(actuatorIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the actuator Id. Don't use characters like *, +, etc..");
        }
        try {
            actuatorId = Integer.parseInt(actuatorIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ActuatorId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Actuator id needs to be a number");
        }

        try {
            actions = (ArrayList<Action>) actionsJson.get("actions");
        } catch (ClassCastException ccex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Json input - no actions key", ccex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Invalid Json input - no actions key");
        }

        database = new DatabaseManager();
        for (Action action : actions) {
            action.setActuatorId(actuatorId);
            if (database.saveNewAction(action) == -1) {
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            }
        }

        for (Action action : actions) {
            database = new DatabaseManager();
            for (Configuration configuration : action.getConfigurations()) {
                configuration.setActionId(action.getActionId());
                configuration.setPartnerId(action.getActionId() / Constants.IDSPERPARTNER);
                if (database.saveNewConfiguration(configuration) == -1) {
                    return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
                }
            }
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Configurations created");
    }

    @GetMapping("/getactions")
    public ResponseEntity<Map> getActions(
            @RequestParam(required = true) String actuatorIdString,
            HttpServletResponse response) {

        int actuatorId;

        if (AdminController.isInputNotValid(actuatorIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Forbidden characters in the actuator id. Don't use characters like *, +, etc..");
        }
        try {
            actuatorId = Integer.parseInt(actuatorIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ActuatorId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Actuator id needs to be a number");
        }

        DatabaseManager database = new DatabaseManager();
        ArrayList<Action> actions = database.getActions(actuatorId);

        if (actions == null) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Action>> actionsJson = new HashMap<>();
        actionsJson.put("actions", actions);

        return new ResponseEntity<>(
                actionsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    @GetMapping("/getconfigurations")
    public ResponseEntity<Map> getConfigurations(@RequestParam(required = true) String actionIdString, HttpServletResponse response) {
        //Validate the entered address, since it is possible to insert more than
        //one address it checks every address that was inserted.
        Integer actionId;
        if (AdminController.isInputNotValid(actionIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the action id. Don't use characters like *, +, etc..");
        }
        try {
            actionId = Integer.parseInt(actionIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ActionId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Action id needs to be a number");
        }

        DatabaseManager database = new DatabaseManager();
        ArrayList<Configuration> configurations = database.getConfigurations(actionId);

        if (configurations == null) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Configuration>> configurationsJson = new HashMap<>();
        configurationsJson.put("configurations", configurations);

        return new ResponseEntity<>(
                configurationsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    //
    //DELETE METHODS
    //
    @DeleteMapping("/deleteaction")
    public ResponseEntity<Map> deleteAction(@RequestParam(required = true) String actionIdString, HttpServletResponse response) {
        int actionId;

        if (AdminController.isInputNotValid(actionIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the action id. Don't use characters like *, +, etc..");
        }
        try {
            actionId = Integer.parseInt(actionIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ActionId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Action id needs to be a number");
        }

        DatabaseManager database = new DatabaseManager();
        if (!database.deleteAction(actionId)) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }
        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Action deleted sucessfully");
    }

    @DeleteMapping("/deleteconfiguration")
    public ResponseEntity<Map> deleteConfiguration(@RequestParam(required = true) String configurationIdString, HttpServletResponse response) {
        int configurationId;

        if (AdminController.isInputNotValid(configurationIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the configuration id. Don't use characters like *, +, etc..");
        }
        try {
            configurationId = Integer.parseInt(configurationIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ConfigurationId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Configuration id needs to be a number");
        }

        DatabaseManager database = new DatabaseManager();
        if (!database.deleteConfiguration(configurationId)) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Configuration deleted sucessfully");
    }

}
