package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class DeleteTest {

  /**
   * Tests the deletion of the whole table "Player".
   * 
   * @author lengist
   */
  @Test
  void testDeleteTable() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", null);
    FillDatabase.deleteTable("Player");
    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals(false, rs.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cdb.disconnect();
  }

  /**
   * Tests the deletion of a certain player in the table Player. Test for Use Case 1 delete.
   * 
   * @author lengist
   */
  @Test
  void testDeletePlayer() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", null);
    FillDatabase.deletePlayer("Laura");

    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals(false, rs.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cdb.disconnect();
  }
}
