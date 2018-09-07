package pt.uc.dei.eubr.atmosphere.tma.admin.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResourceManager {
	
	public Long saveNewResource(String name, String type, String address) {
		String sql = "INSERT INTO "
				+ "Resource(resourceName, resourceType, resourceAddress) "
				+ "VALUES "
				+ "(?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = DatabaseManager.getConnectionInstance().prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DatabaseManager databaseManager = new DatabaseManager();
		return databaseManager.executeQuery(ps);
	}
}