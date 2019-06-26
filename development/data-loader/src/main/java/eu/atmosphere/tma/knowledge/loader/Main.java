/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu/ *** <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Knowledge - DataLoader
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.knowledge.loader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eubr.atmosphere.tma.planning.utils.PropertiesManager;
import eubr.atmosphere.tma.planning.consumer.ConsumerCreator;
import eubr.atmosphere.tma.planning.databaseControler.DatabaseControler;
import eubr.atmosphere.tma.planning.stringManager.StringManager;
import java.util.ArrayList;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 *
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 */
public class Main {

		private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

		public static void main(String[] args) {

				//LOGGER.debug("isto e de debug");
				runConsumer();
		}

		private static void runConsumer() {
				StringManager stringManager = new StringManager();
				DatabaseControler databaseControler = new DatabaseControler();
				Consumer<Long, String> consumer = ConsumerCreator.createConsumer();
				int noMessageFound = 0;
				int maxNoMessageFoundCount
								= Integer.parseInt(PropertiesManager.getInstance().getProperty("maxNoMessageFoundCount"));

				try {
						while (true) {
								ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);

								// 1000 is the time in milliseconds consumer will wait if no record is found at broker.
								if (consumerRecords.count() == 0) {
										noMessageFound++;

										if (noMessageFound > maxNoMessageFoundCount) {
												// If no message found count is reached to threshold exit loop.
												sleep(2000);
												System.out.println("No error found");
										} else {
												continue;
										}
								}

								LOGGER.info("ConsumerRecords: {}", consumerRecords.count());

								// Manipulate the records
								consumerRecords.forEach(record -> {
										ArrayList<String> observations;
										observations = stringManager.parseJsonobservations(record.value());
										//stringManager.printObservations(observations);
										if(observations.size() > 0){
												databaseControler.firstDatabaseChange(stringManager.separateObservation(observations.get(0)));
										}
								});

								// commits the offset of record to broker.
								consumer.commitAsync();
								sleep(5000);
						}
				} finally {
						consumer.close();
						System.out.println("End");
				}
		}

		private static void sleep(int millis) {
				try {
						Thread.sleep(millis);
				} catch (InterruptedException e) {
						LOGGER.warn(e.getMessage(), e);
				}
		}

}
