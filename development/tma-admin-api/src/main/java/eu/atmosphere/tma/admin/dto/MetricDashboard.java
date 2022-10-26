package eu.atmosphere.tma.admin.dto;

import java.util.ArrayList;

/**
 * This class stores the information from an entry on the Metric database table.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class MetricDashboard extends DataObject{
    private int metricId;
    private String metricName;
    private int blockLevel;
    private int attributeAggregationOperator;
    private ArrayList<MetricDashboard> childMetrics;
    private LeafAttribute leafAttribute;

    public MetricDashboard() {
    }
    
    public MetricDashboard(int metricId) {
    this.metricId = metricId;
    }

    public MetricDashboard(int metricId, String metricName) {
        this.metricId = metricId;
        this.metricName = metricName;
    }
    
    public MetricDashboard(int metricId, String metricName, int blockLevel) {
        this(metricId, metricName); //invoke other constructor
        this.blockLevel = blockLevel;
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

    public String getMetricName() {
        return metricName;
    }

    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
    
    public int getBlockLevel() {
        return blockLevel;
    }

    public void setBlockLevel(int blockLevel) {
        this.blockLevel = blockLevel;
    }

    public int getAttributeAggregationOperator() {
        return attributeAggregationOperator;
    }

    public void setAttributeAggregationOperator(int attributeAggregationOperator) {
        this.attributeAggregationOperator = attributeAggregationOperator;
    }
    
    public ArrayList<MetricDashboard> getChildMetrics() {
        return childMetrics;
    }

    public void setChildMetrics(ArrayList<MetricDashboard> childMetrics) {
        this.childMetrics = childMetrics;
    }

    public LeafAttribute getLeafAttribute() {
        return leafAttribute;
    }

    public void setLeafAttribute(LeafAttribute leafAttribute) {
        this.leafAttribute = leafAttribute;
    }
    
    
}
