/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 ***
 * <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class stores the information of the Properties declared in the
 * config.properties file inside the resources folder.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *
 */

public class PropertiesManager {

    private static PropertiesManager instance = null;
    private static Properties props = null;

    private PropertiesManager() {
        InputStream inputStream = PropertiesManager.class.getResourceAsStream("/application.properties");
        props = new Properties();
        try {
            props.load(inputStream);
        } catch (IOException e) {
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