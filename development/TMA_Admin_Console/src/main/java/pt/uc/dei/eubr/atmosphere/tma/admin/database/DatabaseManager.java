package pt.uc.dei.eubr.atmosphere.tma.admin.database;

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
    
    public static Connection getConnectionInstance() {
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
    
    public static ResultSet executeQuery(String sql) {
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

    public void close() {
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

	public Long executeQuery(PreparedStatement ps) {
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
}