package eu.atmosphere.tmaf.admin.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class ResourceManager {
	
	public int saveNewResource(String name, String type, String address) {
		String sql = "INSERT INTO "
				+ "Resource(resourceName, resourceType, resourceAddress) "
				+ "VALUES "
				+ "(?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = DatabaseManager.getConnectionInstance().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DatabaseManager databaseManager = new DatabaseManager();
		return databaseManager.execute(ps);
	}
}