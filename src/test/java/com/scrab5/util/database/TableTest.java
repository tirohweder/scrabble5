package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.Test;

public class TableTest {

  @Test
  public void player() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");
    Statement stm;
    try {
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
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  @Test
  public void server() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createServer("Laura's server");
    Statement stm;
    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Server");
      while (rs.next()) {
        System.out.println("Server Name: " + rs.getString("ServerListNames") + ", ");
        System.out.println("Dictionaries: " + rs.getString("Dictionaries") + ", ");
        System.out.println("Victory Ranking: " + rs.getString("VictoryRanking") + ", ");
        System.out.println("Game Ranking: " + rs.getString("GameRanking") + ", ");
        System.out.println("Victory Loss Rate: " + rs.getString("VictoryLossRate") + ", ");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void letters() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.insertLetters("L", 4);
    Statement stm;
    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Letters");
      while (rs.next()) {
        System.out.println("Letter: " + rs.getString("Letter") + ", ");
        System.out.println("Points: " + rs.getInt("Points") + ", ");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void delete() {
    CreateDatabase db = new CreateDatabase();
    FillDatabase.createPlayer("Laura", "Bild");
    FillDatabase.deleteTable("Player");
    Statement stm;
    try {
      stm = Database.connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      if (!rs.next()) {
        System.out.println("Table Player is empty");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void updatePlayer() {}

  @Test
  public void updateServer() {}

  @Test
  public void updateLetters() {}


}
