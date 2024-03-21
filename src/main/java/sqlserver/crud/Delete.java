package sqlserver.crud;

import java.sql.Connection;
import java.sql.SQLException;
import sqlserver.interfaces.DatabaseOperationsAbstract;

/**
 * Class Delete with query.
 */
public class Delete extends DatabaseOperationsAbstract {
  private String tableDataBase;
  private String deleteConditional;

  /**
   * Method Delete.
   *
   * @param connection        connector.
   * @param nameTable         name table of database.
   * @param deleteConditional conditional for deleted.
   */
  public Delete(Connection connection, String nameTable, String deleteConditional) {
    super(connection);
    this.tableDataBase = nameTable;
    this.deleteConditional = deleteConditional;
  }

  @Override
  public void execute() {
    String query = "DELETE FROM " + tableDataBase + " WHERE " + deleteConditional;
    try {
      preparedStatement = connection.prepareStatement(query);
      preparedStatement.executeUpdate();
      System.out.println("Delete executed successfully.");
    } catch (SQLException ex) {
      System.out.println("Error delete operation: " + ex.getMessage());
    } finally {
      closedStatement();
    }
  }
}