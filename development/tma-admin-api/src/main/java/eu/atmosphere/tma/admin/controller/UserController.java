package eu.atmosphere.tma.admin.controller;

import eu.atmosphere.tma.admin.dto.PlotConfig;
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
import org.springframework.web.bind.annotation.RestController;

import eu.atmosphere.tma.admin.util.DatabaseManager;
import eu.atmosphere.tma.admin.util.Constants;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * This class is a Rest Controller. It handles every request made to the
 * server related with User information.
 *
 * @author João Ribeiro  <jdribeiro@student.dei.uc.pt>
 */
@CrossOrigin
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceAdminController.class);

    @PostMapping("/addPlotConfig")
    public ResponseEntity<Map> addPlotConfig(@RequestBody PlotConfig plotConfig, HttpServletResponse response) {
        //creates a new plot config in the database
        DatabaseManager database = new DatabaseManager();
        switch(database.isPlotConfigNameRepeated(plotConfig.getPlotConfigName())){
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Plot configuration name given already exists");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Plot configuration name already exists, please choose another name");
                
        }
        database.saveNewPlotConfig(plotConfig);
        if (plotConfig.getPlotConfigId()== -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }
        
        HashMap<String, Integer> plotConfigIdJson = new HashMap<>();
        plotConfigIdJson.put("plotConfigId", plotConfig.getPlotConfigId());
        
        return new ResponseEntity<>(
                plotConfigIdJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    @GetMapping("/getPlotsConfigs")
    public ResponseEntity<Map> getPlotsConfigs(HttpServletResponse response) {
        //creates a new plot config in the database
        DatabaseManager database = new DatabaseManager();
        ArrayList<PlotConfig> plotsConfigs = new ArrayList();
        
        try {
            plotsConfigs = database.getPlotsConfigs();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<PlotConfig>> plotsConfigsJson = new HashMap<>();
        plotsConfigsJson.put("plotsConfigs", plotsConfigs);

        return new ResponseEntity<>(
                plotsConfigsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    @PutMapping("/replacePlotConfig")
    public ResponseEntity<Map> replacePlotConfig(@RequestBody PlotConfig plotConfig, HttpServletResponse response) {
        //creates a new plot config in the database
        DatabaseManager database = new DatabaseManager();
        switch(database.isPlotConfigNameRepeated(plotConfig.getPlotConfigName())){
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Plot configuration name given already exists");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Plot configuration name already exists, please choose another name");
                
        }
        if(!database.updatePlotConfig(plotConfig)){
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }
        
        return AdminController.genericResponseEntity(Constants.HTTPSUCESS, Constants.SUCCESS, "Plot configuration updated!");
    }
    
    @DeleteMapping("/deletePlotConfig/{id}")
    public ResponseEntity<Map> deletePlotConfig(@PathVariable(name= "id") int plotConfigId, HttpServletResponse response) {
        //creates a new plot config in the database
        DatabaseManager database = new DatabaseManager();
        if(!database.deletePlotConfig(plotConfigId)){
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }
        
        return AdminController.genericResponseEntity(Constants.HTTPSUCESS, Constants.SUCCESS, "Plot configuration deleted!");
    }
    
}
