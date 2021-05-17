package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/**
 * This class tests the deletion of either the content of a whole table or just the deletion of a single entry. 
 * Concerning CreateDatabase.java and Use Case 1.
 * Note: In the methods with access to the database to connection gets established and disconnect individually for each method. Because of that every test method needs to do so too.
 * 
 * @author lengist
 */
class DeleteTest {

  /**
   * Tests the deletion of the whole table "Player".
   * 
   * @author lengist
   */
  @Test
  void testDeleteTable() {
    Database.reconnect();
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", null);
    FillDatabase.deleteTable("Player");
    Database.reconnect();
    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals(false, rs.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }

  /**
   * Tests the deletion of a certain player in the table Player. Test for Use Case 1 delete.
   * 
   * @author lengist
   */
  @Test
  void testDeletePlayer() {
    Database.reconnect();
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", null);
    FillDatabase.deletePlayer("Laura");
    Database.reconnect();

    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals(false, rs.next());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }
}
