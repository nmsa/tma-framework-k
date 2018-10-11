package pt.uc.dei.eubr.atmosphere.tma.admin.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;

import com.mysql.jdbc.Statement;

public class ActuatorManager {

    public int saveNewActuator(String address, String pubKeyPath) {
        String sql = "INSERT INTO Actuator(address, pubKey) VALUES (?, ?)";
        PreparedStatement ps = null;
        byte[] pubKey = getBytesPublicKey(pubKeyPath);

        try {
            ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address);
            ps.setBytes(2, pubKey);
        } catch (SQLException e) {
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pkBytes;
    }
}