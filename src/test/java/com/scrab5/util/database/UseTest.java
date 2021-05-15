package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.network.Server;
import com.scrab5.network.ServerStatistics;
import com.scrab5.network.ServerStatistics.ClientStatistic;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
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
    //CreateDatabase cdb = new CreateDatabase();
    //FillDatabase.insertLetters("A", 4, 5);
    //FillDatabase.insertLetters("B", 1, 4);
    //FillDatabase.insertLetters("C", 2, 10);
    ResultSet rs = UseDatabase.viewLetters();
    int i = 0;
    String[] letter = new String[3];
    int[] point = new int[3];
    int[] occurrence = new int[3];
    try {
      while (rs.next()) {
        letter[i] = rs.getString(1);
        point[i] = rs.getInt(2);
        occurrence[i] = rs.getInt(3);
        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assertEquals("A", letter[0]);
    assertEquals(4, point[0]);
    assertEquals(5, occurrence[0]);
    assertEquals("B", letter[1]);
    assertEquals(1, point[1]);
    assertEquals(4, occurrence[1]);
    assertEquals("C", letter[2]);
    assertEquals(2, point[2]);
    assertEquals(10, occurrence[2]);
    //cdb.disconnect();
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
   * Tests the function to return all players from the table Player in a ResultSet. Test for Use
   * Case 1 read.
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
   * Tests the function to return all letters stored in an array.
   *
   * @author lengist
   */
  @Test
  void testGetAllLetter() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.fillLetters();

    String[] letter = UseDatabase.getAllLetters();
    assertEquals("A", letter[0]);
    assertEquals("E", letter[4]);
    cdb.disconnect();
  }

  /**
   * Tests the function to return all points stored in an array.
   *
   * @author lengist
   */
  @Test
  void testGetAllPointsPerLetter() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.fillLetters();

    int[] point = UseDatabase.getAllPointsPerLetter();
    assertEquals(1, point[0]);
    assertEquals(1, point[4]);
    cdb.disconnect();
  }

  /**
   * Tests the function to return all occurrences stored in an array.
   *
   * @author lengist
   */
  @Test
  void testGetAllOccurrences() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.fillLetters();

    int[] occurrence = UseDatabase.getAllOccurrences();
    assertEquals(9, occurrence[0]);
    assertEquals(12, occurrence[4]);
    cdb.disconnect();
  }

  /**
   * Tests the return of the statistics for a hosted lobby.
   *
   * @author lengist
   */
  @Test
  void testGetServerStatistics() {
    CreateDatabase cdb = new CreateDatabase();
    Server server = new Server("Laura", 4, true);
    FillDatabase.createServerRow(server.getHost(), "client", "12345");
    ServerStatistics ss = UseDatabase.getServerStatistics(server.getHost());
    LinkedHashMap<String, ClientStatistic> list = ss.getServerStatistics();
    String client = "";
    ClientStatistic cs = null;
    for (Entry<String, ClientStatistic> l : list.entrySet()) {
      client = l.getKey();
      cs = l.getValue();
    }
    assertEquals(false, list.isEmpty());
    assertEquals("Laura", server.getHost());
    assertEquals("client", client);
    assertEquals("12345", cs.getIPAddress());
    cdb.disconnect();
  }

  /**
   * Tests if a player with a given name exists, to make sure that not two players with the same
   * name are saved in the local database.
   *
   * @author lengist
   */
  @Test
  void testPlayerExists() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", null);
    assertEquals(true, UseDatabase.playerExists("Laura"));
    assertEquals(false, UseDatabase.playerExists("Peter"));
    cdb.disconnect();
  }
}
