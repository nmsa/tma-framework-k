package eu.atmosphere.tma.admin.controller;

import eu.atmosphere.tma.admin.dto.ConfigurationProfile;
import eu.atmosphere.tma.admin.dto.MetricDashboard;
import eu.atmosphere.tma.admin.dto.QualityModel;
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
public class QualityModelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricController.class);

    @GetMapping("/getQualityModels")
    public ResponseEntity<Map> getQualityModels(
            @RequestParam(required = false, defaultValue="",name = "qualityModelsFilter") String qualityModelsFilter,
            @RequestParam(required = false, defaultValue="",name = "metricsFilter") String metricsFilter,
            HttpServletResponse response) {
        
        DatabaseManager database = new DatabaseManager();
        ArrayList<QualityModel> qualityModels;
        try {
            qualityModels = database.getQualityModels(qualityModelsFilter, metricsFilter);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<QualityModel>> qualityModelsJson = new HashMap<>();
        qualityModelsJson.put("qualityModels", qualityModels);

        return new ResponseEntity<>(
                qualityModelsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    @GetMapping("/getQualityModels/{id}")
    public ResponseEntity<Map> getQualityModelById(@PathVariable(name= "id") int id, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        QualityModel qualityModel;
        try {
            qualityModel = database.getQualityModelById(id);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, QualityModel> qualityModelJson = new HashMap<>();
        qualityModelJson.put("qualityModel", qualityModel);

        return new ResponseEntity<>(
                qualityModelJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    @GetMapping("/getConfigurationProfile/{id}")
    public ResponseEntity<Map> getConfigurationProfileById(@PathVariable(name= "id") int id, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ConfigurationProfile configurationProfile;
        try {
            configurationProfile = database.getConfigurationProfileById(id);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ConfigurationProfile> configurationProfileJson = new HashMap<>();
        configurationProfileJson.put("configurationProfile", configurationProfile);

        return new ResponseEntity<>(
                configurationProfileJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }
    
    @GetMapping("/getConfigurationProfile/{id}/listOfMetrics")
    public ResponseEntity<Map> getConfigurationProfileListOfMetrics(
            @RequestParam(required = true, name = "leafAttributes") boolean leafAttributes,
            @PathVariable(name= "id") int id, 
            HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        ArrayList<MetricDashboard> listOfMetrics;
        try {
            listOfMetrics = database.getConfigurationProfileListOfMetrics(id, leafAttributes);
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Unable to connect to the database", ex);
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR,
                    Constants.ERROR, "There was a problem with the connection to the database");
        }

        HashMap<String, ArrayList<MetricDashboard>> listOfMetricsJson = new HashMap<>();
        listOfMetricsJson.put("listOfMetrics", listOfMetrics);

        return new ResponseEntity<>(
                listOfMetricsJson,
                HttpStatus.valueOf(response.getStatus())
        );
    }

    
    @PostMapping("/createQualityModel")
    public ResponseEntity<Map> createQualityModel(@RequestBody QualityModel qualityModel, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        switch (database.isQualityModelNameRepeated(qualityModel.getModelName())) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Quality Model name given already exists");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Quality Model name already exists, please choose another name");
        }

        database.saveNewQualityModel(qualityModel);
        if (qualityModel.getQualityModelId()== -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database. Try again later.");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESS, Constants.SUCCESS, "Quality Model created");
    }

    @PostMapping("/createConfigurationProfile")
    public ResponseEntity<Map> createConfigurationProfile(@RequestBody ConfigurationProfile configurationProfile, HttpServletResponse response) {
        DatabaseManager database = new DatabaseManager();
        switch (database.isConfigurationProfileNameRepeatedForQualityModel(
                configurationProfile.getProfileName(),configurationProfile.getQualityModelId())
                ) {
            case -1:
                return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database");
            case 0:
                break;
            case 1:
                LOGGER.warn("[ATMOSPHERE] Configuration Profile name given already exists for the given Quality Model");
                return AdminController.genericResponseEntity(Constants.HTTPBADREQUEST, Constants.WARNING, "Configuration Profile name already exists for given Quality Model, please choose another name");
        }

        database.saveNewConfigurationProfile(configurationProfile);
        if (configurationProfile.getConfigurationProfileId()== -1) {
            return AdminController.genericResponseEntity(Constants.HTTPSERVERERROR, Constants.ERROR, "There was a problem with the connection to the database. Try again later.");
        }

        return AdminController.genericResponseEntity(Constants.HTTPSUCESS, Constants.SUCCESS, "Configuration Profile created");
    }


}
