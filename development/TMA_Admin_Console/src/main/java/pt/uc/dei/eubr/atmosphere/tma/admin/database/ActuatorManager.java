package pt.uc.dei.eubr.atmosphere.tma.admin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    public Long saveActions(List<Action> actions) {
        String sql = "INSERT INTO Action(actuatorId, resourceId, actionName) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = DatabaseManager.getConnectionInstance().prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setInt(2, 1);
            ps.setString(3, "resourceName");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.executeQuery(ps);
    }

    public void saveActions() {
        String filename = "/Users/josealexandredabruzzopereira/Projects/"
                          + "TMA_Admin_Console/src/main/resources/actions.json";
        parseActionsJsonFile(filename);
    }

    private List<Action> parseActionsJsonFile(String filename) {
        Gson gson = new GsonBuilder().create();
        InputStream input;
        List<Action> actions = new ArrayList<Action>();
        try {
            input = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(input);

            Object rawJson = gson.fromJson(isr, Object.class);
            LinkedTreeMap<String, Object> c = (LinkedTreeMap<String, Object>) rawJson;
            List<Object> actionsJson = (List<Object>) c.get("actions");
            for (Object object : actionsJson) {
                LinkedTreeMap<String, Object> actionData = (LinkedTreeMap<String, Object>) object;
                actionData.get("resourceId");
                Action action = new Action(-1, (Integer) actionData.get("resourceId"),
                        actionData.get("action").toString());
                List<Object> configuration = (List<Object>) actionData.get("configuration");
                for (Object configJson : configuration) {
                    LinkedTreeMap<String, Object> configData = (LinkedTreeMap<String, Object>) configJson;
                    Configuration conf = new Configuration(configData.get("key").toString(),
                            configData.get("value").toString());
                    action.addConfiguration(conf);
                }
                System.out.println(actionData);
                actions.add(action);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return actions;
    }
}
