package com.scrab5.util.database;

import java.sql.SQLException;
import java.sql.Statement;


public class CreateDatabase extends Database {

  /* requests methods to create tables */
  private void createTable() {
    createTablePlayer();
    createTableServer();
    createTableLetters();
  }

  /* removes table if a re-start is intended */
  private void removeTable(String name) {
    try (Statement stm = connection.createStatement()) {
      String sql = "DROP TABLE IF EXISTS " + name;
      stm.executeUpdate(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /* Generation of player table */
  private void createTablePlayer() {
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

  /* Generation of server table */
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

  /* Generation of letter table */
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
  public static void main(String[] args) {
    CreateDatabase db = new CreateDatabase();
    System.out.println();
    db.createTable();
    // db.disconnect();
  }



}
