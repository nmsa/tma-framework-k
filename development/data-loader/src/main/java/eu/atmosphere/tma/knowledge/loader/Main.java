/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/
 * ***
 * <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Knowledge - DataLoader
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework
 * License: https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.knowledge.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        LOGGER.debug("isto e de debug");

        for (Event event : evts) {
            byte[] eventBody = event.getBody();
            JSONObject input = new JSONObject(new String(eventBody));
            JSONArray data = input.getJSONArray("data");
            int probeId = input.getInt("probeId");
            int resourceId = input.getInt("resourceId");
            for (int i = 0; i < data.length(); i++) {
                JSONArray observations = data.getJSONObject(i).getJSONArray("observations");
                for (int j = 0; j < observations.length(); j++) {
                    int descriptionId = data.getJSONObject(i).getInt("descriptionId");
                    int time = observations.getJSONObject(j).getInt("time");
                    Double value = observations.getJSONObject(j).getDouble("value");

                    ps.setInt(1, probeId);
                    ps.setInt(2, resourceId);
                    ps.setInt(3, descriptionId);
                    ps.setInt(4, time);
                    ps.setDouble(5, value);

                    int result = ps.executeUpdate();
                    if (result <= 0) {
                        LOGGER.error("Error executing the query {}", ps);

//                      LOGGER.debug("Error executing the query {}" , ps);
//                        
//                       "asdasubads" + "ashduashu"
                    }
                }
            }

        }

    }
}
