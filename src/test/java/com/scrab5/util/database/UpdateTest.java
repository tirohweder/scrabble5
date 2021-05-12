package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class UpdateTest {

  /**
   * Tests the function to change the name in the table Player. Test for Use Case 1 update.
   * 
   * @author lengist
   */

  @Test
  void testUpdatePlayer() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");
    FillDatabase.updatePlayer("Name", "Laura", "Maria", 0, 0.0);
    Statement stm;

    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      assertEquals("Maria", rs.getString("Name"));
      assertEquals("Bild", rs.getString("Picture"));
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
      assertEquals(50.0, rs.getDouble("SoundEffect"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.disconnect();
  }

  /*
   * 
   * @Test void testUpdateServer() { fail("Not yet implemented"); }
   */

  /**
   * Tests the function to change the corresponding points to a given letter in the table letters.
   * Test for F.5 "set different letter distributions (points and occurrences of letter tiles).
   * 
   * @author lengist
   */
  @Test
  void testUpdateLetters() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.insertLetters("L", 4, 6);
    FillDatabase.updatePointLetters("L", 2);
    FillDatabase.updateOccurrenceLetters("L", 7);

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
    db.disconnect();
  }

}
