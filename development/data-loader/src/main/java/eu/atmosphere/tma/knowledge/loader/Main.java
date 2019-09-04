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
 * This class is used to consume the items of the topic monitor
 * and inserts them in the knowledge database.
 * <p>
 * <p>
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Rui Silva        <rfsilva@student.dei.uc.pt>
 * @author Jose Pereira     <josep@dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 */
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static String TOPIC = "topic-monitor";
    private static String DB_URL = "jdbc:mysql://mysql-0.mysql.default.svc.cluster.local:3306/knowledge";

    private static final int KAFKA_CONSUMER_POLL_DURATION = 1000;

    public static void main(String[] args) {

        LOGGER.debug("Creating consumer");
        Consumer<Long, String> consumer = createConsumer();
        LOGGER.trace("Consumer created");

        final Duration pollDuration = Duration.ofMillis(KAFKA_CONSUMER_POLL_DURATION);

        try {
            while (true) {

                // pollDuration is the time in milliseconds consumer will wait if no record is found at broker.
                ConsumerRecords<Long, String> consumerRecords = consumer.poll(pollDuration);

                LOGGER.trace("ConsumerRecords: {}", consumerRecords.count());

                // Manipulate the records
                consumerRecords.forEach(record -> {
                    ArrayList<Evidence> evidences = parseJsonEvidences(record.value());
                    if (evidences.size() > 0) {
                        for (Evidence ev : evidences) {
                            insertEvidenceToDatabase(ev);
                        }

                    }
                });

                // commits the offset of record to broker.
                consumer.commitAsync();
            }
        } finally {
            LOGGER.debug("Closing consumer");
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

    private static void insertEvidenceToDatabase(Evidence evidence) {
        // create a mysql database connection
        String myDriver = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(myDriver);
        } catch (ClassNotFoundException ce) {
            LOGGER.error("Wrong Class name", ce);
            return;
        }

        try {
            LOGGER.trace("Opening connection");
            Connection conn = DriverManager.getConnection(DB_URL, "root", "passtobereplaced");
            LOGGER.debug("Connection opened");

            LOGGER.trace("Preparing mysql statement");
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

            LOGGER.trace("Executing mysql statement");
            // execute the prepared statement
            preparedStatement.executeUpdate();
            LOGGER.trace("Data correctly added to database");

            conn.commit();
            LOGGER.trace("Closing Connection");
            conn.close();
            LOGGER.trace("Connection closed");
        } catch (SQLException sqle) {
            LOGGER.error("SQL Error ", sqle);
        }
    }

    private static ArrayList<Evidence> parseJsonEvidences(String jsonToParse) {
        JSONObject input;
        ArrayList<Evidence> evidences = new ArrayList<Evidence>();

        LOGGER.trace("New message");
        try {
            input = new JSONObject(jsonToParse);
        } catch (JSONException je) {
            LOGGER.error("Invalid Json input", je);
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

                LOGGER.trace("Data number {} Reading every observation", i);
                for (int j = 0; j < observations.length(); j++) {
                    // unpacking the observation
                    int time = observations.getJSONObject(j).getInt("time");
                    Double value = observations.getJSONObject(j).getDouble("value");

                    Evidence newEvidence = new Evidence(probeId, resourceId, descriptionId, time, value);
                    evidences.add(newEvidence);
                }
            }

        } catch (JSONException je) {
            LOGGER.error("Wrong Json format", je);
            return null;
        }
        return evidences;
    }
}
