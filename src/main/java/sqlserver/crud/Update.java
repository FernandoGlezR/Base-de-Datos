package sqlserver.crud;

import java.sql.Connection;
import java.sql.SQLException;
import sqlserver.interfaces.DatabaseOperationsAbstract;

/**
 * Class Update.
 */
public class Update extends DatabaseOperationsAbstract {
  private String tableDataBase;
  private String newValue;
  private String conditional;

  /**
   * Method main for class Update.
   *
   * @param connection connector.
   * @param nameTable name of table database.
   * @param newValue new value column more value;
   * @param conditional for search the parameter required.
   */
  public Update(Connection connection, String nameTable, String newValue, String conditional) {
    super(connection);
    this.tableDataBase = nameTable;
    this.newValue = newValue;
    this.conditional = conditional;
  }

  @Override
  public void execute() {
    String query = "UPDATE " + tableDataBase + " SET " + newValue
        + " WHERE " + conditional;
    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();
      System.out.println("Update executed successfully");
    } catch (SQLException ex) {
      System.out.println("Error update operation: " + ex.getMessage());
    } finally {
      closedStatement();
    }
  }
}