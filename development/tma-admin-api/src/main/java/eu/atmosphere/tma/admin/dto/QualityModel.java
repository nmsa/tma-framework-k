package eu.atmosphere.tma.admin.dto;

import java.util.ArrayList;

/**
 * This class stores the information from an entry on the Quality Model database table and the list of associated
 * configuration profiles. It also holds information of the corresponding metrics tree.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class QualityModel extends DataObject{
    private int qualityModelId;
    private MetricDashboard metric;
    private String modelName;
    private int modelDescriptionReference;
    private float businessThreshold;
    private ArrayList<ConfigurationProfile> configurationProfiles;

    public QualityModel() {
    }
    
    public QualityModel(MetricDashboard metric, String modelName, int modelDescriptionReference, float businessThreshold){
        this.metric = metric;
        this.modelName = modelName;
        this.modelDescriptionReference = modelDescriptionReference;
        this.businessThreshold = businessThreshold;
    }
    
    public QualityModel(int qualityModelId, String modelName, int modelDescriptionReference, float businessThreshold) {
        this.qualityModelId = qualityModelId;
        this.modelName = modelName;
        this.modelDescriptionReference = modelDescriptionReference;
        this.businessThreshold = businessThreshold;
    }
    
    public QualityModel(int qualityModelId, MetricDashboard metric, String modelName, int modelDescriptionReference, 
            float businessThreshold) {
        this(metric, modelName, modelDescriptionReference, businessThreshold);
        this.qualityModelId = qualityModelId;
       
    }
    
    public boolean invalidInputs() {
        return false;
    }

    public int getQualityModelId() {
        return qualityModelId;
    }

    public void setQualityModelId(int qualityModelId) {
        this.qualityModelId = qualityModelId;
    }

    public MetricDashboard getMetric() {
        return metric;
    }

    public void setMetric(MetricDashboard metric) {
        this.metric = metric;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getModelDescriptionReference() {
        return modelDescriptionReference;
    }

    public void setModelDescriptionReference(int modelDescriptionReference) {
        this.modelDescriptionReference = modelDescriptionReference;
    }

    public float getBusinessThreshold() {
        return businessThreshold;
    }

    public void setBusinessThreshold(float businessThreshold) {
        this.businessThreshold = businessThreshold;
    }

    public ArrayList<ConfigurationProfile> getConfigurationProfiles() {
        return configurationProfiles;
    }

    public void setConfigurationProfiles(ArrayList<ConfigurationProfile> configurationProfiles) {
        this.configurationProfiles = configurationProfiles;
    }
    
    
    
}
