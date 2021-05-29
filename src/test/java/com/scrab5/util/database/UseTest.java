package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.scrab5.network.Server;
import com.scrab5.network.ServerStatistics;
import com.scrab5.network.ServerStatistics.ClientStatistic;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * In this class the required functions that need to be tested separately 
 * from FillDatabase.java and need to return important information for other functions are tested.
 * Note: In the methods with access to the database to connection gets established and disconnect 
 * individually for each method. Because of that every test method needs to do so too.
 * 
 * @author lengist
 */
@Disabled
class UseTest {
  CreateDatabase cd = new CreateDatabase();

  /**
   * Tests the function to return all the letters with their corresponding points that are saved in
   * the table Server in the database.
   *
   * @author lengist
   */
  @Test
  void testViewLetters() {
    cd.createTest();
    FillDatabase.insertLetters("A", 4, 5);
    FillDatabase.insertLetters("B", 1, 4);
    FillDatabase.insertLetters("C", 2, 10);
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
    Database.disconnect();
    try {
      assert rs != null;
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the function to check if the table Player is empty.
   *
   * @author lengist
   */
  @Test
  void testTablePlayerIsEmpty() {
    cd.createTest();
    assertEquals(true, UseDatabase.tablePlayerIsEmpty());
    Database.disconnect();
  }

  /**
   * Tests the function to return all players from the table Player in a ResultSet. Test for Use
   * Case 1 read.
   *
   * @author lengist
   */
  @Test
  void testGetAllPlayerRs() {
    cd.createTest();
    FillDatabase.createPlayer("Alpha");
    FillDatabase.createPlayer("Beta");

    ResultSet rs = UseDatabase.getAllPlayerRs();
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
    Database.disconnect();
    try {
      assert rs != null;
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the function to return all letters stored in an array.
   *
   * @author lengist
   */
  @Test
  void testGetAllLetter() {
    cd.createTest();
    FillDatabase.fillLetters();

    String[] letter = UseDatabase.getAllLetters();
    assertEquals("A", letter[0]);
    assertEquals("E", letter[4]);
    Database.disconnect();
  }

  /**
   * Tests the function to return all points stored in an array.
   *
   * @author lengist
   */
  @Test
  void testGetAllPointsPerLetter() {
    cd.createTest();
    FillDatabase.fillLetters();

    int[] point = UseDatabase.getAllPointsPerLetter();
    assertEquals(1, point[0]);
    assertEquals(1, point[4]);
    Database.disconnect();
  }

  /**
   * Tests the function to return all occurrences stored in an array.
   *
   * @author lengist
   */
  @Test
  void testGetAllOccurrences() {
    cd.createTest();
    FillDatabase.fillLetters();

    int[] occurrence = UseDatabase.getAllOccurrences();
    assertEquals(9, occurrence[0]);
    assertEquals(12, occurrence[4]);
    Database.disconnect();
  }

  /**
   * Tests the return of the statistics for a hosted lobby.
   *
   * @author lengist
   */
  @Test
  void testGetServerStatistics() {
    cd.createTest();
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
    assertEquals("12345", cs.getIpAddress());
    Database.disconnect();
  }

  /**
   * Tests if a player with a given name exists, to make sure that not two players with the same
   * name are saved in the local database.
   *
   * @author lengist
   */
  @Test
  void testPlayerExists() {
    cd.createTest();
    FillDatabase.createPlayer("Laura");
    assertEquals(true, UseDatabase.playerExists("Laura"));
    assertEquals(false, UseDatabase.playerExists("Peter"));
    Database.disconnect();
  }
}
