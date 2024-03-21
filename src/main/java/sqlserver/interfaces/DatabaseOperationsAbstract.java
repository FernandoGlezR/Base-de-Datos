package sqlserver.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class abstract DatabaseOperationsAbstract.
 */
public abstract class DatabaseOperationsAbstract implements OperationInterface {
  protected Connection connection;
  protected PreparedStatement preparedStatement;

  /**
   * Construct main for a connection.
   *
   * @param connection of database.
   */
  public DatabaseOperationsAbstract(Connection connection) {
    this.connection = connection;
  }

  /**
   * Closed prepared statement one to one.
   */
  public void closedStatement() {
    if (connection != null) {
      try {
        preparedStatement.close();
      } catch (SQLException ex) {
        System.out.println("Error closing statement: " + ex.getMessage());
      }
    }
  }
}