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
package eu.atmosphere.tma.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.security.SecureRandom;

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

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;

import eu.atmosphere.tma.admin.util.DatabaseManager;
import eu.atmosphere.tma.admin.dto.Probe;
import eu.atmosphere.tma.admin.util.Constants;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related to the probes.
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
public class ProbeAdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProbeAdminController.class);

    
    @PostMapping("/addprobe")
    public ResponseEntity<Map> addProbe(@RequestBody Probe probe, HttpServletResponse response) {
    	
        if (probe.invalidInputs()) {
            return probe.errorHandler(LOGGER);
        }

        //creates a new probe
        DatabaseManager database = new DatabaseManager();

        switch (database.isProbeNameRepeated(probe.getProbeName())) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Probe name given already exists");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Probe name already exists, please choose another name");
            default:
                LOGGER.warn("[ATMOSPHERE] Code was changed and the feature wasn't completly implemented");
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "Problem with the server, feature not completly implemented");
        }

        Argon2Advanced argon2 = Argon2Factory.createAdvanced(Argon2Factory.Argon2Types.ARGON2id);

        // Generate salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[128];
        random.nextBytes(salt);

        // Hashes a password and returns the raw bytes.
        // Uses UTF-8 encoding.

        //@param1 - number of iterations
        //@param2 - memory
        //@param3 - parallelism
        //@param4 - password
        //@param5 - salt
        byte[] hash = argon2.rawHash(4,1048576,4,probe.getPassword(), salt);
        probe.setPassword(new String(hash));
        probe.setSalt(new String(salt));

        database.saveNewProbe(probe);
        if (probe.getProbeId() == -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Probe created");
    }

    @GetMapping("/getprobes")
    public ResponseEntity<Map> getProbes(HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ArrayList<Probe> probes = database.getProbes();

        if (probes == null) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Probe>> probesJson = new HashMap<>();
        probesJson.put("probes", probes);

        return new ResponseEntity<>(
                probesJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }

	@DeleteMapping("/deleteprobe")
    public ResponseEntity<Map> deleteProbe(@RequestParam(required = true) String probeIdString, HttpServletResponse response) {
        int probeId;

        if (AdminController.isInputNotValid(probeIdString)) {
            LOGGER.warn("[ATMOSPHERE] Input has special characters, may be dangerous to the system. Refusing the request");
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.ERROR, "Forbidden characters in the action id. Don't use characters like *, +, etc..");
        }
        try {
            probeId = Integer.parseInt(probeIdString);
        } catch (NumberFormatException ex) {
            LOGGER.warn("[ATMOSPHERE] Invalid Parameter input - ProbeId needs to be a number", ex);
            return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Probe id needs to be a number");
        }

        DatabaseManager database = new DatabaseManager();
        if (!database.deleteProbe(probeId)) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }
        return AdminController.genericResponseEntity(Constants.HTTPSUCESSCREATED, Constants.SUCCESS, "Probe deleted sucessfully");
    }

}
