package eu.atmosphere.tma.admin.dto;

/**
 * This class holds information about performed adaptation plans which will be presented alongside
 * metrics values on plots at the dashboard.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class PlanInfo extends DataObject{
    private int planId;
    private long valueTime;
    
    public PlanInfo() {
    }
    
    public PlanInfo(int planId, long valueTime) {
        this.planId = planId;
        this.valueTime = valueTime;
    }

    public boolean invalidInputs() {
        return false;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public long getValueTime() {
        return valueTime;
    }

    public void setValueTime(long valueTime) {
        this.valueTime = valueTime;
    }
    
}
