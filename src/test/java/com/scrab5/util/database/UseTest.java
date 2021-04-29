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
    assertEquals(letter[0], "A");
    assertEquals(point[0], 4);
    assertEquals(letter[1], "B");
    assertEquals(point[1], 1);
    assertEquals(letter[2], "C");
    assertEquals(point[2], 2);
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
    assertEquals(UseDatabase.tablePlayerIsEmpty(), true);
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
    assertEquals(name[0], "Alpha");
    assertEquals(name[1], "Beta");
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
    assertEquals(UseDatabase.playerExists("Laura"), true);
    cdb.disconnect();
  }

  /*
   * @Test void testServerExists() { fail("Not yet implemented"); }
   */


}
