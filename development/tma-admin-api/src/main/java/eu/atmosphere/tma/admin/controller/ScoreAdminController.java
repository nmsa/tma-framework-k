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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import eu.atmosphere.tma.admin.util.DatabaseManager;
import eu.atmosphere.tma.admin.util.Constants;
import eu.atmosphere.tma.admin.dto.Metric;
import eu.atmosphere.tma.admin.dto.Score;
import java.sql.SQLException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class stores the information of an Action entry in the Action table.
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
public class ScoreAdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoreAdminController.class);
    
    @GetMapping("/get_scores")
    public ResponseEntity<Map> getScores(
            @RequestParam int metricId,
            @RequestParam long timestamp,
            HttpServletResponse response) {
        DatabaseManager database;
        ArrayList<Score> scores;

        database = new DatabaseManager();
        Metric metric = new Metric(metricId, timestamp);
        try {
            scores = database.getScores(metric);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Score>> scoresJson = new HashMap<>();
        scoresJson.put("scores", scores);

        return new ResponseEntity<>(
                scoresJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
}
