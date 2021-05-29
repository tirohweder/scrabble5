package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/**
 * In this class the creation of a new player in the database table, the insertion of a letter with
 * corresponding points and occurrence and the initial filling of the table letter with all letters
 * from the alphabet and the initial points and is tested. The other methods from class
 * CreateDatabase like removeTable() are tested in DeleteTest.java. Note: In the methods with access
 * to the database the connection gets established and disconnect individually for each method.
 * Because of that every test method needs to do so too.
 * 
 * @author lengist
 */
class CreateTest {

  CreateDatabase cd = new CreateDatabase();
  /**
   * Tests the function to create a new Player in the database. Test for Use Case 1 create.
   * 
   * @author lengist
   */
  @Test
  void testCreatePlayer() {
    cd.createTest();
    FillDatabase.createPlayer("Laura");
    Database.reconnect();


    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals("Laura", rs.getString("Name"));
      assertEquals(0, rs.getInt("TotalPoints"));
      assertEquals(0, rs.getInt("PersonalHighscore"));
      assertEquals(0, rs.getInt("LaidWords"));
      assertEquals(0, rs.getInt("PointsPerWordRate"));
      assertEquals(0, rs.getInt("LongestWord"));
      assertEquals(0, rs.getInt("TotalPlayedGames"));
      assertEquals(0, rs.getInt("TotalWins"));
      assertEquals(0.0, rs.getDouble("WinRate"));
      assertEquals("", rs.getString("FaveDic"));
      assertEquals(50.0, rs.getDouble("Music"));
      assertEquals(25.0, rs.getDouble("SoundEffect"));
      System.out.println("done");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cd.deleteDatabaseFile();
  }

  /**
   * Tests the function to insert a letter and the corresponding points and the occurrence into the
   * table Letters.
   * 
   * @author lengist
   */
  @Test
  void testInsertLetters() {
    cd.createTest();
    FillDatabase.insertLetters("L", 4, 5);
    Database.reconnect();
    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      assertEquals("L", rs.getString("Letter"));
      assertEquals(4, rs.getInt("Points"));
      assertEquals(5, rs.getInt("Occurrence"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cd.deleteDatabaseFile();
  }

  /**
   * Tests the initial filling of the table Letters.
   * 
   * @author lengist
   */
  @Test
  void testFillLetters() {
    cd.createTest();
    FillDatabase.fillLetters();
    Database.reconnect();
    Statement stm;
    int i = 0;
    String[] letter = new String[27];
    int[] point = new int[27];
    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      while (rs.next()) {
        letter[i] = rs.getString(1);
        point[i] = rs.getInt(2);
        i++;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assertEquals("A", letter[0]);
    assertEquals(1, point[0]);
    assertEquals("B", letter[1]);
    assertEquals(3, point[1]);
    assertEquals("C", letter[2]);
    assertEquals(3, point[2]);
    assertEquals("D", letter[3]);
    assertEquals(2, point[3]);
    assertEquals("E", letter[4]);
    assertEquals(1, point[4]);
    assertEquals("F", letter[5]);
    assertEquals(4, point[5]);
    assertEquals("G", letter[6]);
    assertEquals(2, point[6]);
    assertEquals("H", letter[7]);
    assertEquals(4, point[7]);
    assertEquals("I", letter[8]);
    assertEquals(1, point[8]);
    assertEquals("J", letter[9]);
    assertEquals(8, point[9]);
    assertEquals("K", letter[10]);
    assertEquals(5, point[10]);
    assertEquals("L", letter[11]);
    assertEquals(1, point[11]);
    assertEquals("M", letter[12]);
    assertEquals(3, point[12]);
    assertEquals("N", letter[13]);
    assertEquals(1, point[13]);
    assertEquals("O", letter[14]);
    assertEquals(1, point[14]);
    assertEquals("P", letter[15]);
    assertEquals(3, point[15]);
    assertEquals("Q", letter[16]);
    assertEquals(10, point[16]);
    assertEquals("R", letter[17]);
    assertEquals(1, point[17]);
    assertEquals("S", letter[18]);
    assertEquals(1, point[18]);
    assertEquals("T", letter[19]);
    assertEquals(1, point[19]);
    assertEquals("U", letter[20]);
    assertEquals(1, point[20]);
    assertEquals("V", letter[21]);
    assertEquals(4, point[21]);
    assertEquals("W", letter[22]);
    assertEquals(4, point[22]);
    assertEquals("X", letter[23]);
    assertEquals(8, point[23]);
    assertEquals("Y", letter[24]);
    assertEquals(4, point[24]);
    assertEquals("Z", letter[25]);
    assertEquals(10, point[25]);
    cd.deleteDatabaseFile();
  }

}
