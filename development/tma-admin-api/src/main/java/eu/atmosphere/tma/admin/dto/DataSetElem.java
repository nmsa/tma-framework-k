package eu.atmosphere.tma.admin.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * This class holds information about a data point to be presented in plots on
 * the dashboard. The information contains values from raw data or metric data, and optionally plans info.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class DataSetElem extends DataObject{
    private double value;
    private long valueTime;
    
    public DataSetElem() {
    }

    public DataSetElem(double value, long valueTime) {
        this.value = value;
        this.valueTime = valueTime;
    }
    
    public boolean invalidInputs() {
        return false;
    }
    
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public long getValueTime() {
        return valueTime;
    }

    public void setValueTime(long valueTime) {
        this.valueTime = valueTime;
    }
}
