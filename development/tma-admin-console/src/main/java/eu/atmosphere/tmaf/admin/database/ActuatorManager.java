package eu.atmosphere.tmaf.admin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Statement;

public class ActuatorManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActuatorManager.class);

    public int saveNewActuator(String address, String pubKeyPath) {
        String sql = "INSERT INTO Actuator(address, pubKey) VALUES (?, ?)";
        PreparedStatement ps = null;
        byte[] pubKey = getBytesPublicKey(pubKeyPath);

        try {
            ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address);
            ps.setBytes(2, pubKey);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.execute(ps);
    }

    private byte[] getBytesPublicKey(String pubKeyPath) {
        InputStream input;
        byte[] pkBytes = null;
        try {
            input = new FileInputStream(pubKeyPath);
            pkBytes = IOUtils.toByteArray(input);
        } catch (FileNotFoundException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return pkBytes;
    }
}