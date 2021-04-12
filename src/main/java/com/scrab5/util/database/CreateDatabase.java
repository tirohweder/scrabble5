package com.scrab5.util.database;

import java.sql.SQLException;
import java.sql.Statement;

/*
 * Class to create the different tables in the database.
 * 
 * @author lengist
 */


public class CreateDatabase extends Database {

  public CreateDatabase() {
    createTable();
  }

  /**
   * @author lengist
   * 
   *         Create all tables initialy.
   */
  public void createTable() {
    createTablePlayer();
    createTableServer();
    createTableLetters();
  }

  /**
   * @author lengist
   * @param name
   * 
   *        Remove a table with the name "name". For example in case of a new start.
   */
  private void removeTable(String name) {
    try (Statement stm = connection.createStatement()) {
      String sql = "DROP TABLE IF EXISTS " + name;
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author lengist
   * 
   *         Generates the table "player" with all required columns.
   */
  public void createTablePlayer() {
    removeTable("Player");
    try (Statement stm = connection.createStatement()) {
      String sql = "CREATE TABLE Player (Name TEXT NOT NULL," + "Picture TEXT,"
          + "TotalPoints INTEGER NOT NULL," + "PersonalHighscore INTEGER NOT NULL,"
          + "LaidWords INTEGER NOT NULL," + "PointsPerWordRate INTEGER NOT NULL,"
          + "LongestWord TEXT," + "TotalPlayedGames INTEGER NOT NULL,"
          + "TotalWins INTEGER NOT NULL," + "WinRate REAL," + "FaveDic TEXT)";
      stm.executeUpdate(sql);
      System.out.println("Table for player generated!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author lengist
   * 
   *         Generates the table "server" with all required columns.
   */
  private void createTableServer() {
    removeTable("Server");
    try (Statement stm = connection.createStatement()) {
      String sql = "CREATE TABLE Server (ServerListNames TEXT," + "Dictionaries TEXT,"
          + "VictoryRanking TEXT," + "GameRanking TEXT," + "VictoryLossRate TEXT)";
      stm.executeUpdate(sql);
      System.out.println("Table for server generated!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * @author lengist
   * 
   *         Generates the table "letter" with all required columns.
   */
  private void createTableLetters() {
    removeTable("Letters");
    try (Statement stm = connection.createStatement()) {
      String sql = "CREATE TABLE Letters (Letter TEXT NOT NULL, Points INTEGER NOT NULL)";
      stm.executeUpdate(sql);
      System.out.println("Table for letters generated!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /* for testing */
  /*
   * public static void main(String[] args) { CreateDatabase db = new CreateDatabase();
   * System.out.println(); db.createTable(); // db.disconnect(); }
   */



}
