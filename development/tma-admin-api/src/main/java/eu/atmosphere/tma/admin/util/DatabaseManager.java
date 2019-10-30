/**
 * <b>ATMOSPHERE</b> - http://www.atmosphere-eubrazil.eu
 **
 * <p>
 * <b>Trustworthiness Monitoring & Assessment Framework</b>
 * Component: Admin
 * <p>
 * Repository: https://github.com/eubr-atmosphere/tma-framework License:
 * https://github.com/eubr-atmosphere/tma-framework/blob/master/LICENSE
 * <p>
 * <p>
 */
package eu.atmosphere.tma.admin.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import eu.atmosphere.tma.admin.dto.Probe;
import eu.atmosphere.tma.admin.dto.Configuration;
import eu.atmosphere.tma.admin.dto.Action;
import eu.atmosphere.tma.admin.dto.Description;
import eu.atmosphere.tma.admin.dto.Resource;
import eu.atmosphere.tma.admin.dto.Actuator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class manages every interaction with the Knowledge database.
 * It connects and executes the statement on the database.
 * <p>
 *
 * @author Paulo Goncalves  <pgoncalves@student.dei.uc.pt>
 * @author Jose A. D. Pereira  <josep@dei.uc.pt>
 * @author Rui Silva <rfsilva@student.dei.uc.pt>
 * @author Nuno Antunes     <nmsa@dei.uc.pt>
 *  
 */
public class DatabaseManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseManager.class);

    public static int INVALID_KEY = -1;

    private static final String RESOURCE = "Resource";
    private static final String DESCRIPTION = "Description";
    private static final String ACTION = "Action";
    private static final String ACTUATOR = "Actuator";
    private static final String PROBE = "Probe";
    
    private static final String connectionString
            = "jdbc:mysql://mysql-0.mysql.default.svc.cluster.local:3306/knowledge";
    
    //Connection pool
    private static final HikariConfig CONFIG = new HikariConfig();
    private static final HikariDataSource DS;
     
    static {
        CONFIG.setJdbcUrl(connectionString);
        String userDB = PropertiesManager.getInstance().getProperty("userDB");
        byte[] decoded = Base64.getDecoder().decode(
                PropertiesManager.getInstance().getProperty("passwordProduction"));
        String password = new String(decoded);
        CONFIG.setUsername(userDB);
        CONFIG.setPassword(password);
        CONFIG.addDataSourceProperty("cachePrepStmts", "true");
        CONFIG.addDataSourceProperty("prepStmtCacheSize", "250");
        CONFIG.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        CONFIG.setAutoCommit(false);
        CONFIG.setMaximumPoolSize(Integer.parseInt(
                PropertiesManager.getInstance().getProperty("maxNumbeOfDatabaseConnections")));
        DS = new HikariDataSource(CONFIG);
    }
     
    public static Connection getConnectionFromPool() throws SQLException {
        return DS.getConnection();
    }
    
    /**
     * *
     * Prepared statements
     */
    private static final String SQL_GET_ACTUATOR_BY_ADDRESS
            = "SELECT address FROM Actuator WHERE address = ? ";
    private static final String SQL_GET_PROBE_NAME
            = "SELECT probeName FROM Probe WHERE probeName = ? ";
    private static final String SQL_GET_ACTION_NAME_BY_ACTUATORID
            = "SELECT actionName FROM Action WHERE actuatorId = ? AND actionName = ? ";
    private static final String SQL_GET_KEY_NAME_BY_ACTIONID
            = "SELECT keyName FROM Configuration WHERE actionId = ? AND keyName = ? ";
    private static final String SQL_NEW_RESOURCE_WITH_ID
            = "INSERT INTO "
            + "Resource(resourceId, resourceName, resourceType, resourceAddress) "
            + "VALUES "
            + "(?, ?, ?, ?)";
    private static final String SQL_NEW_DESCRIPTION_WITH_ID
            = "INSERT "
            + "  INTO Description(descriptionId, dataType, descriptionName, unit) "
            + "VALUES (?, ?, ?, ?)";
    private static final String SQL_NEW_ACTION_WITH_ID
            = "INSERT "
            + "  INTO Action(actionId, actuatorId, resourceId, actionName) "
            + "VALUES (?, ?, ?, ?)";
    private static final String SQL_NEW_CONFIGURATION_WITH_ID
            = "INSERT "
            + "  INTO Configuration(configurationId, actionId, keyName, domain) "
            + "VALUES (?, ?, ?, ?)";
    private static final String SQL_NEW_ACTUATOR_WITH_ID
            = "INSERT INTO Actuator(actuatorId, address, pubKey) VALUES (?, ?, ?)";
    private static final String SQL_NEW_PROBE_WITH_ID
            = "INSERT INTO "
            + "Probe(probeId, probeName, password, salt, token, tokenExpiration) "
            + "VALUES "
            + "(?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE_ACTION
            = "DELETE FROM Action WHERE actionId = ? ";
    private static final String SQL_DELETE_CONFIGURATION_WITH_ACTION_ID
            = "DELETE FROM Configuration WHERE actionId = ? ";
    private static final String SQL_DELETE_CONFIGURATION_ID
            = "DELETE FROM Configuration WHERE configurationId = ? ";
    private static final String SQL_DELETE_PROBE
            = "DELETE FROM Probe WHERE probeId = ? ";
    private static final String SQL_SELECT_ACTION
            = "SELECT * FROM Action WHERE actuatorId = ? ";
    private static final String SQL_SELECT_RESOURCE
            = "SELECT * FROM Resource WHERE resourceId = ? ";
    private static final String SQL_SELECT_CONFIGURATION
            = "SELECT * FROM Configuration WHERE actionId = ? ";
    private static final String SQL_SELECT_ACTUATOR
            = "SELECT * FROM Actuator";
    private static final String SQL_SELECT_PROBE
            = "SELECT * FROM Probe";

    public Connection getConnection() {
        // This will load the MySQL driver, each DB has its own driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("[ATMOSPHERE] Driver not found.", e);
        }
        // Setup the connection with the DB
        try {

            String userDB = PropertiesManager.getInstance().getProperty("userDB");
            byte[] decoded = Base64.getDecoder().decode(PropertiesManager.getInstance().getProperty("passwordProduction"));
            String password = new String(decoded);

            //Connection c = DatabaseManager getConnection(productionConnection, userDB, password);
            Connection c = DatabaseManager.getConnectionFromPool();

            c.setAutoCommit(false);
            return c;
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when connecting to the database", e);
        }
        return null;
    }

    private static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOGGER.error("[ATMOSPHERE] Error when closing the connection to the database.", ex);
            }
        }
    }

    /**
     * TODO: This strategy is far from the best from a concurrency point of
     * view.
     * <p>
     */
    public int getIdFromDatabase(int partnerId, String table) {
        //Selects the highest tableId where the tableId starts with the partnerId
        //specified followed by 3 characters (numbers) that can be anything else.
        String sql
                = "SELECT " + table + "Id "
                + " FROM " + table
                + " WHERE " + table + "Id"
                + " LIKE \"" + partnerId
                + "___\"ORDER BY " + table + "Id"
                + " DESC LIMIT 1";

        Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {
            try (Statement stat = con.createStatement()) {
                ResultSet resultSet = stat.executeQuery(sql);
                if (resultSet.next()) {
                    return resultSet.getInt(table + "Id") + 1;
                } else {
                    return Integer.parseInt(partnerId + "001");
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", sqlex);
            return -1;
        } finally {
            close(con);
        }
    }

    public int isActuatorAddressRepeated(String address) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = con.prepareStatement(SQL_GET_ACTUATOR_BY_ADDRESS)) {
                ps.setString(1, address);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the repeated Item.", sqlex);
            return -1;
        } finally {
            close(con);
        }
    }

    public int isProbeNameRepeated(String probeName) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = con.prepareStatement(SQL_GET_PROBE_NAME)) {
                ps.setString(1, probeName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the repeated Item.", sqlex);
            return -1;
        } finally {
            close(con);
        }
    }

    public int isActionNameRepeated(Action action) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(SQL_GET_ACTION_NAME_BY_ACTUATORID)) {
                ps.setInt(1, action.getActuatorId());
                ps.setString(2, action.getActionName());
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the repeated Item.", sqlex);
            return -1;
        } finally {
            close(con);
        }
    }

    public int isConfigurationNameRepeated(Configuration configuration) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(SQL_GET_KEY_NAME_BY_ACTIONID)) {
                ps.setInt(1, configuration.getActionId());
                ps.setString(2, configuration.getKeyName());
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the repeated Item.", sqlex);
            return -1;
        } finally {
            close(con);
        }
    }

    public void saveNewResource(Resource resource) {
        if (resource.getPartnerId() != -1) {
            resource.setResourceId(getIdFromDatabase(resource.getPartnerId(), RESOURCE));
            saveNewResourceWithPartnerId(resource);
        } else {
            resource.setPartnerId(99);
            resource.setResourceId(getIdFromDatabase(resource.getPartnerId(), RESOURCE));
            saveNewResourceWithPartnerId(resource);
        }
    }

    public boolean saveNewResourceWithPartnerId(Resource resource) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(SQL_NEW_RESOURCE_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, resource.getResourceId());
                ps.setString(2, resource.getResourceName());
                ps.setString(3, resource.getResourceType());
                ps.setString(4, resource.getResourceAddress());

                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    resource.setResourceId(generatedKeys.getInt(1));
                } else {
                    resource.setResourceId(INVALID_KEY);
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a resource in the database.", e);
            resource.setResourceId(INVALID_KEY);
            return false;
        } finally {
            close(con);
        }

    }

    public void saveNewDescription(Description description) {
        if (description.getPartnerId() != -1) {
            description.setDescriptionId(getIdFromDatabase(description.getPartnerId(), DESCRIPTION));
            saveNewDescriptionWithPartnerId(description);
        } else {
            description.setPartnerId(99);
            description.setDescriptionId(getIdFromDatabase(description.getPartnerId(), DESCRIPTION));
            saveNewDescriptionWithPartnerId(description);
        }
    }

    public boolean saveNewDescriptionWithPartnerId(Description description) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_NEW_DESCRIPTION_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, description.getDescriptionId());
                ps.setString(2, description.getDataType());
                ps.setString(3, description.getDescriptionName());
                ps.setString(4, description.getUnit());

                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    description.setDescriptionId(generatedKeys.getInt(1));
                } else {
                    description.setDescriptionId(INVALID_KEY);
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a description in the database.", e);
            description.setDescriptionId(-1);
            return false;
        } finally {
            close(con);
        }
    }

    public int saveNewAction(Action action) {
        if (action.getPartnerId() != -1) {
            action.setActionId(getIdFromDatabase(action.getPartnerId(), ACTION));
            saveNewActionWithPartnerId(action);
        } else {
            action.setPartnerId(99);
            action.setActionId(getIdFromDatabase(action.getPartnerId(), ACTION));
            saveNewActionWithPartnerId(action);
        }

        if (action.getActionId() == -1) {
            return -1;
        }
        return 0;
    }

    //Action Manager
    public boolean saveNewActionWithPartnerId(Action action) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_NEW_ACTION_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, action.getActionId());
                ps.setInt(2, action.getActuatorId());
                ps.setInt(3, action.getResourceId());
                ps.setString(4, action.getActionName());

                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    action.setActionId(generatedKeys.getInt(1));
                } else {
                    action.setActionId(INVALID_KEY);
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting an action in the database.", e);
            action.setActionId(-1);
            return false;
        } finally {
            close(con);
        }
    }

    public int saveNewConfiguration(Configuration configuration) {
        if (configuration.getPartnerId() != -1) {
            configuration.setConfigurationId(getIdFromDatabase(configuration.getPartnerId(), "Configuration"));
            saveNewConfigurationWithPartnerId(configuration);
        } else {
            configuration.setPartnerId(99);
            configuration.setConfigurationId(getIdFromDatabase(configuration.getPartnerId(), "Configuration"));
            saveNewConfigurationWithPartnerId(configuration);
        }

        if (configuration.getActionId() == -1) {
            return -1;
        }
        return 1;
    }

    public boolean saveNewConfigurationWithPartnerId(Configuration configuration) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_NEW_CONFIGURATION_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, configuration.getConfigurationId());
                ps.setInt(2, configuration.getActionId());
                ps.setString(3, configuration.getKeyName());
                ps.setString(4, configuration.getDomain());

                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    configuration.setConfigurationId(generatedKeys.getInt(1));
                } else {
                    configuration.setConfigurationId(INVALID_KEY);
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a configuration in the database.", e);
            configuration.setConfigurationId(-1);
            return false;
        } finally {
            close(con);
        }
    }

    public int saveNewActuator(Actuator actuator) {
        if (actuator.getPartnerId() != -1) {
            actuator.setActuatorId(getIdFromDatabase(actuator.getPartnerId(), ACTUATOR));
            return saveNewActuatorWithPartnerId(actuator);
        } else {
            actuator.setPartnerId(99);
            actuator.setActuatorId(getIdFromDatabase(actuator.getPartnerId(), ACTUATOR));
            return saveNewActuatorWithPartnerId(actuator);
        }
    }

    public int saveNewActuatorWithPartnerId(Actuator actuator) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        int key;
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_NEW_ACTUATOR_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, actuator.getActuatorId());
                ps.setString(2, actuator.getAddress());
                ps.setString(3, Base64.getEncoder().encodeToString(actuator.getPubKeyBytes()));

                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    key = generatedKeys.getInt(1);
                } else {
                    key = INVALID_KEY;
                }
                con.commit();
                return key;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting an actuator in the database.", e);
            return -1;
        } finally {
            close(con);
        }
    }

    public void saveNewProbe(Probe probe) {
        if (probe.getPartnerId() != -1) {
            probe.setProbeId(getIdFromDatabase(probe.getPartnerId(), PROBE));
            saveNewProbeWithPartnerId(probe);
        } else {
            probe.setPartnerId(99);
            probe.setProbeId(getIdFromDatabase(probe.getPartnerId(), PROBE));
            saveNewProbeWithPartnerId(probe);
        }
    }

    public boolean saveNewProbeWithPartnerId(Probe probe) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_NEW_PROBE_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, probe.getProbeId());
                ps.setString(2, probe.getProbeName());
                ps.setString(3, probe.getPassword());
                ps.setString(4, probe.getSalt());
                ps.setString(5, probe.getToken());
                ps.setTimestamp(6, new Timestamp(probe.getTokenExpiration()));

                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    probe.setProbeId(generatedKeys.getInt(1));
                } else {
                    probe.setProbeId(INVALID_KEY);
                }
                con.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a resource in the database.", e);
            probe.setProbeId(-1);
            return false;
        } finally {
            close(con);
        }
    }

    public boolean deleteAction(int actionId) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_DELETE_CONFIGURATION_WITH_ACTION_ID)) {
                ps.setInt(1, actionId);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the configurations associated with the action.", ex);
            return false;
        }

        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_DELETE_ACTION)) {
                ps.setInt(1, actionId);
                ps.executeUpdate();

                con.commit();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the action.", ex);
            return false;
        } finally {
            close(con);
        }
    }

    public boolean deleteProbe(int probeId) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(SQL_DELETE_PROBE)) {
                ps.setInt(1, probeId);
                ps.executeUpdate();

                con.commit();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the probe.", ex);
            return false;
        } finally {
            close(con);
        }
    }

    public boolean deleteConfiguration(int configurationId) {
                Connection con;
        try {
            con = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_DELETE_CONFIGURATION_ID)) {
                ps.setInt(1, configurationId);
                ps.executeUpdate();

                con.commit();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the configuration.", ex);
            return false;
        } finally {
            close(con);
        }
    }

    public ArrayList<Action> getActions(int actuatorId) throws SQLException {
        Action newAction;
        ArrayList<Action> actions = new ArrayList<>();

        Connection con = DatabaseManager.getConnectionFromPool();
        
        try {
            try (PreparedStatement ps = con.prepareStatement(SQL_SELECT_ACTION)) {
                ps.setInt(1, actuatorId);
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    newAction = new Action(
                            resultSet.getInt("actuatorId"),
                            resultSet.getInt("resourceId"),
                            resultSet.getString("actionName"));
                    newAction.setActionId(resultSet.getInt("actionId"));

                    try (PreparedStatement psResource = con.prepareStatement(
                            SQL_SELECT_RESOURCE)) {
                        ResultSet resResource;

                        psResource.setInt(1, resultSet.getInt("resourceId"));

                        resResource = psResource.executeQuery();
                        if (!resResource.first()) {
                            LOGGER.error("[ATMOSPHERE] An action has a resourceId that doesn't exist.");
                            return null;
                        }
                        newAction.setResourceName(resResource.getString("resourceName"));
                        actions.add(newAction);
                    }
                }
                return actions;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the actions from the database.", ex);
            return null;
        } finally {
            close(con);
        }
    }

    public ArrayList<Configuration> getConfigurations(int actionId) throws SQLException {
        Configuration newConfiguration;
        ArrayList<Configuration> configurations = new ArrayList<>();

        
        Connection con = DatabaseManager.getConnectionFromPool();
        try {
            try (PreparedStatement ps = con.prepareStatement(
                    SQL_SELECT_CONFIGURATION)) {
                ps.setInt(1, actionId);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    newConfiguration = new Configuration(
                            -1,
                            rs.getInt("configurationId"),
                            rs.getInt("actionId"),
                            rs.getString("keyName"),
                            rs.getString("domain"));
                    configurations.add(newConfiguration);
                }

                return configurations;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the configurations from the database.", ex);
            return null;
        } finally {
            close(con);
        }
    }

    //Get Resources
    public ArrayList<Resource> getResources(int actuatorId) throws SQLException {
        //Gets the partner Id of the actuator
        int partnerId = actuatorId / 1000;
        //Gets the resources that have the same partner Id as the actuator
        //Selects every resource where the resourceId starts with the partnerId
        //of the actuator, followed by 3 characters (numbers) that can be anything else.
        String sql = "SELECT * FROM Resource WHERE resourceId LIKE \"" + partnerId + "___\"";
        Resource newResource;
        ArrayList<Resource> resources = new ArrayList<>();

        
        Connection con = DatabaseManager.getConnectionFromPool();
        try {
            try (Statement statement = con.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);

                while (rs.next()) {
                    newResource = new Resource(
                            rs.getString("resourceName"),
                            rs.getString("resourceType"),
                            rs.getString("resourceAddress"));
                    newResource.setResourceId(rs.getInt("resourceId"));
                    resources.add(newResource);
                }
                return resources;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the actuators from the database.", ex);
            return null;
        } finally {
            close(con);
        }
    }

    //Get Actuators
    public ArrayList<Actuator> getActuators() throws SQLException {
        Actuator newActuator;
        ArrayList<Actuator> actuators = new ArrayList<>();

        
        Connection con = DatabaseManager.getConnectionFromPool();
        try {
            try (Statement statement = con.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_ACTUATOR);
                while (resultSet.next()) {
                    newActuator = new Actuator(
                            resultSet.getString("address"),
                            resultSet.getString("pubKey"));
                    newActuator.setActuatorId(resultSet.getInt("actuatorId"));
                    actuators.add(newActuator);
                }
            }
            return actuators;
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the actuators from the database.", ex);
            return null;
        } finally {
            close(con);
        }
    }

    //Get Probes
    public ArrayList<Probe> getProbes() throws SQLException {
        
        Connection con = DatabaseManager.getConnectionFromPool();
        try {
            ArrayList<Probe> probes = new ArrayList<>();
            try (Statement statement = con.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_PROBE);
                while (resultSet.next()) {
                    System.out.println(resultSet.getTimestamp("tokenExpiration"));
                    probes.add(new Probe(
                            resultSet.getInt("probeId"),
                            resultSet.getString("probeName"),
                            resultSet.getString("token"),
                            resultSet.getTimestamp("tokenExpiration").toString()));
                }
                return probes;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the probes from the database.", ex);
            return null;
        } finally {
            close(con);
        }
    }
}
