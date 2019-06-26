package eubr.atmosphere.tma.planning.databaseControler;
				
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class DatabaseControler {
		public DatabaseControler(){}
		

		public void firstDatabaseChange(String [] observation){
				try{
						// create a mysql database connection
						String myDriver = "knowledge";
						String myUrl = "jdbc:mysql://localhost/test";
						Class.forName(myDriver);
						Connection conn = DriverManager.getConnection(myUrl, "root", "passtobereplaced");

						// the mysql insert statement
						String query = "INSERT INTO Data(probeId, resourceId, descriptionId, valueTime, value) "
						+ " VALUES (? , ? , ?, ?, ?)";

						// create the mysql insert preparedstatement
						PreparedStatement preparedState = conn.prepareStatement(query);

						preparedState.setInt(1, Integer.parseInt(observation[0]));//probeId
						preparedState.setInt(2, Integer.parseInt(observation[1]));//resourceId
						preparedState.setInt(3, Integer.parseInt(observation[2]));//descriptionId
						preparedState.setDouble(4, Double.parseDouble(observation[3]));//time
						preparedState.setDouble(5, Double.parseDouble(observation[4]));//value

						// execute the preparedstatement
						preparedState.execute();

						conn.close();
				}
				//error handler
				catch (Exception e)
				{
						System.err.println("Got an exception!");
						System.err.println(e.getMessage());
				}
		}
}