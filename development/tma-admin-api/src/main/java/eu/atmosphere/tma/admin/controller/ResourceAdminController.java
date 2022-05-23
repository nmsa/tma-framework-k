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

import eu.atmosphere.tma.admin.dto.ConfigurationProfile;
import eu.atmosphere.tma.admin.dto.DataObject;
import eu.atmosphere.tma.admin.dto.DataSetElem;
import eu.atmosphere.tma.admin.dto.DataSetPlot;
import eu.atmosphere.tma.admin.dto.MetricDashboard;
import eu.atmosphere.tma.admin.dto.QualityModel;
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
import eu.atmosphere.tma.admin.dto.SimulationData;
import eu.atmosphere.tma.admin.util.Constants;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.logging.Level;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        ArrayList<Resource> resources;
        try {
            resources = database.getResources(actuatorId);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Resource>> resourcesJson = new HashMap<>();
        resourcesJson.put("resources", resources);

        return new ResponseEntity<>(
                resourcesJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    /**
    * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
    * 
    * Get list of currently active resources
    */
    @GetMapping("/getResources")
    public ResponseEntity<Map> getResourcesMonitored( 
            @RequestParam(required = false, name = "createRule", defaultValue = "false") boolean createRule, 
            HttpServletResponse response) {
        
        DatabaseManager database = new DatabaseManager();
        ArrayList<Resource> monitoredResources;
        try {
            monitoredResources = database.getMonitoredResources(createRule);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<Resource>> monitoredResourcesJson = new HashMap<>();
        monitoredResourcesJson.put("resources", monitoredResources);

        return new ResponseEntity<>(
                monitoredResourcesJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    /**
    * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
    * 
    * Gets the configuration profile and corresponding metrics tree associated to a resouce
    */
    @GetMapping("/getResources/{id}/weightedTree")
    public ResponseEntity<Map> getResourceWeightedMetricsTree( @PathVariable(name= "id") int id,
            HttpServletResponse response) {
        
        DatabaseManager database = new DatabaseManager();
        
        int configurationProfileID;
        int qualityModelId;
        QualityModel qualityModel;
        ConfigurationProfile configurationProfile;
        
        try {
            
            //get conf profile Id
            configurationProfileID = database.getConfigurationProfileIdByResourceId(id);
            
            //get quality Model Id
            qualityModelId = database.getQualityModelIdByConfigurationProfileId(configurationProfileID);
            
            //get quality model metrics tree
            qualityModel = database.getQualityModelById(qualityModelId);
            
            //get configuration profile weights
            configurationProfile = database.getConfigurationProfileById(configurationProfileID);
        
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, DataObject> metricsTreeAndWeightsJson = new HashMap<>();
        metricsTreeAndWeightsJson.put("qualityModel", (DataObject) qualityModel);
        metricsTreeAndWeightsJson.put("configurationProfile", (DataObject) configurationProfile);

        return new ResponseEntity<>(
                metricsTreeAndWeightsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    /**
    * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
    * 
    * Get resource's data (either metric data or raw data). Also, may add information about adaptation plans
     * @param dataType it takes values "raw" or "metric". The value is sent from the webpage and it determines
     * if the data to send in the response is data collected from probes or calculated by Analyze 
     * @param startDate start timestamp for retrieving data. It is sent from webPage in UTC
     * @param endDate end timestamp for retrieving data. It is sent from webPage in UTC
     * @param addPlansInfo either true or false. If set to true, information about executed plans in the 
     * time window is sent in the response.
    */
    @GetMapping("/getResources/{id}/data")
    public ResponseEntity<Map> getResourceData ( @PathVariable(name= "id") int resourceId,
            @RequestParam(required = true, name = "metricId") int metricId,
            @RequestParam(required = true, name = "dataType") String dataType,
            @RequestParam(required = true, name = "startDate") long startDate,
            @RequestParam(required = true, name = "endDate") long endDate,
            @RequestParam(required = true, name = "addPlansInfo") boolean addPlansInfo,
            HttpServletResponse response) {
        
        DatabaseManager database = new DatabaseManager();
        ArrayList<DataSetPlot> plotData;
        
        
        try {
            
            plotData = database.getPlotData(resourceId, metricId, dataType, 
                    startDate, endDate, addPlansInfo);
        
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        } 
        HashMap<String, ArrayList<DataSetPlot>> plotDataJson = new HashMap<>();
        plotDataJson.put("plotData", plotData);

        return new ResponseEntity<>(
                plotDataJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    
    //NOTE: This has been made just for metrics that use neutrality as aggregation operator
    @PatchMapping("/simulateData")
    public ResponseEntity<Map> simulateData ( @RequestBody SimulationData simulationRequestData,
            HttpServletResponse response) {
        
        ArrayList<DataSetElem> simulationData = new ArrayList();
        
        try {
            HashMap <Integer,Double> preferences = simulationRequestData.getPreferences();
            MetricDashboard metricToSimulate = simulationRequestData.getMetricToSimulate();
            metricsTreeTraverse(
                    simulationData, simulationRequestData.getResourceId(), simulationRequestData.getStartDate(), 
                    simulationRequestData.getEndDate(), metricToSimulate, preferences,
                    preferences.get(metricToSimulate.getMetricId())
            );
        
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database when trying to perform a simulation.");
        }
        HashMap<String, ArrayList<DataSetElem>> simulationDataJson = new HashMap<>();
        simulationDataJson.put("simulationData", simulationData);

        return new ResponseEntity<>(
                simulationDataJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    //used by "/simulateData" endpoint to retrieve the array of data where weight simulation was applied
    private void metricsTreeTraverse(ArrayList<DataSetElem> simulationData, int resourceId, long startDate, long endDate, 
            MetricDashboard metric, HashMap <Integer,Double> preferences, double traversalWeight) throws SQLException{
        //then, it is a leaf attribute
        if(metric.getChildMetrics().isEmpty()){
            DatabaseManager database = new DatabaseManager();
            database.getSimulationData(simulationData, resourceId, startDate, endDate, 
                    metric.getMetricId(), traversalWeight);
            
        }
        else{
            for( MetricDashboard child: metric.getChildMetrics()){
                double newTraversalWeight = traversalWeight * preferences.get(child.getMetricId());
                metricsTreeTraverse(simulationData, resourceId,startDate, endDate, child, preferences, 
                        newTraversalWeight);
            }
        }
    }
}
