package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FillDatabase extends Database {
  private PreparedStatement pstmPlayer, pstmServer, pstmDic;

  /* closes Statement stream */
  private void closeStatements() {}

  /* deletes all entries from table "name" */
  private void deleteTable(String name) {}

  /* creates the preparedStatements to insert something in a table */
  private void prepareStatements() {
    try {
      pstmPlayer = connection.prepareStatement(
          "INSERT INTO Player " + "(Name, Picture, TotalPoints, PersonalHighscore, LaidWords, "
              + "PointsPerWordRate, LongestWord, TotalPlayedGames, TotalWins, "
              + "WinRate, FaveDic) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
      pstmServer = connection.prepareStatement("INSERT INTO Server (ServerNames, Dictionaries,"
          + "VictoryRanking, GameRanking, VictoryLossRate) VALUE (?,?,?,?,?);");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /*
   * method to fill table player at specific index/column. If variable for column name is from type
   * integer, variable content is default.
   */
  public static void fillPlayer(int index, String name, String content, int content2) {}

  /*
   * method to fill table player completely. Used when a new player profile is created. Variables
   * for statistics get default values.
   */
  public void fillPlayerComplete(String name, String picture) {}

  /*
   * method to fill table server at specific index/column.
   */
  public void fillServer(int index, String name, String content) {}

  /*
   * method to fill table server completely. Used when a new server is created. Variables for
   * statistics get default values.
   */
  public void fillServerComplete(String name) {}

  /* method to fill table dictionary from file */
  public void fillLetters(String name) {
    // read from intake
  }

  /*
   * for testing: public static void main(String[] args) { new FillDatabase().fillPlayer(…); }
   */

}
