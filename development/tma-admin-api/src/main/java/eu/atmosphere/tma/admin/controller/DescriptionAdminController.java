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

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import eu.atmosphere.tma.admin.util.DatabaseManager;
import eu.atmosphere.tma.admin.dto.Description;
import eu.atmosphere.tma.admin.dto.MetricDashboard;
import eu.atmosphere.tma.admin.util.Constants;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related to the descriptions.
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
public class DescriptionAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DescriptionAdminController.class);

    

    @PostMapping("/adddescription")
    public ResponseEntity<Map> addDescription(@RequestBody Description description, HttpServletResponse response) {

        if (description.invalidInputs()) {
            return description.errorHandler(LOGGER);
        }

        //creates a new description
        DatabaseManager database = new DatabaseManager();
        database.saveNewDescription(description);
        if (description.getDescriptionId() == -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Description created");
    }
    
    /**
    * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
    */
    @GetMapping("/getDescriptions")
    public ResponseEntity<Map> getDescriptions(@RequestParam(required = false, defaultValue="",name = "filter") String filter, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ArrayList<Description> descriptions;
        try {
            descriptions = database.getDescriptions(filter);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Description>> descriptionsJson = new HashMap<>();
        descriptionsJson.put("descriptions", descriptions);

        return new ResponseEntity<>(
                descriptionsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    

}
