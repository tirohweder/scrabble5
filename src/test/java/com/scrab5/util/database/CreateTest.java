package com.scrab5.util.database;

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
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");

    Statement stm;
    try {
      System.out.println("\n-----------------CREATE-PLAYER-Laura-----------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      while (rs.next()) {
        System.out.println("Name: " + rs.getString("Name") + ", ");
        System.out.println("Picture: " + rs.getString("Picture") + ", ");
        System.out.println("Total points: " + rs.getString("TotalPoints") + ", ");
        System.out.println("Personal Highscore: " + rs.getString("PersonalHighscore") + ", ");
        System.out.println("Laid words: " + rs.getString("LaidWords") + ", ");
        System.out.println("Points per word rate: " + rs.getString("PointsPerWordRate") + ", ");
        System.out.println("Longest word: " + rs.getString("LongestWord") + ", ");
        System.out.println("Total played games: " + rs.getString("TotalPlayedGames") + ", ");
        System.out.println("Total wins: " + rs.getString("TotalWins") + ", ");
        System.out.println("Win rate: " + rs.getString("WinRate") + ", ");
        System.out.println("Favorite dictionary: " + rs.getString("FaveDic") + ", ");
        System.out.println("Music: " + rs.getString("Music") + ", ");
        System.out.println("Sound Effect: " + rs.getString("SoundEffect") + ", ");
      }
      System.out.println("-----------------CREATED-PLAYER-Laura-----------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
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
    CreateDatabase db = new CreateDatabase();
    FillDatabase.insertLetters("L", 4);
    Statement stm;
    try {
      System.out.println("\n-----------------CREATE-Letter-FOR-L----------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      while (rs.next()) {
        System.out.println("Letter: " + rs.getString("Letter") + ", ");
        System.out.println("Points: " + rs.getInt("Points") + ", ");
      }
      System.out.println("-----------------CREATED-Letter-FOR-L----------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Tests the initial filling of the table Letters.
   * 
   * @author lengist
   */
  @Test
  void testFillLetters() {
    CreateDatabase cb = new CreateDatabase();
    FillDatabase.fillLetters();
    Statement stm;
    try {
      System.out.println("\n---------------View all content of table letters---------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      while (rs.next()) {
        System.out.print("Letter: " + rs.getString("Letter") + ",  ");
        System.out.println("Points: " + rs.getInt("Points") + ", ");
      }
      System.out.println("-----------------------------------------------------------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
