package eu.atmosphere.tmaf.admin.database;

public class Configuration {

    private String keyName;
    private String domain;

    public Configuration(String keyName, String domain) {
        this.keyName = keyName;
        this.domain = domain;
    }
    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
