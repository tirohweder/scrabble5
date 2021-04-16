package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

/**
 * @author lengist
 * 
 *         Class to test the filling, updating and deleting of tables.
 *
 */
public class TableTest {

  /**
   * @author lengist
   * 
   *         Tests the inital creation of a column for a new player.
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
   * @author lengist
   * 
   *         Tests the inital creation of a column for a new server.
   */
  @Test
  public void serverTest() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createServer("Laura's server");
    Statement stm;
    try {
      System.out.println("\n-----------------CREATE-SERVER-Laura's server-----------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Server");
      while (rs.next()) {
        System.out.println("Server Name: " + rs.getString("ServerListNames") + ", ");
        System.out.println("Dictionaries: " + rs.getString("Dictionaries") + ", ");
        System.out.println("Victory Ranking: " + rs.getString("VictoryRanking") + ", ");
        System.out.println("Game Ranking: " + rs.getString("GameRanking") + ", ");
        System.out.println("Victory Loss Rate: " + rs.getString("VictoryLossRate") + ", ");
      }
      System.out.println("-----------------CREATED-SERVER-Laura's server-----------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author lengist
   * 
   *         Tests the inital creation of a column for a new Letter.
   */
  @Test
  public void lettersTest() {
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
   * @author lengist
   * 
   *         Tests the deletion of a whole table.
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
   * @author lengist
   * 
   *         Tests the update/edit function for the table player.
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
   * @author lengist
   * 
   *         Tests the update/edit function for the table server.
   */
  @Test
  public void updateServerTEst() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createServer("Laura's server");
    /* update the server name */
    FillDatabase.updateServer("ServerListNames", "Laura's server", "Maria's server");
    Statement stm;
    try {
      System.out.println("\n---------------CHANGE-IN-SERVER-FROM-Laura-To-Maria---------");
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Server");
      while (rs.next()) {
        System.out.println("Server Name: " + rs.getString("ServerListNames") + ", ");
        System.out.println("Dictionaries: " + rs.getString("Dictionaries") + ", ");
        System.out.println("Victory Ranking: " + rs.getString("VictoryRanking") + ", ");
        System.out.println("Game Ranking: " + rs.getString("GameRanking") + ", ");
        System.out.println("Victory Loss Rate: " + rs.getString("VictoryLossRate") + ", ");
      }
      System.out.println("---------------CHANGE-IN-SERVER-FINISHED------------");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * @author lengist
   * 
   *         Tests the update/edit function for the table Letters.
   */
  @Test
  public void updateLettersTest() {
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
