package sqlserver.connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class settings for connection to SQL Server.
 */
public class ConnectionSqlServer {
  private static final String connectionString = "jdbc:sqlserver://localhost:1433;"
      + "databaseName=hospital;user=sa;password=12345678;encrypt=true;"
      + "trustServerCertificate=true;";
  private static int counterInstance = 0;
  private static Connection connection = null;

  private ConnectionSqlServer() {

  }

  /**
   * Connection database with one instance.
   *
   * @return connection.
   */
  public static Connection getConnection() {
    if (counterInstance < 1) {
      if (connection == null) {
        try {
          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
          connection = DriverManager.getConnection(connectionString);
          System.out.println("Successful connection!");
        } catch (ClassNotFoundException | SQLException ex) {
          System.out.println("Error connection: " + ex.getMessage());
        }
      }
      counterInstance++;
      return connection;
    }
    throw new IllegalArgumentException("number of connections exceeded");
  }

  /**
   * Close connection to database.
   */
  public static void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
        System.out.println("Closed connection!");
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
    }
  }
}