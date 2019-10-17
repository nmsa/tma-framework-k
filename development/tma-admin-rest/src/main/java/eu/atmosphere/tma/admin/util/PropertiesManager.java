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

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

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
@Configuration
@PropertySource("classpath:config.properties")
public class PropertiesManager {
    
    @Value("#{${partnerList}}")
    private Map<String, Integer> partnerList;
    @Value("${algorithm}")
    private String algorithm;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public Map<String, Integer> getPartnerList() {
        return partnerList;
    }

}
