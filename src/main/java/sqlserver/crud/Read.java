package sqlserver.crud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import sqlserver.interfaces.DatabaseOperationsAbstract;

/**
 * Class Read querys.
 */
public class Read extends DatabaseOperationsAbstract {
  private String tableDataBase;

  /**
   * Method main of class Read.
   *
   * @param connection connector to database.
   * @param nameTable parameter name of table to read.
   */
  public Read(Connection connection, String nameTable) {
    super(connection);
    this.tableDataBase = nameTable;
  }

  @Override
  public void execute() {
    String query = "SELECT * FROM " + tableDataBase;
    try {
      preparedStatement = connection.prepareStatement(query);
      ResultSet resultSet = preparedStatement.executeQuery();
      System.out.println("Read execute successfully.");
      while (resultSet.next()) {
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
          System.out.print(resultSet.getObject(i) + "\t");
        }
        System.out.println();
      }
    } catch (SQLException ex) {
      System.out.println("Error read operation: " + ex.getMessage());
    } finally {
      closedStatement();
    }
  }
}