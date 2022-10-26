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
import eu.atmosphere.tma.admin.dto.ConfigurationDashboard;
import eu.atmosphere.tma.admin.dto.Action;
import eu.atmosphere.tma.admin.dto.ActionDashboard;
import eu.atmosphere.tma.admin.dto.Description;
import eu.atmosphere.tma.admin.dto.Resource;
import eu.atmosphere.tma.admin.dto.Actuator;
import eu.atmosphere.tma.admin.dto.ConfigurationProfile;
import eu.atmosphere.tma.admin.dto.DataSetElem;
import eu.atmosphere.tma.admin.dto.DataSetPlot;
import eu.atmosphere.tma.admin.dto.LeafAttribute;
import eu.atmosphere.tma.admin.dto.Metric;
import eu.atmosphere.tma.admin.dto.MetricDashboard;
import eu.atmosphere.tma.admin.dto.PlanInfo;
import eu.atmosphere.tma.admin.dto.PlotConfig;
import eu.atmosphere.tma.admin.dto.Preference;
import eu.atmosphere.tma.admin.dto.QualityModel;
import eu.atmosphere.tma.admin.dto.Score;
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
                PropertiesManager.getInstance().getProperty("maxNumberOfDatabaseConnections")));
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
    private static final String SQL_GET_SCORES
            = "SELECT * FROM "
            + "MetricData WHERE metricId = ? and valueTime >= ? "
            + "ORDER BY valueTime "
            + "LIMIT 100;";
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
    
    //================================ DASHBOARD ADDED SQLS ====================================================//
    
                                //++++++++ GET EVERYTHING SQL COMMANDS ++++++++
    private static final String SQL_GET_METRICS
            = "SELECT * FROM Metric WHERE metricId LIKE ? or metricName LIKE ? ";
    private static final String SQL_GET_QUALITY_MODELS
            = "SELECT qm.*, m.metricName FROM QualityModel as qm, Metric as m "
            + "WHERE qm.metricId = m.metricId AND (qualityModelId LIKE ? or modelName LIKE ?) AND "
            + "(qm.metricId LIKE ? or m.metricName LIKE ?)";
    private static final String SQL_GET_DESCRIPTIONS
            = "SELECT * FROM Description WHERE descriptionId LIKE ? or descriptionName LIKE ? ";
    //could be named as SQL_GET_CONFIGURATION_PROFILES, but in the dashboard they are listed for a Quality Model
    private static final String SQL_GET_QUALITY_MODEL_CONFIGURATION_PROFILES
            = "SELECT configurationProfileId, profileName FROM ConfigurationProfile WHERE qualityModelId = ?";
    //the same as SQL_GET_METRICS but the outcome represents metrics not associated to Quality Models
    private static final String SQL_GET_METRICS_TO_CREATE_QUALITY_MODEL
            = "SELECT * FROM Metric m "
            + "LEFT JOIN QualityModel qm on m.metricId = qm.metricId "
            + "WHERE qm.qualityModelId is NULL";
    private static final String SQL_GET_ACTIVE_RESOURCES_WHICH_HAVE_ACTIONS
            = "SELECT r.* FROM Resource r join Action a on r.resourceId = a.resourceId WHERE active = true "
            + "GROUP BY resourceId HAVING count(*) > 0";
    private static final String SQL_GET_ACTIVE_RESOURCES
            = "SELECT * FROM Resource WHERE active = true";
    private static final String SQL_GET_PLOTS_CONFIGS
            = "SELECT * FROM PlotConfig";
    private static final String SQL_GET_ACTIONS_AND_CONFIGURATIONS
            = "SELECT * FROM Action a LEFT JOIN Configuration c on a.actionId = c.actionId";
    
    
                                //++++++++ GET BY SOME ATTRIBUTES (FILTERS) SQL COMMANDS ++++++++
    private static final String SQL_GET_METRIC_BY_ID
            = "SELECT * FROM Metric WHERE metricId = ?";
    private static final String SQL_GET_CHILD_METRICS_OF_PARENT_METRIC_BY_ID
            = "SELECT childMetric, metricName, attributeAggregationOperator, blockLevel "
            + "FROM CompositeAttribute, Metric WHERE parentMetric  = ? "
            + "and metricId = childMetric";
    private static final String SQL_GET_QUALITY_MODEL_BY_ID
            = "SELECT * FROM QualityModel WHERE qualityModelId = ?";
    private static final String SQL_GET_CONFIGURATION_PROFILE_BY_ID
            = "SELECT * FROM ConfigurationProfile WHERE configurationProfileId = ?";
    private static final String SQL_GET_PREFERENCES_BY_CONFIGURATION_PROFILE_ID
            = "SELECT * FROM Preference WHERE configurationProfileId = ?";
    private static final String SQL_GET_LEAF_ATTRIBUTE_BY_METRIC_ID
            = "SELECT LA.*, M.metricName, M.blockLevel, D.descriptionName, D.unit "
            + "FROM LeafAttribute LA, Metric M, Description D "
            + "WHERE D.descriptionId = LA.descriptionId AND M.metricId = LA.metricId AND M.metricId = ?";
    private static final String SQL_GET_CONFIGURATION_PROFILE_ID_BY_RESOURCE_ID
            = "SELECT configurationProfileID FROM Resource WHERE resourceId = ?";
    private static final String SQL_GET_QUALITY_MODEL_ID_BY_CONFIGURATION_PROFILE_ID
            = "SELECT qualityModelId FROM ConfigurationProfile WHERE configurationProfileID = ?";
    private static final String SQL_GET_LIST_OF_METRICS_NOT_LEAF_BY_CONFIGURATION_PROFILE_ID
            = "SELECT m.metricId, m.metricName from Metric m join Preference p on m.metricId = p.metricId "
            + "WHERE p.configurationProfileID = ?";
    private static final String SQL_GET_LIST_OF_LEAF_METRICS_BY_CONFIGURATION_PROFILE_ID
            = "SELECT m.metricId, m.metricName from Metric m "
            + "join Preference p on m.metricId = p.metricId "
            + "join LeafAttribute la on m.metricId = la.metricId "
            + "WHERE p.configurationProfileID = ?";
    private static final String SQL_GET_DESCRIPTION_INFO_BY_METRIC_ID
            = "SELECT d.* From Description d JOIN LeafAttribute la on d.descriptionId = la.descriptionId "
            + "WHERE la.metricId = ?";
    private static final String SQL_GET_TIME_SLOT_RAW_DATA_BY_RESOURCE_ID_AND_DESCRIPTION_ID
            = "SELECT value, UNIX_TIMESTAMP(valueTime) as valueTime FROM Data WHERE resourceId = ?  "
            + "AND descriptionId = ? AND valueTime >= FROM_UNIXTIME(?) AND valueTime <= FROM_UNIXTIME(?) "
            + "ORDER BY valueTime ASC";
    private static final String SQL_GET_TIME_SLOT_METRIC_DATA_BY_RESOURCE_ID_AND_BY_METRIC_ID
            = "SELECT value, UNIX_TIMESTAMP(valueTime) as valueTime FROM MetricData WHERE resourceId = ?  "
            + "AND metricId = ? AND valueTime >= FROM_UNIXTIME(?) AND valueTime <= FROM_UNIXTIME(?) "
            + "ORDER BY valueTime ASC";
    private static final String SQL_GET_TIME_SLOT_PLANS_IDS_BY_RESOURCE_ID_AND_METRIC_ID
            = "SELECT planId, UNIX_TIMESTAMP(valueTime) as valueTime FROM Plan WHERE status = 2 AND resourceId = ? AND metricId = ? "
            + "AND valueTime >= FROM_UNIXTIME(?) AND valueTime <= FROM_UNIXTIME(?) ORDER BY valueTime ASC";
    private static final String SQL_GET_ACTIONS_AND_CONFIGURATIONS_BY_RESOURCE_ID
            = "SELECT * FROM Action a LEFT JOIN Configuration c on a.actionId = c.actionId WHERE resourceId = ?";
    
    
    
                                //++++++++ INSERT/CREATE SQL COMMANDS ++++++++
    private static final String SQL_NEW_QUALITY_MODEL
            = "INSERT "
            + "INTO QualityModel(modelName, modelDescriptionReference, businessThreshold, metricId)"
            + "VALUES (?, ?, ?, ?)";
    private static final String SQL_NEW_CONFIGURATION_PROFILE
            = "INSERT "
            + "INTO ConfigurationProfile(profileName, qualityModelId)"
            + "VALUES (?, ?)";
    private static final String SQL_NEW_PREFERENCE
            = "INSERT INTO Preference VALUES (?, ?, ?, ?)";
    private static final String SQL_NEW_METRIC
            = "INSERT INTO Metric(metricName) VALUES (?)";
    private static final String SQL_NEW_LEAF_ATTRIBUTE
            = "INSERT INTO LeafAttribute  VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_NEW_COMPOSITE_ATTRIBUTE
            = "INSERT INTO CompositeAttribute VALUES (?, ?, ?)";
    private static final String SQL_NEW_PLOT_CONFIG
            = "INSERT INTO PlotConfig (configObject, plotConfigName) VALUES (?, ?)";
    
    
                                //++++++++ UPDATE SQL COMMANDS ++++++++
    private static final String SQL_UPDATE_PLOT_CONFIG_BY_ID
            = "UPDATE PlotConfig SET configObject = ?, plotConfigName = ? WHERE plotConfigId = ?";
    
    
    
                                //++++++++ VERIFICATIONS SQL COMMANDS ++++++++
    private static final String SQL_GET_PLOT_CONFIG_NAME
            = "SELECT plotConfigName FROM PlotConfig WHERE plotConfigName = ? ";
    private static final String SQL_GET_QUALITY_MODEL_NAME
            = "SELECT modelName FROM QualityModel WHERE modelName = ? ";
    private static final String SQL_GET_CONFIGURATION_PROFILE_NAME_FOR_QUALITY_MODEL_ID
            = "SELECT profileName FROM ConfigurationProfile WHERE profileName = ? and qualityModelId = ? ";
    private static final String SQL_GET_METRIC_NAME
            = "SELECT metricName FROM Metric WHERE metricName = ? ";
    //this sql is the base for extension when searching for a metric with the same number of and same child metrics,
    //when it is wanted to create a new metric that isn't a leat attribute
    private static final String SQL_BASE_GET_NUMBER_OF_CHILD_METRICS
            = "SELECT parentMetric FROM CompositeAttribute ca WHERE (ca.childMetric = ?";
    
                                //++++++++ DELETE SQL COMMANDS ++++++++
    private static final String SQL_DELETE_PLOT_CONFIG_BY_ID
            = "DELETE FROM PlotConfig WHERE plotConfigId = ?";
    
    
    
    public Connection getConnection() {
        // This will load the MySQL driver, each DB has its own driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error("[ATMOSPHERE] Driver not found.", e);
        }
        // Setup the connection with the DB
        try {

            Connection c = DatabaseManager.getConnectionFromPool();

            c.setAutoCommit(false);
            return c;
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when connecting to the database", e);
        }
        return null;
    }

    private static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
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

        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {
            try (Statement stat = conn.createStatement()) {
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
            close(conn);
        }
    }

    public int isActuatorAddressRepeated(String address) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_ACTUATOR_BY_ADDRESS)) {
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
            close(conn);
        }
    }

    public int isProbeNameRepeated(String probeName) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_PROBE_NAME)) {
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
            close(conn);
        }
    }

    public int isActionNameRepeated(Action action) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_ACTION_NAME_BY_ACTUATORID)) {
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
            close(conn);
        }
    }

    public int isConfigurationNameRepeated(Configuration configuration) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_KEY_NAME_BY_ACTIONID)) {
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
            close(conn);
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
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_NEW_RESOURCE_WITH_ID, Statement.RETURN_GENERATED_KEYS)) {
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
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a resource in the database.", e);
            resource.setResourceId(INVALID_KEY);
            return false;
        } finally {
            close(conn);
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
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(
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
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a description in the database.", e);
            description.setDescriptionId(-1);
            return false;
        } finally {
            close(conn);
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
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(
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
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting an action in the database.", e);
            action.setActionId(-1);
            return false;
        } finally {
            close(conn);
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
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(
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
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a configuration in the database.", e);
            configuration.setConfigurationId(-1);
            return false;
        } finally {
            close(conn);
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
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return -1;
        }
        int key;
        try {
            try (PreparedStatement ps = conn.prepareStatement(
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
                conn.commit();
                return key;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting an actuator in the database.", e);
            return -1;
        } finally {
            close(conn);
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
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(
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
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a resource in the database.", e);
            probe.setProbeId(-1);
            return false;
        } finally {
            close(conn);
        }
    }

    public boolean deleteAction(int actionId) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    SQL_DELETE_CONFIGURATION_WITH_ACTION_ID)) {
                ps.setInt(1, actionId);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the configurations associated with the action.", ex);
            return false;
        }

        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    SQL_DELETE_ACTION)) {
                ps.setInt(1, actionId);
                ps.executeUpdate();

                conn.commit();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the action.", ex);
            return false;
        } finally {
            close(conn);
        }
    }

    public boolean deleteProbe(int probeId) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_DELETE_PROBE)) {
                ps.setInt(1, probeId);
                ps.executeUpdate();

                conn.commit();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the probe.", ex);
            return false;
        } finally {
            close(conn);
        }
    }

    public boolean deleteConfiguration(int configurationId) {
                Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the Id associated to the partner.", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    SQL_DELETE_CONFIGURATION_ID)) {
                ps.setInt(1, configurationId);
                ps.executeUpdate();

                conn.commit();
                return true;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when deleting the configuration.", ex);
            return false;
        } finally {
            close(conn);
        }
    }

    public ArrayList<Action> getActions(int actuatorId) throws SQLException {
        Action newAction;
        ArrayList<Action> actions = new ArrayList<>();

        Connection conn = DatabaseManager.getConnectionFromPool();
        
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ACTION)) {
                ps.setInt(1, actuatorId);
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    newAction = new Action(
                            resultSet.getInt("actuatorId"),
                            resultSet.getInt("resourceId"),
                            resultSet.getString("actionName"));
                    newAction.setActionId(resultSet.getInt("actionId"));

                    try (PreparedStatement psResource = conn.prepareStatement(
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
            close(conn);
        }
    }

    public ArrayList<Configuration> getConfigurations(int actionId) throws SQLException {
        Configuration newConfiguration;
        ArrayList<Configuration> configurations = new ArrayList<>();

        
        Connection conn = DatabaseManager.getConnectionFromPool();
        try {
            try (PreparedStatement ps = conn.prepareStatement(
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
            close(conn);
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

        
        Connection conn = DatabaseManager.getConnectionFromPool();
        try {
            try (Statement statement = conn.createStatement()) {
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
            close(conn);
        }
    }

    //Get Actuators
    public ArrayList<Actuator> getActuators() throws SQLException {
        Actuator newActuator;
        ArrayList<Actuator> actuators = new ArrayList<>();

        
        Connection conn = DatabaseManager.getConnectionFromPool();
        try {
            try (Statement statement = conn.createStatement()) {
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
            close(conn);
        }
    }

    //Get Probes
    public ArrayList<Probe> getProbes() throws SQLException {
        
        Connection conn = DatabaseManager.getConnectionFromPool();
        try {
            ArrayList<Probe> probes = new ArrayList<>();
            try (Statement statement = conn.createStatement()) {
                ResultSet resultSet = statement.executeQuery(SQL_SELECT_PROBE);
                while (resultSet.next()) {
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
            close(conn);
        }
    }

    //Get Scores
    public ArrayList<Score> getScores(Metric metric) throws SQLException {
        
        Connection conn = DatabaseManager.getConnectionFromPool();
        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    SQL_GET_SCORES)) {
                ArrayList<Score> scores = new ArrayList<>();
                Score newScore;
                ps.setInt(1, metric.getMetricId());
                ps.setTimestamp(2, new Timestamp(metric.getTimestamp()));
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    newScore = new Score(
                            rs.getDouble("value"),
                            rs.getInt("resourceId"),
                            rs.getTimestamp("valueTime").getTime() 
                    );
                    scores.add(newScore);
                }
                return scores;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when getting the scores from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
    }

    
    //=====================================  DASHBOARD IMPLEMENTATION =============================================
    
    //Get Metrics - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public ArrayList<MetricDashboard> getMetrics(String filter, boolean createQualityModel) throws SQLException {
        
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        PreparedStatement ps = null;
        try {
            ArrayList<MetricDashboard> metrics = new ArrayList<>();
            //if the request wants the metrics available for creating quality models,
            //send those which are not yet associated to quality models
            if(createQualityModel){
                ps = conn.prepareStatement(SQL_GET_METRICS_TO_CREATE_QUALITY_MODEL);
            }
            else{
                ps = conn.prepareStatement(SQL_GET_METRICS);
                ps.setString(1, "%" + filter + "%");
                ps.setString(2, "%" + filter + "%");
            }
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                metrics.add(new MetricDashboard(
                        resultSet.getInt("metricId"),
                        resultSet.getString("metricName"),
                        resultSet.getInt("blockLevel")));
            }
            return metrics;
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the metrics from the database.", ex);
            return null;
        } finally {
            if(ps != null){
                ps.close();
            }
            close(conn);
        }
    }
    
    //Get Metric by Id - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public MetricDashboard getMetricById(int metricId) throws SQLException {
        
        Connection conn;
        MetricDashboard metric = new MetricDashboard();
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            //get the metric considering it is a leaf attribute
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_LEAF_ATTRIBUTE_BY_METRIC_ID)){
                
                ps.setInt(1, metricId);
                ResultSet resultSet = ps.executeQuery();
                
                //if there was a result, then it is because the metric is a leaf attribute
                if(resultSet.next()){
                    metric.setLeafAttribute(new LeafAttribute(
                            resultSet.getInt("metricId"),
                            new Description(
                                    resultSet.getInt("descriptionId"),
                                    resultSet.getString("descriptionName"),
                                    resultSet.getString("unit")
                            ),
                            resultSet.getInt("metricAggregationOperator"),
                            resultSet.getInt("numSamples"),
                            resultSet.getString("normalizationMethod"),
                            resultSet.getInt("normalizationKind"),
                            resultSet.getFloat("minimumThreshold"),
                            resultSet.getFloat("maximumThreshold")
                    ));
                    metric.setChildMetrics(new ArrayList());
                    
                    metric.setMetricId(resultSet.getInt("metricId"));
                    metric.setMetricName(resultSet.getString("metricName"));
                    metric.setBlockLevel(resultSet.getInt("blockLevel"));
                }
                //otherwise, it is a parent metric
                else{
                    try( PreparedStatement ps2 = conn.prepareStatement(SQL_GET_METRIC_BY_ID)){
                        ps2.setInt(1, metricId);
                        ResultSet resultSet2 = ps2.executeQuery();
                        resultSet2.next();

                        metric.setMetricId(resultSet2.getInt("metricId"));
                        metric.setMetricName(resultSet2.getString("metricName"));
                        metric.setBlockLevel(resultSet2.getInt("blockLevel"));
                    }
                } 
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the metric from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
        if(metric.getLeafAttribute() == null){
            //If it isn't a Leaf Attribute, find childs of a Metric, and then the childs of its childs and so on...
            metric.setChildMetrics(getChildMetrics(metric));
        }
        return metric;
        
    }
    
    //Function to use recursively to get child metrics of another metric - João Ribeiro <jdribeiro@student.dei.uc.pt>
    private ArrayList<MetricDashboard> getChildMetrics(MetricDashboard parent) throws SQLException {
        Connection conn;
        ArrayList<MetricDashboard> childMetrics = new ArrayList<>();
        
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_CHILD_METRICS_OF_PARENT_METRIC_BY_ID)){
                //get child metrics from a "parent" metric and save them on "childMetrics" ArrayList
                ps.setInt(1, parent.getMetricId());
                ResultSet resultSet = ps.executeQuery();
                
                while(resultSet.next()){
                    MetricDashboard metricToAdd = new MetricDashboard();
                    metricToAdd.setMetricId(resultSet.getInt("childMetric"));
                    metricToAdd.setMetricName(resultSet.getString("metricName"));
                    metricToAdd.setBlockLevel(resultSet.getInt("blockLevel"));
                    childMetrics.add(metricToAdd);
                }
            }
                    
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the metric from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
        
        //for each child metric execute this function recursively to find the childs of the parent's childs
        for(MetricDashboard m : childMetrics){
            m.setChildMetrics(getChildMetrics(m));
        }
        return childMetrics;
    }
    
    //Get Descriptions - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public ArrayList<Description> getDescriptions(String filter) throws SQLException {
        
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            ArrayList<Description> descriptions = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_DESCRIPTIONS)) {
                ps.setString(1, "%" + filter + "%");
                ps.setString(2, "%" + filter + "%");
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    descriptions.add(new Description(-1,
                            resultSet.getInt("descriptionId"),
                            resultSet.getString("dataType"),
                            resultSet.getString("descriptionName"),
                            resultSet.getString("unit")));
                }
                return descriptions;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the descriptions from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
    }
    
    //Get Quality Models - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public ArrayList<QualityModel> getQualityModels(String qualityModelsFilter, 
            String metricsFilter) throws SQLException {
        
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            ArrayList<QualityModel> qualityModels = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_QUALITY_MODELS)) {
                ps.setString(1, "%" + qualityModelsFilter + "%");
                ps.setString(2, "%" + qualityModelsFilter + "%");
                ps.setString(3, "%" + metricsFilter + "%");
                ps.setString(4, "%" + metricsFilter + "%");
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    qualityModels.add(new QualityModel(
                            resultSet.getInt("qualityModelId"),
                            new MetricDashboard(resultSet.getInt("metricId"),resultSet.getString("metricName")),
                            resultSet.getString("modelName"),
                            resultSet.getInt("modelDescriptionReference"),
                            resultSet.getFloat("businessThreshold")));
                }
                return qualityModels;
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the Quality Models from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
    }
    
    //Get Quality Models by Id - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public QualityModel getQualityModelById(int qualityModelId) throws SQLException {
        
        Connection conn;
        QualityModel qualityModel;
        int metricId;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_QUALITY_MODEL_BY_ID)) {
                ps.setInt(1,qualityModelId);
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                qualityModel = new QualityModel(
                            resultSet.getInt("qualityModelId"),
                            resultSet.getString("modelName"),
                            resultSet.getInt("modelDescriptionReference"),
                            resultSet.getFloat("businessThreshold"));
                metricId = resultSet.getInt("metricId");
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the Quality Model from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
        qualityModel.setMetric(getMetricById(metricId));
        qualityModel.setConfigurationProfiles(getQualityModelConfigurationProfiles(qualityModel.getQualityModelId()));
        return qualityModel;
    }
    
    //Get Quality Model Configuration Profiles - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public ArrayList<ConfigurationProfile> getQualityModelConfigurationProfiles(int qualityModelId) 
            throws SQLException {
        
        Connection conn;
        ArrayList<ConfigurationProfile> configurationProfiles = new ArrayList<>();
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_QUALITY_MODEL_CONFIGURATION_PROFILES)) {
                ps.setInt(1,qualityModelId);
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()){
                    configurationProfiles.add(
                            new ConfigurationProfile(
                                    resultSet.getInt("configurationProfileId"),
                                    resultSet.getString("profileName")));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the Quality Model from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
        return configurationProfiles;
    }
    
    //Get Configuration Profile and its preferences by configuration profile Id - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public ConfigurationProfile getConfigurationProfileById(int configurationProfileId) throws SQLException {
        
        Connection conn;
        ConfigurationProfile configurationProfile;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_CONFIGURATION_PROFILE_BY_ID)){
                ps.setInt(1,configurationProfileId);
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                configurationProfile = new ConfigurationProfile(configurationProfileId, resultSet.getString("profileName"));
            }
            
            ArrayList<Preference> preferences = new ArrayList();
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_PREFERENCES_BY_CONFIGURATION_PROFILE_ID)){
                ps.setInt(1,configurationProfileId);
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()){
                    preferences.add(
                            new Preference(
                                    resultSet.getInt("metricId"),
                                    resultSet.getFloat("weight"),
                                    resultSet.getFloat("threshold")
                            )
                    );
                }
                configurationProfile.setPreferences(preferences);
            }
            
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the Quality Model from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
        return configurationProfile;
    }
    
    public ArrayList<MetricDashboard> getConfigurationProfileListOfMetrics (int configurationProfileId, 
            boolean leafAttributes) throws SQLException{
        
        Connection conn;
        ArrayList<MetricDashboard> listOfMetrics = new ArrayList();
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        String sql;
        if(leafAttributes){
            sql = SQL_GET_LIST_OF_LEAF_METRICS_BY_CONFIGURATION_PROFILE_ID;
        }
        else{
            sql = SQL_GET_LIST_OF_METRICS_NOT_LEAF_BY_CONFIGURATION_PROFILE_ID;
        }
        try {
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,configurationProfileId );
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()){
                    listOfMetrics.add(
                            new MetricDashboard(
                                    resultSet.getInt("metricId"),
                                    resultSet.getString("metricName")
                            )
                    );
                }
            }   
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the list of metrics from the database using"
                    + "configuration profile id.", ex);
            return null;
        } finally {
            close(conn);
        }
        return listOfMetrics; 
    }
            
    public ArrayList<Resource> getMonitoredResources(boolean createRule) throws SQLException{
        Connection conn;
        ArrayList<Resource> resources = new ArrayList();
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        String sqlChosen;
        
        if(createRule){
            sqlChosen = SQL_GET_ACTIVE_RESOURCES_WHICH_HAVE_ACTIONS;
        }
        else{
            sqlChosen = SQL_GET_ACTIVE_RESOURCES;
        }
        
        try {
            try(PreparedStatement ps = conn.prepareStatement(sqlChosen)){
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()){
                    resources.add(
                            new Resource(
                                    -1,
                                    resultSet.getInt("resourceId"),
                                    resultSet.getString("resourceName"),
                                    resultSet.getString("resourceType"),
                                    resultSet.getString("resourceAddress")
                            )
                    );
                }
            }   
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the active Resources from the database.", ex);
            return null;
        } finally {
            close(conn);
        }
        return resources;
    }
    
    public int getConfigurationProfileIdByResourceId(int resourceId) throws SQLException{
        Connection conn;
        int configurationProfileId = -1;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return configurationProfileId;
        }
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_CONFIGURATION_PROFILE_ID_BY_RESOURCE_ID)){
                ps.setInt(1,resourceId );
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                configurationProfileId = resultSet.getInt("configurationProfileID");
            }   
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the configuration profile id from the database using"
                    + "resource id.", ex);
        } finally {
            close(conn);
        }
        return configurationProfileId;
    }
    
    public int getQualityModelIdByConfigurationProfileId(int configurationProfileId) throws SQLException{
        Connection conn;
        int qualityModelId = -1;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return qualityModelId;
        }
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_QUALITY_MODEL_ID_BY_CONFIGURATION_PROFILE_ID)){
                ps.setInt(1,configurationProfileId );
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                qualityModelId = resultSet.getInt("qualityModelId");
            }   
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the quality model id from the database using"
                    + "configuration profile id.", ex);
            return -1;
        } finally {
            close(conn);
        }
        return qualityModelId;
    }
    
    public ArrayList<DataSetPlot> getPlotData(int resourceId, int metricId, String dataType, long startDate, 
            long endDate, boolean addPlansInfo) throws SQLException{
        Connection conn;
        ArrayList<DataSetPlot> plotData = new ArrayList();
        
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        
        try {
            //always get the first dataSet which corresponds to the raw data or metric data
            DataSetPlot dataSet = new DataSetPlot();
            //if the request is to get rawData, firstly get associated description Info and then the data
            if(dataType.equals("raw")){
                int descriptionId;
                try(PreparedStatement ps = conn.prepareStatement(SQL_GET_DESCRIPTION_INFO_BY_METRIC_ID)){
                    ps.setInt(1, metricId);
                    ResultSet resultSet = ps.executeQuery();
                    resultSet.next();
                    dataSet.setDescriptionInfo(
                            resultSet.getString("descriptionName") + " (" + 
                            resultSet.getString("unit") + ")"
                    
                    );
                    descriptionId = resultSet.getInt("descriptionId");
                }
                
                try(PreparedStatement ps = 
                        conn.prepareStatement(SQL_GET_TIME_SLOT_RAW_DATA_BY_RESOURCE_ID_AND_DESCRIPTION_ID)){
                    ps.setInt(1, resourceId);
                    ps.setInt(2, descriptionId);
                    //divide millis by 1000 to get result in seconds, which is the format accepted by the database
                    //using the function FROM_UNIXTIME()
                    ps.setLong(3, startDate);
                    ps.setLong(4, endDate);
                    
                    ResultSet resultSet = ps.executeQuery();
                    ArrayList <DataSetElem> dataPoints = new ArrayList();
                    while(resultSet.next()){
                        dataPoints.add(
                                new DataSetElem(
                                        resultSet.getDouble("value"),
                                        //multiply by 1000 to get result in milliseconds and ease the conversio n
                                        //into Date in the web page
                                        resultSet.getLong("valueTime") * 1000  
                                )
                        );
                    }
                    dataSet.setListOfDataPoints(dataPoints);
                }
            }
            //if the request is to get MetricData, directly get it
            else{
                try(PreparedStatement ps = 
                        conn.prepareStatement(SQL_GET_TIME_SLOT_METRIC_DATA_BY_RESOURCE_ID_AND_BY_METRIC_ID)){
                    ps.setInt(1, resourceId);
                    ps.setInt(2, metricId);
                    ps.setLong(3, startDate);
                    ps.setLong(4, endDate);
                    ResultSet resultSet = ps.executeQuery();
                    ArrayList <DataSetElem> dataPoints = new ArrayList();
                    while(resultSet.next()){
                        dataPoints.add(
                                new DataSetElem(
                                        resultSet.getDouble("value"),
                                        resultSet.getLong("valueTime") * 1000
                                )
                        );
                    }
                    dataSet.setListOfDataPoints(dataPoints);
                }
            }
            
            //If the request has to show adaptation plans info, add a new DataSet which holds plans' info            
            if(addPlansInfo){
                try(PreparedStatement ps = 
                        conn.prepareStatement(SQL_GET_TIME_SLOT_PLANS_IDS_BY_RESOURCE_ID_AND_METRIC_ID)){
                    ps.setInt(1, resourceId);
                    ps.setInt(2, metricId);
                    ps.setLong(3, startDate);
                    ps.setLong(4, endDate);
                    ResultSet resultSet = ps.executeQuery();
                    ArrayList <PlanInfo> plansInfoPoints = new ArrayList();
                    while(resultSet.next()){
                        plansInfoPoints.add(
                                new PlanInfo(
                                        resultSet.getInt("planId"),
                                        resultSet.getLong("valueTime") * 1000
                                )
                        );
                    }
                    dataSet.setListOfPlansInfo(plansInfoPoints);
                }
            }
            plotData.add(dataSet);
            
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the data to plot from the database.", ex);
            return null;
        } finally {
            conn.commit();
            close(conn);
        }
        return plotData; 
    }
    
    public void getSimulationData(ArrayList<DataSetElem> simulationData, int resourceId, 
            long startDate, long endDate, int metricId, double traversalWeight) throws SQLException{
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return;
        }
        
        try {
            try(PreparedStatement ps = 
                    conn.prepareStatement(SQL_GET_TIME_SLOT_METRIC_DATA_BY_RESOURCE_ID_AND_BY_METRIC_ID)){
                ps.setInt(1, resourceId);
                ps.setInt(2, metricId);
                ps.setLong(3, startDate);
                ps.setLong(4, endDate);
                ResultSet resultSet = ps.executeQuery();
                int iterator = 0;
                boolean firstLeafFound = simulationData.isEmpty();
                while(resultSet.next()){
                    if(firstLeafFound){
                        simulationData.add(
                            new DataSetElem(
                                resultSet.getDouble("value") * traversalWeight,
                                resultSet.getLong("valueTime") * 1000
                            )
                        );
                    }
                    else{
                        DataSetElem elem = simulationData.get(iterator);
                        elem.setValue(  elem.getValue() + (resultSet.getDouble("value") * traversalWeight)  );
                        iterator++;
                    }
                }
                conn.commit();
            }
            
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the data from the database to simulate a plot.", ex);
            return;
        } finally {
            close(conn);
        }
    }
    
    public ArrayList<PlotConfig> getPlotsConfigs() throws SQLException{
        Connection conn;
        ArrayList<PlotConfig> plotsConfigs = new ArrayList();
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_GET_PLOTS_CONFIGS)){
                ResultSet resultSet = ps.executeQuery();
                while(resultSet.next()){
                    plotsConfigs.add(
                            new PlotConfig(
                                    resultSet.getString("plotConfigName"),
                                    resultSet.getBytes("configObject"),
                                    resultSet.getInt("plotConfigId")
                            )
                    );
                }
            }   
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading Plot configurations from the database.", ex);
            plotsConfigs = null;
        } finally {
            close(conn);
        }
        return plotsConfigs;
    }
    
    //Get Actions  - João Ribeiro <jdribeiro@student.dei.uc.pt>
    public ArrayList<ActionDashboard> getActionsDashboard(int resourceId) throws SQLException {
        
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return null;
        }
        
        PreparedStatement ps = null;
        ArrayList<ActionDashboard> actions = new ArrayList<>();
        try {
            //if the request wants all the actions available, then resourceId will be 0
            //otherwise the request wants the actions related to a resource which means resourceId > 0
            if(resourceId == 0){
                ps = conn.prepareStatement(SQL_GET_ACTIONS_AND_CONFIGURATIONS);
            }
            else{
                ps = conn.prepareStatement(SQL_GET_ACTIONS_AND_CONFIGURATIONS_BY_RESOURCE_ID);
                ps.setInt(1, resourceId);
            }
            
            ResultSet rs = ps.executeQuery();
            int lastActionId = -1;
            ActionDashboard lastAction = null;
            
            while (rs.next()) {
                if(lastActionId != rs.getInt("actionId")){
                    lastActionId = rs.getInt("actionId");
                    lastAction = new ActionDashboard(
                        rs.getInt("actionId"),
                        rs.getInt("actuatorId"),
                        rs.getInt("resourceId"),
                        rs.getString("actionName"),
                        null
                    );
                    actions.add(lastAction);
                    if(rs.getInt("configurationId") != 0){
                        lastAction.setConfigurations(new ArrayList());
                        lastAction.addConfiguration(
                                new ConfigurationDashboard(
                                        rs.getInt("configurationId"),
                                        rs.getString("keyName")
                                )
                        );
                    }
                }
                else{
                    lastAction.addConfiguration(
                            new ConfigurationDashboard(
                                    rs.getInt("configurationId"),
                                    rs.getString("keyName")
                            )
                    );
                }
            }
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Error when reading the actions from the database.", ex);
            actions = null;
        } finally {
            if(ps != null){
                ps.close();
            }
            close(conn);
        }
        return actions;
    }
    
    
    
    
    
    
    
    
    
    
    public boolean saveNewQualityModel(QualityModel qualityModel) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return false;
        }
        try {
            try (PreparedStatement ps = conn.prepareStatement(SQL_NEW_QUALITY_MODEL, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, qualityModel.getModelName());
                ps.setInt(2, qualityModel.getModelDescriptionReference());
                ps.setFloat(3, qualityModel.getBusinessThreshold());
                ps.setInt(4, qualityModel.getMetric().getMetricId());
                ps.execute();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    qualityModel.setQualityModelId(generatedKeys.getInt(1));
                } else {
                    qualityModel.setQualityModelId(INVALID_KEY);
                    return false;
                }
                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a Quality Model in the database.", e);
            qualityModel.setQualityModelId(INVALID_KEY);
            return false;
        } finally {
            close(conn);
        }

    }
    
    public boolean saveNewConfigurationProfile(ConfigurationProfile configurationProfile) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return false;
        }
        try {
            // start transaction
            
            //Firstly, create configuration profile database entry
            try( PreparedStatement ps = 
                    conn.prepareStatement(SQL_NEW_CONFIGURATION_PROFILE, Statement.RETURN_GENERATED_KEYS)){
                
                ps.setString(1, configurationProfile.getProfileName());
                ps.setInt(2, configurationProfile.getQualityModelId());
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    configurationProfile.setConfigurationProfileId(generatedKeys.getInt(1));
                } else {
                    configurationProfile.setConfigurationProfileId(INVALID_KEY);
                    return false;
                }
            }
            
            //for each of the preferences, create the corresponding database entry
            try( PreparedStatement ps = conn.prepareStatement(SQL_NEW_PREFERENCE)){
                for(Preference preference : configurationProfile.getPreferences()){
                    ps.setInt(1, configurationProfile.getConfigurationProfileId());
                    ps.setInt(2, preference.getMetricId());
                    ps.setFloat(3, preference.getWeight());
                    ps.setFloat(4, preference.getThreshold());
                    ps.executeUpdate();
                }
            }
            
            //end transaction
            conn.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a Configuration Profile in the database.", e);
            configurationProfile.setConfigurationProfileId(INVALID_KEY);
            if (conn != null) {
                try {
                  LOGGER.error("Transaction is being rolled back");
                  conn.rollback();
                } catch (SQLException excep) {
                  LOGGER.error("[ATMOSPHERE] Error when inserting a Configuration Profile in the database.", excep);
                }
            }
            return false;
            
        } finally {
            close(conn);
        }
    }
    
    public boolean saveNewMetric(MetricDashboard metric) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return false;
        }
        try {
            // start transaction
            
            //firstly create metric
            try(PreparedStatement ps = conn.prepareStatement(SQL_NEW_METRIC, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1, metric.getMetricName());
                ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    metric.setMetricId(generatedKeys.getInt(1));
                } else {
                    metric.setMetricId(INVALID_KEY);
                    return false;
                }
            }
            
            //If child metrics array is empty, then this metric is a leaf attribute
            if(metric.getChildMetrics().isEmpty()){
                try(PreparedStatement ps = conn.prepareStatement(SQL_NEW_LEAF_ATTRIBUTE)){
                    ps.setInt(1, metric.getMetricId());
                    ps.setInt(2, metric.getLeafAttribute().getDescription().getDescriptionId());
                    ps.setInt(3, metric.getLeafAttribute().getMetricAggregationOperator());
                    ps.setInt(4, metric.getLeafAttribute().getNumSamples());
                    ps.setString(5, metric.getLeafAttribute().getNormalizationMethod());
                    ps.setInt(6, metric.getLeafAttribute().getNormalizationKind());
                    ps.setFloat(7, metric.getLeafAttribute().getMinimumThreshold());
                    ps.setFloat(8, metric.getLeafAttribute().getMaximumThreshold());
                    ps.executeUpdate();
                }
            }
            //otherwise, it is a parent metric and thereby relationships with other metrics must be saved 
            //in the Composite Attribute table
            else{
                try(PreparedStatement ps = conn.prepareStatement(SQL_NEW_COMPOSITE_ATTRIBUTE)){
                    for(MetricDashboard child : metric.getChildMetrics()){
                        ps.setInt(1,metric.getMetricId());
                        ps.setInt(2,child.getMetricId());
                        ps.setInt(3,metric.getAttributeAggregationOperator());
                        ps.executeUpdate();
                    }
                }
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a Metric in the database.", e);
            metric.setMetricId(INVALID_KEY);
            return false;
        } finally {
            close(conn);
        }

    }
    
    public void saveNewPlotConfig(PlotConfig plotConfig) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            plotConfig.setPlotConfigId(INVALID_KEY);
            return;
        }
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_NEW_PLOT_CONFIG, Statement.RETURN_GENERATED_KEYS)){
                ps.setBytes(1, plotConfig.getConfigObject());
                ps.setString(2, plotConfig.getPlotConfigName());
                ps.executeUpdate();

                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    plotConfig.setPlotConfigId(generatedKeys.getInt(1));
                } else {
                    plotConfig.setPlotConfigId(INVALID_KEY);
                }
            }
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a plot configuration in the database.", e);
            plotConfig.setPlotConfigId(INVALID_KEY);
        } finally {
            close(conn);
        }

    }
    
    
    
    public boolean updatePlotConfig(PlotConfig plotConfig) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return false;
        }
        boolean successful = true;
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_UPDATE_PLOT_CONFIG_BY_ID)){
                ps.setBytes(1, plotConfig.getConfigObject());
                ps.setString(2, plotConfig.getPlotConfigName());
                ps.setInt(3, plotConfig.getPlotConfigId());
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when updating a plot configuration in the database.", e);
            successful = false;
        } finally {
            close(conn);
        }
        return successful;
    }
    
    
    
    public int isQualityModelNameRepeated(String qualityModelName) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_QUALITY_MODEL_NAME)) {
                ps.setString(1, qualityModelName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the if quality model name is repeated.", sqlex);
            return -1;
        } finally {
            close(conn);
        }
    }
    
    public int isConfigurationProfileNameRepeatedForQualityModel(String configurationProfileName, int qualityModelId) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_CONFIGURATION_PROFILE_NAME_FOR_QUALITY_MODEL_ID)) {
                ps.setString(1, configurationProfileName);
                ps.setInt(2, qualityModelId);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the if configuration profile name is repeated for given quality model.", sqlex);
            return -1;
        } finally {
            close(conn);
        }
    }
    
    public int isMetricNameRepeated(String metricName) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return -1;
        }
        try {

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_METRIC_NAME)) {
                ps.setString(1, metricName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking if metric name is repeated.", sqlex);
            return -1;
        } finally {
            close(conn);
        }
    }
    
    public int isMetricAlreadyExisting(ArrayList<MetricDashboard> childMetrics) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return -1;
        }

        /*========================= BASE SQL (SQL_BASE_GET_NUMBER_OF_CHILD_METRICS) =======================
            "SELECT parentMetric FROM CompositeAttribute ca WHERE (ca.childMetric = ?"          
        */
        
        String sql_extension = " OR ca.childMetric = ?";
        String sql_statement = SQL_BASE_GET_NUMBER_OF_CHILD_METRICS;
        
        for(int i = 1; i < childMetrics.size(); i++){
            sql_statement += sql_extension;
        }
        
        sql_statement += ") GROUP BY parentMetric HAVING count(*) = ?" ;
        
        try {
            try(PreparedStatement ps = conn.prepareStatement(sql_statement)){
                int c = 1;
                for(MetricDashboard child: childMetrics){
                    ps.setInt(c++, child.getMetricId());
                }
                ps.setInt(c,childMetrics.size());

                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    return 1;
                } else {
                    return 0;
                }
            }
           
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking if exact set of metrics childs is repeated.", sqlex);
            return -1;
        } finally {
            close(conn);
        }
    }
    
    public int isPlotConfigNameRepeated(String plotConfigName) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return -1;
        }
        int found = 0;
        try {

            try (PreparedStatement ps = conn.prepareStatement(SQL_GET_PLOT_CONFIG_NAME)) {
                ps.setString(1, plotConfigName);
                ResultSet resultSet = ps.executeQuery();
                if (resultSet.next()) {
                    found = 1;
                }
            }
        } catch (SQLException sqlex) {
            LOGGER.error("[ATMOSPHERE] Error when checking the if plot configuration name is repeated.", sqlex);
            found = -1;
        } finally {
            close(conn);
        }
        return found;
    }
    
    
    
    public boolean deletePlotConfig(int plotConfigId) {
        Connection conn;
        try {
            conn = DatabaseManager.getConnectionFromPool();
        } catch (SQLException ex) {
            LOGGER.error("[ATMOSPHERE] Couldn't get database connection from pool", ex);
            return false;
        }
        boolean successful = true;
        try {
            try(PreparedStatement ps = conn.prepareStatement(SQL_DELETE_PLOT_CONFIG_BY_ID)){
                ps.setInt(1, plotConfigId);
                ps.executeUpdate();
            }
            conn.commit();
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when deleting a plot configuration in the database.", e);
            successful = false;
        } finally {
            close(conn);
        }
        return successful;
    }
    
}

