package pt.uc.dei.eubr.atmosphere.tma.admin.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ActuatorManager {

    public int saveNewActuator(String address, String pubKey) {
        String sql = "INSERT INTO Actuator(address, pubKey) VALUES (?, ?)";
        PreparedStatement ps = null;

        try {
            ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, address);
            ps.setString(2, pubKey);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseManager databaseManager = new DatabaseManager();
        return databaseManager.execute(ps);
    }
}