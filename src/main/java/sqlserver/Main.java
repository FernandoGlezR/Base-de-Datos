package sqlserver;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import sqlserver.connector.ConnectionSqlServer;
import sqlserver.crud.Create;
import sqlserver.crud.Delete;
import sqlserver.crud.Read;
import sqlserver.crud.Update;
import sqlserver.process.StoredProcedures;

/**
 * Class Main.
 */
public class Main {

  /**
   * Method main for query's.
   *
   * @param args arrays of character.
   */
  public static void main(String[] args) {
    Connection connection = ConnectionSqlServer.getConnection();

    Map<String, Object> valueColumns = new HashMap<>();
    valueColumns.put("motivo", "chequeo mensual");
    valueColumns.put("estado", "pendiente");
    valueColumns.put("id_medico", 1);
    valueColumns.put("id_paciente", 2);
    Create create = new Create(connection, "citas", valueColumns);
    create.execute();

    Read read = new Read(connection, "citas");
    read.execute();

    Delete delete = new Delete(connection, "citas", "id_cita=8");
    delete.execute();

    Update update = new Update(connection, "citas", "estado='completo'", "id_cita=4");
    update.execute();

    StoredProcedures storedProcedures = new StoredProcedures(connection, 3);
    storedProcedures.execute();

    ConnectionSqlServer.closeConnection();
  }
}