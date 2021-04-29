package com.scrab5.util.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class UpdateTest {

  /**
   * Tests the function to change the name in the table Player.
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
      assertEquals(rs.getString("Name"), "Maria");
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
    db.disconnect();
  }

  /*
   * 
   * @Test void testUpdateServer() { fail("Not yet implemented"); }
   */

  /**
   * Tests the function to change the corresponding points to a given letter in the table letters.
   * 
   * @author lengist
   */
  @Test
  void testUpdateLetters() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.insertLetters("L", 4);
    FillDatabase.updateLetters("L", 2);
    Statement stm;
    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      assertEquals(rs.getString("Letter"), "L");
      assertEquals(rs.getInt("Points"), 2);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.disconnect();
  }

}
