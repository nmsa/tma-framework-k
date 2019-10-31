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
import eu.atmosphere.tma.admin.util.Constants;

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

}
