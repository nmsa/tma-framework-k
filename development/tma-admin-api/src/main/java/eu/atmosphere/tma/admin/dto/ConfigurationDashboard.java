package eu.atmosphere.tma.admin.dto;

/**
 * This class stores the information of a Configuration entry in the Configuration table [Based on 'Configuration' class].
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class ConfigurationDashboard {
    private int configurationId;
    private String keyName;


    public ConfigurationDashboard(int configurationId, String keyName) {
        this.configurationId = configurationId;
        this.keyName = keyName;
    }

    public int getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(int configurationId) {
        this.configurationId = configurationId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }
    
    
}
