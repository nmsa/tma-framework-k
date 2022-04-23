package eu.atmosphere.tma.admin.dto;

/**
 * This class stores the information from an entry on the LeafAttribute database table.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */

public class LeafAttribute extends DataObject{
    private int metricId;
    private Description description;
    private int metricAggregationOperator;
    private int numSamples;
    private String normalizationMethod;
    private int normalizationKind;
    private float minimumThreshold;
    private float maximumThreshold;

    public LeafAttribute() {
    }
    
    public LeafAttribute(int metricId, Description description, int metricAggregationOperator, int numSamples,
            String normalizationMethod, int normalizationKind, float minimumThreshold, float maximumThreshold) {
    this.metricId =  metricId;
    this.description =  description;
    this.metricAggregationOperator =   metricAggregationOperator;
    this.numSamples =   numSamples;
    this.normalizationMethod =   normalizationMethod;
    this.normalizationKind =   normalizationKind;
    this.minimumThreshold =   minimumThreshold;
    this.maximumThreshold =   maximumThreshold;
    }
    
    public boolean invalidInputs() {
        return false;
    }

    public int getMetricId() {
        return metricId;
    }

    public void setMetricId(int metricId) {
        this.metricId = metricId;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public int getMetricAggregationOperator() {
        return metricAggregationOperator;
    }

    public void setMetricAggregationOperator(int metricAggregationOperator) {
        this.metricAggregationOperator = metricAggregationOperator;
    }

    public int getNumSamples() {
        return numSamples;
    }

    public void setNumSamples(int numSamples) {
        this.numSamples = numSamples;
    }

    public String getNormalizationMethod() {
        return normalizationMethod;
    }

    public void setNormalizationMethod(String normalizationMethod) {
        this.normalizationMethod = normalizationMethod;
    }

    public int getNormalizationKind() {
        return normalizationKind;
    }

    public void setNormalizationKind(int normalizationKind) {
        this.normalizationKind = normalizationKind;
    }

    public float getMinimumThreshold() {
        return minimumThreshold;
    }

    public void setMinimumThreshold(float minimumThreshold) {
        this.minimumThreshold = minimumThreshold;
    }

    public float getMaximumThreshold() {
        return maximumThreshold;
    }

    public void setMaximumThreshold(float maximumThreshold) {
        this.maximumThreshold = maximumThreshold;
    }
    
    
}
