package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * @author lengist
 * @author hraza
 * 
 *         class to fill all tables in the database
 *
 */
public class FillDatabase extends Database {
  private static PreparedStatement pstmPlayer;
  private static PreparedStatement pstmServer;
  private static PreparedStatement pstmDic;

  /**
   * @author lengist
   * @author hraza
   * 
   *         Closes the statement streams.
   */
  private void closeStatements() {
    try {
      if ((pstmServer != null) && (!pstmServer.isClosed())) {
        pstmServer.close();
      }
      if ((pstmDic != null) && (!pstmDic.isClosed())) {
        pstmDic.close();
      }
      if ((pstmPlayer != null) && (!pstmPlayer.isClosed())) {
        pstmPlayer.close();
      }
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Problem beim Schliessen der Prepared Statements aufgetreten.");
    }
  }

  /**
   * @author lengist
   * @author hraza
   * @param name
   * 
   *        Deleting all entries from the table "name".
   */
  public static void deleteTable(String name) {
    try {
      Statement statement = connection.createStatement();
      String sql = "DELETE FROM " + name + ';';
      statement.execute(sql);
    } catch (SQLException e) {
      System.out.println("Could not perform deletion in table " + name);
      System.out.println(e);
    }
  }

  /**
   * @author hraza
   * @param column
   * @param name
   * @param contentString
   * @param contentInt
   * 
   *        Filling the table player at specific index/column. If variable for column name is from
   *        type integer, variable contentString is default.
   */
  public static void updatePlayer(String column, String name, String contentString,
      int contentInt) {
    PreparedStatement pstm = null;

    if (column == "Name") {
      String sql = "UPDATE Player SET Name = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, contentString);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "Picture") {
      String sql = "UPDATE Player SET Picture = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(2, contentString);
        pstmt.setString(1, name); // update
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "TotalPoints") {
      String sql = "UPDATE Player SET TotalPoints = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name); // update
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "PersonalHighscore") {
      String sql = "UPDATE Player SET PersonalHighscore = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "LaidWords") {
      String sql = "UPDATE Player SET Laidwords = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name); //
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "PointsPerWordRate") {
      String sql = "UPDATE Player SET PointsPerWordRate = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name); //
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "LongestWord") {
      String sql = "UPDATE Player SET LongestWord = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "TotalPlayedGames") {
      String sql = "UPDATE Player SET TotalPlayedGames = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "TotalWins") {
      String sql = "UPDATE Player SET TotalWins = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "WinRate") {
      String sql = "UPDATE Player SET WinRate = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "FavDic") {
      String sql = "UPDATE Player SET FaveDic = ? WHERE name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, contentString);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /*
   * method to fill table player completely. Used when a new player profile is created. Variables
   * for statistics get default values.
   */
  public static void createPlayer(String name, String picture) {
    /* missing: check if name already exists!!! */
    try {
      pstmPlayer = connection.prepareStatement(
          "INSERT INTO Player " + "(Name, Picture, TotalPoints, PersonalHighscore, LaidWords, "
              + "PointsPerWordRate, LongestWord, TotalPlayedGames, TotalWins, "
              + "WinRate, FaveDic) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
      pstmPlayer.setString(1, name);
      pstmPlayer.setString(2, picture);
      pstmPlayer.setInt(3, 0);
      pstmPlayer.setInt(4, 0);
      pstmPlayer.setInt(5, 0);
      pstmPlayer.setInt(6, 0);
      pstmPlayer.setInt(7, 0);
      pstmPlayer.setInt(8, 0);
      pstmPlayer.setInt(9, 0);
      pstmPlayer.setInt(10, 0);
      pstmPlayer.setString(11, "");
      pstmPlayer.executeUpdate();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  // This method updates the entries from the server table at specific column from
  // a specific server
  public static void updateServer(String column, String name, String content) {

    if (column == "ServerListNames") {
      String sql = "UPDATE Server SET ServerListNames = ? WHERE ServerListNames = ?";

      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, content);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "Dictionaries") {
      String sql = "UPDATE Server SET Dictionaries = ? WHERE ServerNames = ?";

      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, content);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "VictoryRanking") {
      String sql = "UPDATE Server SET VictoryRanking = ? WHERE ServerNames = ?";

      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, content);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "GameRanking") {
      String sql = "UPDATE Server SET GameRanking = ? WHERE ServerNames = ?";

      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, content);
        pstmt.setString(2, name);
        // update
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "VictoryLossRate") {
      String sql = "UPDATE Server SET VictoryLossRate = ? WHERE ServerNames = ?";

      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, content);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /*
   * method to fill table server completely. Used when a new server is created. Variables for
   * statistics get default values.
   */
  public static void createServer(String name) {
    /* missing: check if name already exists in database */
    try {
      pstmServer = connection.prepareStatement("INSERT INTO Server (ServerListNames, Dictionaries,"
          + "VictoryRanking, GameRanking, VictoryLossRate) VALUES (?,?,?,?,?);");
      pstmServer.setString(1, name);
      pstmServer.setString(2, "");
      pstmServer.setString(3, "");
      pstmServer.setString(4, "");
      pstmServer.setString(5, "");
      pstmServer.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /*
   * insert letters with corresponding points to calculate points per word
   * 
   * --> check if letter exists needed???
   * 
   */
  public static void insertLetters(String letter, int point) {
    try {
      pstmDic = connection.prepareStatement("INSERT INTO Letters (Letter, Points) VALUES (?,?);");
      pstmDic.setString(1, letter);
      pstmDic.setInt(2, point);
      pstmDic.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * @author lengist
   * @param letter
   * @param point
   * 
   *        Updates the point for a particular letter if a change is needed.
   */
  public static void updateLetters(String letter, int point) {
    try {
      pstmDic = connection.prepareStatement("UPDATE Letters SET Points = ? WHERE Letter = ?");
      pstmDic.setInt(1, point);
      pstmDic.setString(2, letter);
      pstmDic.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
