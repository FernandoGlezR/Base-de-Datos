package sqlserver.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.StringJoiner;
import sqlserver.interfaces.DatabaseOperationsAbstract;

/**
 * Class Create.
 */
public class Create extends DatabaseOperationsAbstract {
  private String tableDataBase;
  private Map<String, Object> entryColumnData;

  /**
   * Method main Create.
   *
   * @param connection      connector.
   * @param nameTable   parameter of name tabla in DB.
   * @param entryColumnData parameter of columns and values to insert.
   */
  public Create(Connection connection, String nameTable, Map<String, Object> entryColumnData) {
    super(connection);
    this.tableDataBase = nameTable;
    this.entryColumnData = entryColumnData;
  }

  @Override
  public void execute() {
    if (tableDataBase.isEmpty() || entryColumnData.isEmpty()) {
      throw new IllegalArgumentException("Table name and columns cannot  be empty.");
    }

    StringJoiner columnNames = new StringJoiner(", ", "(", ")");
    StringJoiner valuesPlaceholder = new StringJoiner(", ", "(", ")");
    entryColumnData.keySet().forEach(key -> {
      columnNames.add(key);
      valuesPlaceholder.add("?");
    });

    String query = "INSERT INTO " + tableDataBase + " "
        + columnNames + " VALUES" + valuesPlaceholder;

    try {
      preparedStatement = connection.prepareStatement(query);
      int index = 1;
      for (Object value : entryColumnData.values()) {
        preparedStatement.setObject(index++, value);
      }
      preparedStatement.executeUpdate();
      System.out.println("Create executed successfully.");
    } catch (SQLException ex) {
      System.out.println("Error create operation: " + ex.getMessage());
    } finally {
      closedStatement();
    }
  }
}