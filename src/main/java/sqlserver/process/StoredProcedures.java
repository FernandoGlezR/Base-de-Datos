package sqlserver.process;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import sqlserver.interfaces.DatabaseOperationsAbstract;


/**
 * Class storedProcedures.
 */
public class StoredProcedures extends DatabaseOperationsAbstract {
  private int idPacient;

  public StoredProcedures(Connection connection, int idPacient) {
    super(connection);
    this.idPacient = idPacient;
  }

  @Override
  public void execute() {
    try {
      String query = "{call cantidadRecetas (?,?)}";
      CallableStatement callableStatement = connection.prepareCall(query);
      callableStatement.setInt(1, idPacient);
      callableStatement.registerOutParameter(2, Types.INTEGER);
      callableStatement.execute();
      int quantity = callableStatement.getInt(2);
      System.out.println("Patient with id: " + idPacient + " have " + quantity + " recipes");
      callableStatement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}