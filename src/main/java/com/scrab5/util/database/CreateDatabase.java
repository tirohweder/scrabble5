package com.scrab5.util.database;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creation of all tables in the database.
 * 
 * @author lengist
 */
public class CreateDatabase extends Database {

  public CreateDatabase() {
    reconnect();
    createTable();
  }

  /**
   * Create all tables initialy.
   * 
   * @author lengist
   */
  protected void createTable() {
    createTablePlayer();
    createTableServer();
    createTableLetters();
  }

  /**
   * Remove a table with the name "name". For example in case of a new start.
   * 
   * @author lengist
   * @param name String representing the name of the player who's column needs to be deleted
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
   * Generates the table "Player" with all required columns.
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
          + "TotalWins INTEGER NOT NULL," + "WinRate REAL," + "FaveDic TEXT," + "Music REAL,"
          + "SoundEffect REAL)";
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Generates the table "server" with all required columns.
   * 
   * @author lengist
   * @author nitterhe
   */
  private void createTableServer() {
    removeTable("Server");
    try (Statement stm = connection.createStatement()) {
      String sql = "CREATE TABLE Server (ServerHostName TEXT NOT NULL," + "ClientUsername TEXT,"
          + "GamesPlayed INTEGER," + "GamesWon INTEGER," + "IPAddress TEXT NOT NULL)";
      stm.executeUpdate(sql);
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
      String sql =
          "CREATE TABLE Letters (Letter TEXT NOT NULL, Points INTEGER NOT NULL, "
          + "Occurrence INTEGER NOT NULL)";
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
