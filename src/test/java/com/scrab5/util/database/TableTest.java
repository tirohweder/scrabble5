package com.scrab5.util.database;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

/**
 * Class to test the filling, updating and deleting of tables.
 * 
 * @author lengist
 */
public class TableTest {

  /**
   * Tests the inital creation of a column for a new player.
   * 
   * @author lengist
   */
  @Test
  public void playerTest() {
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
      }
      System.out.println("-----------------CREATED-PLAYER-Laura-----------");
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * Tests the inital creation of a column for a new server.
   * 
   * @author lengist
   */
  @Test
  public void serverTest() {
    /* not yet implemented */
  }

  /**
   * Tests the inital creation of a column for a new Letter.
   * 
   * @author lengist
   */
  @Test
  public void lettersTest() throws IOException {
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
   * Tests the deletion of a whole table.
   * 
   * @author lengist
   */
  @Test
  public void deleteTest() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");
    FillDatabase.deleteTable("Player");
    Statement stm;
    try {
      System.out.println("\n-----------------DELETE-TABLE-PLAYER-----------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      if (!rs.next()) {
        System.out.println("Table Player is empty");
      }
      System.out.println("-----------------DELETED-TABLE-PLAYER-----------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Tests the update/edit function for the table player.
   * 
   * @author lengist
   */
  @Test
  public void updatePlayerTest() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");
    /* update the name: */
    FillDatabase.updatePlayer("Name", "Laura", "Maria", 0, 0.0);
    Statement stm;
    try {
      System.out.println("\n---------------CHANGE-IN-PLAYER-FROM-Laura-To-Maria---------");
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
      }
      System.out.println("---------------CHANGE-IN-PLAYER-FINISHED------------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Tests the update/edit function for the table server.
   * 
   * @author lengist
   */
  @Test
  public void updateServerTest() {
    /* not yet implemented */
  }

  /**
   * Tests the inital filling of the table letters.
   * 
   * @author lengist
   * @throws IOException from insertLetters
   */
  @Test
  public void fillLettersTest() throws IOException {
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


  /**
   * Tests the update/edit function for the table Letters.
   * 
   * @author lengist
   * @throws IOException Exception if letter already exists when filling.
   */
  @Test
  public void updateLettersTest() throws IOException {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.insertLetters("L", 4);
    /* updating: */
    FillDatabase.updateLetters("L", 2);
    Statement stm;
    try {
      System.out.println("\n---------------CHANGE-IN-LETTERS-FROM-4-To-2---------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      while (rs.next()) {
        System.out.println("Letter: " + rs.getString("Letter") + ", ");
        System.out.println("Points: " + rs.getInt("Points") + ", ");
      }
      System.out.println("---------------CHANGE-IN-LETTERS-FINISHED------------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
