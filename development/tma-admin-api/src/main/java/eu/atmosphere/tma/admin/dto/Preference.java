package eu.atmosphere.tma.admin.dto;

/**
 * This class stores the information from an entry on the Preference database table.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class Preference extends DataObject{
    private int configurationProfileId;
    private int metricId;
    private float weight;
    private float threshold;

    public Preference() {
    }
    
    public Preference(int metricId, float weight, float threshold) {
        this.metricId = metricId;
        this.weight = weight;
        this.threshold = threshold;
    }
    
    public Preference(int configurationProfileId, int metricId, float weight, float threshold) {
        this(metricId, weight, threshold);
        this.configurationProfileId = configurationProfileId;
    }
    
    public boolean invalidInputs() {
        return false;
    }

    public int getConfigurationProfileId() {
        return configurationProfileId;
    }

    public void setConfigurationProfileId(int configurationProfileId) {
        this.configurationProfileId = configurationProfileId;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }
    
    
}
