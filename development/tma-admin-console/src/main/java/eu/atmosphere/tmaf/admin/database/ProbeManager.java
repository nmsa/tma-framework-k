package eu.atmosphere.tmaf.admin.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

public class ProbeManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProbeManager.class);

    public int saveNewProbe(String probeName) {
        String sql = "INSERT INTO Probe(probeName, salt) VALUES (?, ?)";
        PreparedStatement ps;

        try {
            ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, probeName);
            ps.setString(2, "n/a");

            DatabaseManager databaseManager = new DatabaseManager();
            return databaseManager.execute(ps);
        } catch (SQLException e) {
            LOGGER.error("[ATMOSPHERE] Error when inserting a probe in the database.", e);
        }
        return -1;
    }
}
