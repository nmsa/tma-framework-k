/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tma.admin.dto;

/**
 * This class stores the information from an entry on the Metric database table.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class MetricDashboard extends DataObject{
    private int metricId;
    private String metricName;
    private int blockLevel;

    public MetricDashboard() {
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
    
}
