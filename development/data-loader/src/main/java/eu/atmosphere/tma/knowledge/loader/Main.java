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
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 */
public class Main {

    // TODO: Rui arranja o nome
    private static String TOPIC = "test";

    // TODO: Paulo usar logger em todo lado em vez de  System.out.println("");
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final int KAFKA_CONSUMER_POLL_DURATION = 1000;

    public static void main(String[] args) {

        Consumer<Long, String> consumer = createConsumer();

        //int noMessageFound = 0;
        //int maxNoMessageFoundCount = Integer.parseInt(PropertiesManager.getInstance().getProperty("maxNoMessageFoundCount"));
        final Duration pollDuration = Duration.ofMillis(KAFKA_CONSUMER_POLL_DURATION);

        try {
            while (true) {

                ConsumerRecords<Long, String> consumerRecords = consumer.poll(pollDuration);

                // 1000 is the time in milliseconds consumer will wait if no record is found at broker.
                //if (consumerRecords.count() == 0) {
                //    noMessageFound++;
                //}
                LOGGER.trace("ConsumerRecords: {}", consumerRecords.count());

                // Manipulate the records
                consumerRecords.forEach(record -> {
                    ArrayList<Evidence> observations = parseJsonEvidences(record.value());
                    //observations.get(0).printObservation();
                    if (observations.size() > 0) {
                        firstDatabaseChange(observations.get(0));
                    }
                });

                // commits the offset of record to broker.
                consumer.commitAsync();
//                sleep(5000);
            }
        } finally {
            consumer.close();
            System.out.println("End");
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

    // TODO: name corrigir
    private static void firstDatabaseChange(Evidence observation) {
        try {
            // create a mysql database connection
            String myDriver = "knowledge";
            Class.forName(myDriver);

            String myUrl = "jdbc:mysql://localhost/test"; // @TODO Rui corrige

            Connection conn = DriverManager.getConnection(myUrl, "root", "passtobereplaced");

            // the mysql insert statement
            String query = "INSERT INTO Data(probeId, resourceId, descriptionId, valueTime, value) "
                    + " VALUES (? , ? , ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedState = conn.prepareStatement(query);

            preparedState.setInt(1, observation.getProbeId());
            preparedState.setInt(2, observation.getResourceId());
            preparedState.setInt(3, observation.getDescriptionId());
            preparedState.setDouble(4, observation.getTime());
            preparedState.setDouble(5, observation.getValue());

            // execute the preparedstatement
            preparedState.execute();

            conn.commit();
            conn.close();
        } //error handler
        catch (Exception e) {

            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    private static ArrayList<Evidence> parseJsonEvidences(String jsonToParse) {
        JSONObject input;
        ArrayList<Evidence> evidences = new ArrayList<Evidence>();

        try {
            input = new JSONObject(jsonToParse);

        } catch (JSONException je) {
            // TODO: Paulo Logger e corrigir texto se for necessario.
            System.out.println("Invalid  Json");
            // TODO: Paulo usar a je
            return null;
        }

        // TODO: Paulo Logger e corrigir texto se for necessario.
        System.out.println("NEW MESSAGE:");
        try {

            /* unpacking the json */
            JSONArray data = input.getJSONArray("data");
            int probeId = input.getInt("probeId");
            int resourceId = input.getInt("resourceId");
//            int sentTime = input.getInt("sentTime");
//            int messageId = input.getInt("messageId");

            for (int i = 0; i < data.length(); i++) {
                /* unpacking the data */
                int descriptionId = data.getJSONObject(i).getInt("descriptionId");
                //String type = data.getJSONObject(i).getString("type");

                JSONArray observations = data.getJSONObject(i).getJSONArray("observations");
                for (int j = 0; j < observations.length(); j++) {
                    /* unpacking the observation */
                    int time = observations.getJSONObject(j).getInt("time");
                    Double value = observations.getJSONObject(j).getDouble("value");

                    Evidence newObservation = new Evidence(probeId, resourceId, descriptionId, time, value);

                    evidences.add(newObservation);
                    //System.out.println(newObservation);
                }
            }
        } catch (JSONException je) {
            // TODO: Paulo Logger e corrigir texto se for necessario.
            // TODO: Paulo usar a je

            System.out.println("***********************");
            System.out.println("Error reading the Json");
            System.out.println("***********************");
        }
        return evidences;
    }
}
