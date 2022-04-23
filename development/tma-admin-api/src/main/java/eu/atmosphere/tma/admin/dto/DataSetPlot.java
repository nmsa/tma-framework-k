package eu.atmosphere.tma.admin.dto;

import java.util.ArrayList;

/**
 * This class holds information about a data set to be presented in plots on the dashboard. The information 
 * may contain description info in the case of being related to raw data.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class DataSetPlot extends DataObject{
    private String descriptionInfo;
    private ArrayList<DataSetElem> listOfDataPoints;
    private ArrayList<PlanInfo> listOfPlansInfo;
    
    public DataSetPlot() {
    }

    public DataSetPlot(ArrayList<DataSetElem> listOfDataPoints, String descriptionInfo) {
        this.listOfDataPoints = listOfDataPoints;
        this.descriptionInfo = descriptionInfo;
    }
    
    public boolean invalidInputs() {
        return false;
    }


    public String getDescriptionInfo() {
        return descriptionInfo;
    }

    public void setDescriptionInfo(String descriptionInfo) {
        this.descriptionInfo = descriptionInfo;
    }

    public ArrayList<DataSetElem> getListOfDataPoints() {
        return listOfDataPoints;
    }

    public void setListOfDataPoints(ArrayList<DataSetElem> listOfDataPoints) {
        this.listOfDataPoints = listOfDataPoints;
    }

    public ArrayList<PlanInfo> getListOfPlansInfo() {
        return listOfPlansInfo;
    }

    public void setListOfPlansInfo(ArrayList<PlanInfo> listOfPlansInfo) {
        this.listOfPlansInfo = listOfPlansInfo;
    }
    
}
