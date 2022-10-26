
package eu.atmosphere.tma.admin.dto;

import java.util.HashMap;

/**
 * This class represents the information of a simulation needed to represent on server side.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class SimulationData {
    private int resourceId;
    private long startDate;
    private long endDate;
    private MetricDashboard metricToSimulate;
    private HashMap <Integer,Double> preferences;
    
    public SimulationData(){
        
    }

    public SimulationData(int resourceId, long startDate, long endDate, MetricDashboard metricToSimulate, HashMap<Integer, Double> preferences) {
        this.resourceId = resourceId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.metricToSimulate = metricToSimulate;
        this.preferences = preferences;
    }
    
    
    
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public MetricDashboard getMetricToSimulate() {
        return metricToSimulate;
    }

    public void setMetricToSimulate(MetricDashboard metricToSimulate) {
        this.metricToSimulate = metricToSimulate;
    }

    public HashMap<Integer, Double> getPreferences() {
        return preferences;
    }

    public void setPreferences(HashMap<Integer, Double> preferences) {
        this.preferences = preferences;
    }
    
    
}
