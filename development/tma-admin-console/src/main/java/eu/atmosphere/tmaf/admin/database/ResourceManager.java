package eu.atmosphere.tmaf.admin.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

public class ResourceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceManager.class);

    public int saveNewResource(String name, String type, String address) {
        String sql = "INSERT INTO "
                + "Resource(resourceName, resourceType, resourceAddress) "
                + "VALUES "
                + "(?, ?, ?)";

        PreparedStatement ps;

        try {
            ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setString(3, address);

            DatabaseManager databaseManager = new DatabaseManager();
            return databaseManager.execute(ps);
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a resource in the database.", e);
        }

        return -1;
    }
}