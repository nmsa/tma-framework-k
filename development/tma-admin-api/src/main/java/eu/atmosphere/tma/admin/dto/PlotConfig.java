/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.atmosphere.tma.admin.dto;

/**
 * This class holds information about dashboard plot configurations sent to the server to be saved in the database.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class PlotConfig extends DataObject{
    private String plotConfigName;
    private byte[] configObject;
    private int plotConfigId;
    
    public PlotConfig() {
    }

    public PlotConfig(String plotConfigName, byte[] configObject, int plotConfigId) {
        this.plotConfigName = plotConfigName;
        this.configObject = configObject;
        this.plotConfigId = plotConfigId;
    }
    
    
    public String getPlotConfigName() {
        return plotConfigName;
    }

    public void setPlotConfigName(String plotConfigName) {
        this.plotConfigName = plotConfigName;
    }

    public byte[] getConfigObject() {
        return configObject;
    }

    public void setConfigObject(byte[] configObject) {
        this.configObject = configObject;
    }

    public int getPlotConfigId() {
        return plotConfigId;
    }

    public void setPlotConfigId(int plotConfigId) {
        this.plotConfigId = plotConfigId;
    }
    
    
}
