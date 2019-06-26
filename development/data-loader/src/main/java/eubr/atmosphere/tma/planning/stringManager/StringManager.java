package eubr.atmosphere.tma.planning.stringManager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StringManager {

    public StringManager(){}
		
		public String [] separateObservation(String observation){
				String [] details = new String [5];
				
				details = observation.split(",");
				return details;
		}
		
		public void printObservations(ArrayList <String> observations){
				for(int i = 0; i < observations.size(); i++){
						System.out.println(observations.get(i));
				}
		}

    public ArrayList <String> parseJsonobservations(String jsonToParse){
				JSONObject input;
				ArrayList <String> newObservations = new ArrayList <String> ();

				try {
						input = new JSONObject(jsonToParse);

						System.out.println("NEW MESSAGE:");

						/* unpacking the json */
						JSONArray data = input.getJSONArray("data");
						int probeId = input.getInt("probeId");
						int resourceId = input.getInt("resourceId");
						int sentTime = input.getInt("sentTime");
						int messageId = input.getInt("messageId");

						/* Beggining of the string describing the new message
						 * will always repeated on every new message for the same json */
						String beggining = probeId + ",";

						for (int i = 0; i < data.length(); i++) {
								/* unpacking the data */
								int descriptionId = data.getJSONObject(i).getInt("descriptionId");
								String type = data.getJSONObject(i).getString("type");

								/* The description Id will be the same for
								 * every observation in the same array data */
								String newData = beggining + descriptionId + "," + resourceId + ",";

								JSONArray observations = data.getJSONObject(i).getJSONArray("observations");
								for (int j = 0; j < observations.length(); j++) {
										/* unpacking the observation */
										int time = observations.getJSONObject(j).getInt("time");
										Double value = observations.getJSONObject(j).getDouble("value");
										String newObservation = newData + time + "," + value;

										newObservations.add(newObservation);
										//System.out.println(newObservation);
								}
						}
				} catch (JSONException ex) {
						System.out.println("***********************");
						System.out.println("Error reading the Json");
						System.out.println("***********************");
				}
				
				return newObservations;
		}
}