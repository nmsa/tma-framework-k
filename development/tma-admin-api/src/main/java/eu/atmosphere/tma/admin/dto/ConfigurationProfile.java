package eu.atmosphere.tma.admin.dto;

import java.util.ArrayList;

/**
 * This class stores the information from an entry on the Configuration Profile database table.
 * <p>
 * @author João Ribeiro <jdribeiro@student.dei.uc.pt>
 */
public class ConfigurationProfile extends DataObject{
    private int configurationProfileId;
    private String profileName;
    private int qualityModelId;
    ArrayList<Preference> preferences;

    public ConfigurationProfile() {
    }
    
    public ConfigurationProfile(int configurationProfileId, String profileName) {
        this.configurationProfileId = configurationProfileId;
        this.profileName = profileName;
    }
    
    public ConfigurationProfile(int configurationProfileId, String profileName, int qualityModelId) {
        this(configurationProfileId, profileName);
        this.qualityModelId = qualityModelId;
    }
    
    public ConfigurationProfile(int configurationProfileId, String profileName, int qualityModelId, 
            ArrayList<Preference> preferences) {
        this(configurationProfileId, profileName, qualityModelId);
        this.preferences = preferences;
       
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getQualityModelId() {
        return qualityModelId;
    }

    public void setQualityModelId(int qualityModelId) {
        this.qualityModelId = qualityModelId;
    }

    public ArrayList<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(ArrayList<Preference> preferences) {
        this.preferences = preferences;
    }
    
    
}
