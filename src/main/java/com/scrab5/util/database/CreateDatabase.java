package com.scrab5.util.database;

import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase extends Database {

  public CreateDatabase() {
    createTable();
  }

  /**
   * Create all tables initialy.
   * 
   * @author lengist
   */
  public void createTable() {
    createTablePlayer();
    createTableServer();
    createTableLetters();
  }

  /**
   * Remove a table with the name "name". For example in case of a new start.
   * 
   * @author lengist
   * @param name
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
   * Generates the table "player" with all required columns.
   * 
   * @author lengist
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
   * Generates the table "server" with all required columns.
   * 
   * @author lengist
   */
  private void createTableServer() {
    removeTable("Server");
    try (Statement stm = connection.createStatement()) {
      String sql = "CREATE TABLE Server (ServerHostName TEXT," + "Information REAL)";
      stm.executeUpdate(sql);
      System.out.println("Table for server generated!");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generates the table "letter" with all required columns.
   * 
   * @author lengist
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

}
