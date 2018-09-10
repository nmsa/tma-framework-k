package pt.uc.dei.eubr.atmosphere.tma.admin.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

public class ActuatorManager {

	public Long saveNewActuator(String address, String pubKey) {
		String sql = "INSERT INTO Actuator(address, pubKey) VALUES (?, ?)";
		PreparedStatement ps = null;
		
		try {
			ps = DatabaseManager.getConnectionInstance().prepareStatement(sql);
			ps.setString(1, address);
			ps.setString(2, pubKey);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DatabaseManager databaseManager = new DatabaseManager();
		return databaseManager.executeQuery(ps);
	}
	
	public void saveActions() {
	    
		//String jsonString = readJson();
        String filename = "/Users/josealexandredabruzzopereira/Projects/"
                          + "TMA_Admin_Console/src/main/resources/actions.json";
		readJson2(filename);
		/*JsonObject obj = new JsonObject(jsonString);
		JsonArray arr = obj.getAsJsonArray()("number");
		for (int i = 0; i < arr.length(); i++)
		    System.out.println(arr.getInt(i));*/
	}
	
    private void readJson2(String filename) {
    	Gson gson = new GsonBuilder().create();
    	File file = new File(filename);
		InputStream input;
		try {
			input = new FileInputStream(filename);
			InputStreamReader isr = new InputStreamReader(input);
	
			Object rawJson = gson.fromJson(isr, Object.class);	  
			LinkedTreeMap<String, Object> c = (LinkedTreeMap<String, Object>) rawJson;
			List<Object> actions = (List<Object>) c.get("actions");
			for (Object object : actions) {
				LinkedTreeMap<String, Object> actionData = (LinkedTreeMap<String, Object>) object;
				System.out.println(actionData.get("action"));
				System.out.println(actionData.get("resourceId"));
				List<Object> configuration = (List<Object>) actionData.get("configuration");
				for (Object config : configuration) {
					LinkedTreeMap<String, Object> configData = (LinkedTreeMap<String, Object>) config;
					System.out.println(configData.get("key"));
					System.out.println(configData.get("value"));
				}
				System.out.println(actionData);	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private String readJson() {
		StringBuilder sb = new StringBuilder();
		try {
			// pass the path to the file as a parameter 
		    FileReader fr = 
		      new FileReader("C:\\Users\\pankaj\\Desktop\\test.txt"); 

            int i;
		    while ((i=fr.read()) != -1) {
			  System.out.print((char) i);
			  sb.append((char) i);
		    }
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	
}
