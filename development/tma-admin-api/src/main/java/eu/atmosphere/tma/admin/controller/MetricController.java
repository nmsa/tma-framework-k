package eu.atmosphere.tma.admin.controller;

import eu.atmosphere.tma.admin.dto.MetricDashboard;
import eu.atmosphere.tma.admin.util.Constants;
import eu.atmosphere.tma.admin.util.DatabaseManager;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */

@CrossOrigin
@RestController
public class MetricController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricController.class);

    @GetMapping("/getMetrics")
    public ResponseEntity<Map> getMetrics(
            @RequestParam(required = false, defaultValue="",name = "filter") String filter,
            @RequestParam(required = false, defaultValue="false",name = "createQualityModel") boolean createQualityModel,
            HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ArrayList<MetricDashboard> metrics;
        try {
            metrics = database.getMetrics(filter,createQualityModel);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<MetricDashboard>> metricsJson = new HashMap<>();
        metricsJson.put("metrics", metrics);

        return new ResponseEntity<>(
                metricsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    @GetMapping("/getMetrics/{id}")
    public ResponseEntity<Map> getMetricById(@PathVariable(name= "id") int id, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        MetricDashboard metric;
        try {
            metric = database.getMetricById(id);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, MetricDashboard> metricJson = new HashMap<>();
        metricJson.put("metric", metric);

        return new ResponseEntity<>(
                metricJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    @PostMapping("/createMetric")
    public ResponseEntity<Map> createQualityModel(@RequestBody MetricDashboard metric, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        switch (database.isMetricNameRepeated(metric.getMetricName())) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Metric name given already exists");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Metric name already exists, please choose another name");
        }
        
        //If it isn't a leaf attribute, then check for existing metric with the same set of childs
        if(metric.getChildMetrics().size() > 0){
            switch (database.isMetricAlreadyExisting(metric.getChildMetrics())) {
                case -1:
                    return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
                case 0:
                    break;
                case 1:
                    LOGGER.warn("[ATMOSPHERE] Exact set of child metrics given already exists for another metric");
                    return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Exact same set of child metrics already exists for another metric, please choose another set of child metrics");
            }
        }
        
        database.saveNewMetric(metric);
        if (metric.getMetricId()== -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database. Try again later.");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESS, Constants.SUCCESS, "Metric created");
    }

}
