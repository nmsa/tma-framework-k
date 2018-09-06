package pt.uc.dei.eubr.atmosphere.tma.admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    private static Connection getConnectionInstanceMySQL() {
        // This will load the MySQL driver, each DB has its own driver
        try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        // Setup the connection with the DB
        try {
			connection = DriverManager
			        .getConnection("jdbc:mysql://localhost/feedback?"
			                + "user=sqluser&password=sqluserpw");
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return connection;
    }
    
    private static Connection getConnectionInstance() {
        // SQLite connection string
        String url = "jdbc:sqlite:/Users/josealexandredabruzzopereira/"
        		+ "Projects/tma-framework-k/development/TMA-Admin/sqlite/db/"
        		+ "adminDatabase";

        try {
            if ((connection == null) || connection.isClosed()) {
            	connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    static void executeStatement(String sql) {
        Connection conn = getConnectionInstance();
        try {
            Statement statement = conn.createStatement();
            statement.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static ResultSet executeQuery(String sql) {
        Connection conn = getConnectionInstance();
        Statement stmt;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {

        }
    }

	public Long saveNewResource(String name, String type, String address) {
		String sql = "INSERT INTO "
				+ "Resource(resourceName, resourceType, resourceAddress) "
				+ "VALUES "
				+ "(?, ?, ?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ps.setString(2, type);
			ps.setString(3, address);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return executeQuery(ps);
	}

	private Long executeQuery(PreparedStatement ps) {
		Long key = (long) -1;
		try {
			ps.executeQuery();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				key = generatedKeys.getLong(1);
				System.out.println(key);
			}
		} catch (SQLException e) {
			e.printStackTrace();
	    }
		return key;
	}

	public Long saveNewActuator(String address, String pubKey) {
		String sql = "INSERT INTO Actuator(address, pubKey) VALUES (?, ?)";
		PreparedStatement ps = null;
		
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, address);
			ps.setString(2, pubKey);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return executeQuery(ps);
	}
}
