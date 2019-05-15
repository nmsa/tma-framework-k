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

import java.sql.Connection;
import java.sql.PreparedStatement;
import org.json.JSONArray;
import org.json.JSONObject;
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

        // para cada mensagem recebida do kafka
        // vais ter de obter o json do body da mensagem
        // e usar o condigo abaixo para processar
        
        
//agent.sinks.jdbcSink.connectionString = jdbc:mysql://mysql-0.mysql.default.svc.cluster.local:3306/knowledge
//
//agent.sinks.jdbcSink.username=root
//
//agent.sinks.jdbcSink.password=passtobereplaced
//
//agent.sinks.jdbcSink.batchSize = 10
//
//agent.sinks.jdbcSink.channel =channel1
//
//agent.sinks.jdbcSink.sqlDialect=MYSQL
//
//agent.sinks.jdbcSink.driver=com.mysql.jdbc.Driver

        
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection c = ;
        
        

        PreparedStatement ps = c.prepareStatement("INSERT INTO Data(probeId, descriptionId, resourceId, valueTime, value) "
                + " VALUES (? , ? , ?, ?, ?)");
        
        
        
        String jsonContent = "Isto vais precisar da obter do kafka";
        
        JSONObject input = new JSONObject(new String(jsonContent));
        JSONArray data = input.getJSONArray("data");
        int probeId = input.getInt("probeId");
        int resourceId = input.getInt("resourceId");
        for (int i = 0; i < data.length(); i++) {
            JSONArray observations = data.getJSONObject(i).getJSONArray("observations");
            for (int j = 0; j < observations.length(); j++) {
                int descriptionId = data.getJSONObject(i).getInt("descriptionId");
                int time = observations.getJSONObject(j).getInt("time");
//                Double value = observations.getJSONObject(j).getDouble("value");

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
