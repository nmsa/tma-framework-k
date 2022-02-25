/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */

@CrossOrigin
@RestController
public class MetricController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricController.class);

    @GetMapping("/getMetrics")
    public ResponseEntity<Map> getMetrics(@RequestParam(required = false, defaultValue="",name = "filter") String filter, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ArrayList<MetricDashboard> metrics;
        try {
            metrics = database.getMetrics(filter);
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

}
