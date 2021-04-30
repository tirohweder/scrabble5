package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

class UseTest {

  /**
   * Tests the function to return all the letters with their corresponding points that are saved in
   * the table Server in the database.
   * 
   * @author lengist
   */
  @Test
  void testViewLetters() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.insertLetters("A", 4);
    FillDatabase.insertLetters("B", 1);
    FillDatabase.insertLetters("C", 2);
    ResultSet rs = UseDatabase.viewLetters();
    int i = 0;
    String[] letter = new String[3];
    int[] point = new int[3];
    try {
      while (rs.next()) {
        letter[i] = rs.getString(1);
        point[i] = rs.getInt(2);
        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assertEquals("A", letter[0]);
    assertEquals(4, point[0]);
    assertEquals("B", letter[1]);
    assertEquals(1, point[1]);
    assertEquals("C", letter[2]);
    assertEquals(2, point[2]);
    cdb.disconnect();
  }

  /**
   * Tests the function to check if the table Player is empty.
   * 
   * @author lengist
   */
  @Test
  void testTablePlayerIsEmpty() {
    CreateDatabase cdb = new CreateDatabase();
    assertEquals(true, UseDatabase.tablePlayerIsEmpty());
    cdb.disconnect();
  }

  /**
   * Tests the function to return all players from the table Player in a ResultSet.
   * 
   * @author lengist
   */
  @Test
  void testGetAllPlayerRS() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Alpha", null);
    FillDatabase.createPlayer("Beta", null);

    ResultSet rs = UseDatabase.getAllPlayerRS();
    String[] name = new String[2];
    int i = 0;
    try {
      while (rs.next()) {
        name[i] = rs.getString(1);
        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assertEquals("Alpha", name[0]);
    assertEquals("Beta", name[1]);
    cdb.disconnect();
  }

  /**
   * Tests the function to return all players from the table Player in a Observable List.
   * 
   * @author lengist
   */
  /*
   * @Test void testGetAllPlayer() { fail("Not yet implemented"); FillDatabase.createPlayer("Gamma",
   * null); FillDatabase.createPlayer("Delta", null); ObservableList<String> list =
   * UseDatabase.getAllPlayer(); }
   */

  @Test
  void testPlayerExists() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", null);
    assertEquals(true, UseDatabase.playerExists("Laura"));
    assertEquals(false, UseDatabase.playerExists("Peter"));
    cdb.disconnect();
  }

  /*
   * @Test void testServerExists() { fail("Not yet implemented"); }
   */


}
