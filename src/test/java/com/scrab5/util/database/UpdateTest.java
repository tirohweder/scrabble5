package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

/**
 * This class tests the methods to change a entry in a table in the database. The other required
 * functions supported by methods in FillDatabase.java, UseDatabase.java and
 * PlayerProfileDatabase.java are getting tested in other test classes. Note: In the methods with
 * access to the database the connection gets established and disconnect individually for each
 * method. Because of that every test method needs to do so too.
 * 
 * @author lauraengist
 */
class UpdateTest {
  CreateDatabase cd = new CreateDatabase();

  /**
   * Tests the function to change the name in the table Player. Test for Use Case 1 update.
   * 
   * @author lengist
   */
  @Test
  void testUpdatePlayer() {
    cd.createTest();
    FillDatabase.createPlayer("Laura");
    FillDatabase.updatePlayer("Name", "Laura", "Maria", 0, 0.0);
    Database.reconnect();
    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals("Maria", rs.getString("Name"));
      assertEquals(0, rs.getInt("TotalPoints"));
      assertEquals(0, rs.getInt("PersonalHighscore"));
      assertEquals(0, rs.getInt("LaidWords"));
      assertEquals(0, rs.getInt("PointsPerWordRate"));
      assertEquals(0, rs.getInt("TotalPlayedGames"));
      assertEquals(0, rs.getInt("TotalWins"));
      assertEquals(0.0, rs.getDouble("WinRate"));
      assertEquals("", rs.getString("FaveDic"));
      assertEquals(50.0, rs.getDouble("Music"));
      assertEquals(25.0, rs.getDouble("SoundEffect"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cd.deleteDatabaseFile();
  }

  /**
   * Tests the function to change the corresponding points to a given letter in the table letters.
   * Test for F.5 "set different letter distributions (points and occurrences of letter tiles).
   * 
   * @author lengist
   */
  @Test
  void testUpdateLetters() {
    cd.createTest();
    FillDatabase.insertLetters("L", 4, 6);
    FillDatabase.updatePointLetters("L", 2);
    FillDatabase.updateOccurrenceLetters("L", 7);
    Database.reconnect();

    Statement stm;
    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      assertEquals("L", rs.getString("Letter"));
      assertEquals(2, rs.getInt("Points"));
      assertEquals(7, rs.getInt("Occurrence"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    cd.deleteDatabaseFile();
  }

}
