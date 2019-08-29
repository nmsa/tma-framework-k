/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/ *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: (TBD)
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class manages the general properties of the project
 * 
 * <p>
 *
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 * @author Jose Pereira     <josep@dei.uc.pt>
 * @author Rui Silva        <rfsilva@student.dei.uc.pt>
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 */
public class PropertiesManager {

    private static PropertiesManager instance;
    private static final Properties props = new Properties();

    private PropertiesManager() {
        InputStream inputStream = PropertiesManager.class.getResourceAsStream("/environment.properties");
        try {
            props.load(inputStream);
        } catch (IOException e) {
            // FIXME: log...
            e.printStackTrace();
        }
    }

    public static PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
