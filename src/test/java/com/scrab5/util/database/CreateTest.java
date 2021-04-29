package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class CreateTest {

  /**
   * Tests the function to create a new Player in the database.
   * 
   * @author lengist
   */
  @Test
  void testCreatePlayer() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");


    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals(rs.getString("Name"), "Laura");
      assertEquals(rs.getString("Picture"), "Bild");
      assertEquals(rs.getInt("TotalPoints"), 0);
      assertEquals(rs.getInt("PersonalHighscore"), 0);
      assertEquals(rs.getInt("LaidWords"), 0);
      assertEquals(rs.getInt("PointsPerWordRate"), 0);
      assertEquals(rs.getInt("LongestWord"), 0);
      assertEquals(rs.getInt("TotalPlayedGames"), 0);
      assertEquals(rs.getInt("TotalWins"), 0);
      assertEquals(rs.getDouble("WinRate"), 0.0);
      assertEquals(rs.getString("FaveDic"), "");
      assertEquals(rs.getDouble("Music"), 50.0);
      assertEquals(rs.getDouble("SoundEffect"), 50.0);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cdb.disconnect();

  }

  /*
   * @Test void testCreateServer() { fail("Not yet implemented"); }
   */

  /**
   * Tests the function to insert a letter and the corresponding points into the table Letters.
   * 
   * @author lengist
   */
  @Test
  void testInsertLetters() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.insertLetters("L", 4);
    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      assertEquals(rs.getString("Letter"), "L");
      assertEquals(rs.getInt("Points"), 4);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cdb.disconnect();
  }

  /**
   * Tests the initial filling of the table Letters.
   * 
   * @author lengist
   */
  @Test
  void testFillLetters() {
    CreateDatabase cdb = new CreateDatabase();
    FillDatabase.fillLetters();
    Statement stm;
    int i = 0;
    String[] letter = new String[26];
    int[] point = new int[26];
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
    assertEquals(letter[0], "A");
    assertEquals(point[0], 1);
    assertEquals(letter[1], "B");
    assertEquals(point[1], 3);
    assertEquals(letter[2], "C");
    assertEquals(point[2], 3);
    assertEquals(letter[3], "D");
    assertEquals(point[3], 2);
    assertEquals(letter[4], "E");
    assertEquals(point[4], 1);
    assertEquals(letter[5], "F");
    assertEquals(point[5], 4);
    assertEquals(letter[6], "G");
    assertEquals(point[6], 2);
    assertEquals(letter[7], "H");
    assertEquals(point[7], 4);
    assertEquals(letter[8], "I");
    assertEquals(point[8], 1);
    assertEquals(letter[9], "J");
    assertEquals(point[9], 8);
    assertEquals(letter[10], "K");
    assertEquals(point[10], 5);
    assertEquals(letter[11], "L");
    assertEquals(point[11], 1);
    assertEquals(letter[12], "M");
    assertEquals(point[12], 3);
    assertEquals(letter[13], "N");
    assertEquals(point[13], 1);
    assertEquals(letter[14], "O");
    assertEquals(point[14], 1);
    assertEquals(letter[15], "P");
    assertEquals(point[15], 3);
    assertEquals(letter[16], "Q");
    assertEquals(point[16], 10);
    assertEquals(letter[17], "R");
    assertEquals(point[17], 1);
    assertEquals(letter[18], "S");
    assertEquals(point[18], 1);
    assertEquals(letter[19], "T");
    assertEquals(point[19], 1);
    assertEquals(letter[20], "U");
    assertEquals(point[20], 1);
    assertEquals(letter[21], "V");
    assertEquals(point[21], 4);
    assertEquals(letter[22], "W");
    assertEquals(point[22], 4);
    assertEquals(letter[23], "X");
    assertEquals(point[23], 8);
    assertEquals(letter[24], "Y");
    assertEquals(point[24], 4);
    assertEquals(letter[25], "Z");
    assertEquals(point[25], 10);
    cdb.disconnect();
  }

}
