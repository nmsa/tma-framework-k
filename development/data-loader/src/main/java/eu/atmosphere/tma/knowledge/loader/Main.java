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

import eu.atmosphere.tma.util.PropertiesManager;
import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 */
public class Main {

    private static String TOPIC = "topic-monitor";

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final int KAFKA_CONSUMER_POLL_DURATION = 1000;

    public static void main(String[] args) {

        Consumer<Long, String> consumer = createConsumer();

        final Duration pollDuration = Duration.ofMillis(KAFKA_CONSUMER_POLL_DURATION);

        try {
            while (true) {

                // pollDuration is the time in milliseconds consumer will wait if no record is found at broker.
                ConsumerRecords<Long, String> consumerRecords = consumer.poll(pollDuration);
                
                LOGGER.info("ConsumerRecords: {}", consumerRecords.count());

                // Manipulate the records
                consumerRecords.forEach(record -> {
                    ArrayList <Evidence> evidences = parseJsonEvidences(record.value());
                    
                    if (evidences == null){
												return;                    
										}
					if (evidences.size() > 0) {
                        for (Evidence ev : evidences){
                            insertEvidenceToDatabase(ev);
                        }
                        
                    }
                });

                // commits the offset of record to broker.
                consumer.commitAsync();
            }
        } finally {
						LOGGER.trace("Closing consumer");
            consumer.close();
						LOGGER.trace("Consumer closed");
        }

    }

    private static Consumer<Long, String> createConsumer() {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                PropertiesManager.getInstance().getProperty("bootstrapServers"));
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                PropertiesManager.getInstance().getProperty("groupIdConfig"));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
                Integer.parseInt(PropertiesManager.getInstance().getProperty("maxPollRecords")));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                PropertiesManager.getInstance().getProperty("offsetResetEarlier"));

        Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));

        return consumer;
    }

    private static void insertEvidenceToDatabase (Evidence evidence){
        // create a mysql database connection
        String myDriver = "com.mysql.cj.jdbc.Driver";
				try{
        		Class.forName(myDriver);
				}
				catch (ClassNotFoundException ce) {
						LOGGER.error("Wrong Class name");
						LOGGER.error("Message: " + ce.getMessage());
						
						Throwable t = ce.getCause();
						if(t != null){
								LOGGER.error("Causes:");
		            while(t != null) {
		                LOGGER.error(t.getMessage());
		                t = t.getCause();
		            }
						}
				}

        String myUrl = "jdbc:mysql://mysql-0.mysql.default.svc.cluster.local:3306/knowledge";

        try {
            Connection conn = DriverManager.getConnection(myUrl, "root", "passtobereplaced");

            // the mysql insert statement
            String query = "INSERT INTO Data(probeId, resourceId, descriptionId, valueTime, value) "
                    + " VALUES (? , ? , ?, FROM_UNIXTIME(?), ?)";

            conn.setAutoCommit(false);

            // create the mysql insert preparedstatement
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, evidence.getProbeId());
            preparedStatement.setInt(2, evidence.getResourceId());
            preparedStatement.setInt(3, evidence.getDescriptionId());
            preparedStatement.setDouble(4, evidence.getTime());
            preparedStatement.setDouble(5, evidence.getValue());
            
            // execute the prepared statement
            preparedStatement.executeUpdate();

            
            conn.commit();
            conn.close();
        }
        catch (SQLException sqle) {
						LOGGER.error("SQL Error");
						LOGGER.error("Error Code: " + sqle.getErrorCode());
						LOGGER.error("Message: " + sqle.getMessage());
						
						Throwable t = sqle.getCause();
						if(t != null){
								LOGGER.error("Causes:");
                while(t != null) {
                    LOGGER.error(t.getMessage());
                    t = t.getCause();
                }
						}
        }
    }

    private static ArrayList <Evidence> parseJsonEvidences(String jsonToParse) {
        JSONObject input;
        ArrayList <Evidence> evidences = new ArrayList <Evidence>();

        LOGGER.trace("New message");
        try {
            input = new JSONObject(jsonToParse);
        } catch (JSONException je) {
            LOGGER.error("Invalid Json input");
						LOGGER.error("Message: " + je.getMessage());
						
						Throwable t = je.getCause();
						if(t != null){
								LOGGER.error("Causes:");
                while(t != null) {
                    LOGGER.error(t.getMessage());
                    t = t.getCause();
                }
						}
            return null;
        }

        try {
            // unpacking the json
            JSONArray data = input.getJSONArray("data");
            int probeId = input.getInt("probeId");
            int resourceId = input.getInt("resourceId");
						
						LOGGER.trace("Reading every data");
            for (int i = 0; i < data.length(); i++) {
                // unpacking the data
                int descriptionId = data.getJSONObject(i).getInt("descriptionId");
                JSONArray observations = data.getJSONObject(i).getJSONArray("observations");
								
								LOGGER.trace("Reading every observation");
                for (int j = 0; j < observations.length(); j++) {
                    // unpacking the observation
                    int time = observations.getJSONObject(j).getInt("time");
                    Double value = observations.getJSONObject(j).getDouble("value");
										
                    Evidence newEvidence = new Evidence(probeId, resourceId, descriptionId, time, value);
                    evidences.add(newEvidence);
                }
            }
						
        } catch (JSONException je) {
						LOGGER.error("Wrong Json format");
						LOGGER.error("Message: " + je.getMessage());
						
						Throwable t = je.getCause();
						if(t != null){
								LOGGER.error("Causes:");
                while(t != null) {
                    LOGGER.error(t.getMessage());
                    t = t.getCause();
                }
						}
						return null;
        }
        return evidences;
    }
}
