package eu.atmosphere.tma.admin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.mysql.jdbc.Statement;

public class ActionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActionManager.class);

    public void saveNewActions(String filename, int actuatorId) {
        // One example of file is the one of the repository: src/main/resources/actions.json
        List<Action> actions = parseActionsJsonFile(filename, actuatorId);
        saveActions(actions, actuatorId);
    }

    private void saveActions(List<Action> actions, int actuatorId) {
        String sql = "INSERT INTO Action(actuatorId, resourceId, actionName) "
                + "VALUES (?, ?, ?)";
        PreparedStatement ps;
        DatabaseManager databaseManager = new DatabaseManager();

        try {
            for (Action action : actions) {
                ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, actuatorId);
                ps.setInt(2, action.getResourceId());
                ps.setString(3, action.getActionName());

                int actionId = databaseManager.execute(ps);
                saveConfiguration(actionId, action.getConfiguration());
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting an action in the database.", e);
        }
    }

    private List<Action> parseActionsJsonFile(String filename, int actuatorId) {
        Gson gson = new GsonBuilder().create();
        InputStream input;
        List<Action> actions = new ArrayList<>();
        try {
            input = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(input);

            Object rawJson = gson.fromJson(isr, Object.class);
            LinkedTreeMap<String, Object> c = (LinkedTreeMap<String, Object>) rawJson;
            List<Object> actionsJson = (List<Object>) c.get("actions");
            for (Object object : actionsJson) {
                LinkedTreeMap<String, Object> actionData = (LinkedTreeMap<String, Object>) object;
                Double resourceId = Double.parseDouble(actionData.get("resourceId").toString().trim());
                Action action = new Action(actuatorId, resourceId.intValue(), actionData.get("action").toString());
                List<Object> configuration = (List<Object>) actionData.get("configuration");
                for (Object configJson : configuration) {
                    LinkedTreeMap<String, Object> configData = (LinkedTreeMap<String, Object>) configJson;
                    Configuration conf = new Configuration(configData.get("key").toString(),
                            configData.get("value").toString());
                    action.addConfiguration(conf);
                }
                actions.add(action);
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("[ATMOSPHERE] Action JSON File not found.", e);
        }
        return actions;
    }

    private void saveConfiguration(int actionId, List<Configuration> configurationList) {
        String sql = "INSERT INTO Configuration(actionId, keyName, domain) VALUES (?, ?, ?)";
        PreparedStatement ps;

        try {
            for (Configuration configuration : configurationList) {
                ps = DatabaseManager.getConnectionInstance().prepareStatement(sql);
                ps.setInt(1, actionId);
                ps.setString(2, configuration.getKeyName());
                ps.setString(3, configuration.getDomain());

                DatabaseManager databaseManager = new DatabaseManager();
                databaseManager.execute(ps);
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a configuration in the database.", e);
        }
    }
}
