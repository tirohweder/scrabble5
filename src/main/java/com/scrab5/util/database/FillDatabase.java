package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * class with methods to fill all tables, edit certain entries or delete a table or certain entries
 * in the database.
 * 
 * @author lengist
 * @author hraza
 */
public class FillDatabase extends Database {
  /*
   * to-do for this class:
   * 
   * @hraza: closing statements for all statements in FillDatabase UI: view errors if name of
   * server/player or a letter already exists in the corresponding table.
   */
  private static PreparedStatement pstmPlayer;
  private static PreparedStatement pstmServer;
  private static PreparedStatement pstmDic;
  private static PreparedStatement pstmDelete;

  /**
   * Closes the statement stream for preparedSatetements with "name" in its notation.
   * 
   * @author lengist
   * @author hraza
   */
  private static void closeStatement(String name) {
    try {
      switch (name) {
        case "delete":
          if ((pstmDelete != null) && (!pstmDelete.isClosed())) {
            pstmDelete.close();
          } ;
          break;
        case "player":
          if ((pstmPlayer != null) && (!pstmPlayer.isClosed())) {
            pstmPlayer.close();
          } ;
          break;
        case "server":
          if ((pstmServer != null) && (!pstmServer.isClosed())) {
            pstmServer.close();
          } ;
          break;
        case "dic":
          if ((pstmDic != null) && (!pstmDic.isClosed())) {
            pstmDic.close();
          } ;
          break;
      }
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Problem with closing statement " + name + "!");
    }
  }


  /**
   * Closes all prepared statements when the application gets closed.
   * 
   * @author lengist
   */
  public static void closeAllStatements() {
    try {
      if ((pstmDelete != null) && (!pstmDelete.isClosed())) {
        pstmDelete.close();
      }
      if ((pstmPlayer != null) && (!pstmPlayer.isClosed())) {
        pstmPlayer.close();
      }
      if ((pstmServer != null) && (!pstmServer.isClosed())) {
        pstmServer.close();
      }
      if ((pstmDic != null) && (!pstmDic.isClosed())) {
        pstmDic.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Deleting all entries from the table "name".
   * 
   * @author lengist
   * @author hraza
   * @param name
   */
  public static void deleteTable(String name) {
    try {
      Statement statement = connection.createStatement();
      String sql = "DELETE FROM " + name;
      statement.execute(sql);
    } catch (SQLException e) {
      System.out.println("Could not perform deletion in table " + name);
      System.out.println(e);
    }
  }

  /**
   * Deletes a certain player with name "name" in the table Player.
   * 
   * @author lengist
   * @param name
   */
  public static void deletePlayer(String name) {
    try {
      String sql = "DELETE FROM Player WHERE Name = ?";
      pstmDelete = connection.prepareStatement(sql);
      pstmDelete.setString(1, name);
      pstmDelete.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Could not perform deletion from player " + name);
      System.out.println(e);
    }
  }

  /**
   * Deletes a certain server with name "name" in the table Server.
   * 
   * @author lengist
   * @param name
   */
  public static void deleteServer(String name) {
    try {
      String sql = "DELETE FROM Server WHERE ServerListNames = ?";
      pstmDelete = connection.prepareStatement(sql);
      pstmDelete.setString(1, name);
      pstmDelete.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Could not perform deletion from server " + name);
      System.out.println(e);
    }
  }

  /**
   * Method to fill table player completely. Used when a new player profile is created. Variables
   * for statistics get default values.
   * 
   * @author lengist
   * @author hraza
   * @param name
   * @param picture
   */
  public static void createPlayer(String name, String picture) {
    boolean alreadyExists = false;
    try {
      Statement test = connection.createStatement();
      ResultSet rs = test.executeQuery("SELECT Name FROM Player");
      while (rs.next()) {
        if (rs.getString("Name").equals(name)) {
          alreadyExists = true;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    if (!alreadyExists) {
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
        e.printStackTrace();
      }
    } else {
      /* to-do: error message needs to be displayed via UI: */
      System.out
          .println("Player with name: " + name + "already exists. Please choose different name");
    }
  }

  /**
   * Filling the table player at specific index/column. If variable for column name is from type
   * integer, variable contentString is default.
   * 
   * @author hraza
   * @param column
   * @param name
   * @param contentString
   * @param contentInt
   */
  public static void updatePlayer(String column, String name, String contentString, int contentInt,
      double rate) {
    PreparedStatement pstm = null;

    if (column == "Name") {
      String sql = "UPDATE Player SET Name = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, contentString);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM Player");
        while (rs.next()) {
          System.out.println("Namen jetzt: " + rs.getString("Name") + ", ");
          System.out.println();
        }
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
        pstmt.setDouble(1, rate);
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

  /**
   * Method to fill table server completely. Used when a new server is created. Variables for
   * statistics get default values.
   * 
   * @author lengist
   * @author hraza
   * @param name
   */
  public static void createServer(String name) {
    boolean alreadyExists = false;
    try {
      Statement test = connection.createStatement();
      ResultSet rs = test.executeQuery("SELECT ServerListNames FROM Server");
      while (rs.next()) {
        if (rs.getString("ServerListNames").equals(name)) {
          alreadyExists = true;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    if (!alreadyExists) {
      try {
        pstmServer =
            connection.prepareStatement("INSERT INTO Server (ServerListNames, Dictionaries,"
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
    } else {
      /* to-do: error message needs to be displayed via UI: */
      System.out
          .println("Server with name: " + name + "already exists. Please choose different name");
    }
  }

  /**
   * Updates the entries from the table server at a specific column from a specific serverListName.
   * 
   * @author hraza
   * @param column
   * @param name
   * @param content
   */
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

  /**
   * Inserts letters with corresponding points to calculate points per word.
   * 
   * @author lengist
   * @author hraza
   * @param letter
   * @param point
   */
  public static void insertLetters(String letter, int point) {
    boolean alreadyExists = false;
    try {
      Statement test = connection.createStatement();
      ResultSet rs = test.executeQuery("SELECT Letter FROM Letters");
      while (rs.next()) {
        if (rs.getString("Letter").equals(letter)) {
          alreadyExists = true;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }

    if (!alreadyExists) {
      try {
        pstmDic = connection.prepareStatement("INSERT INTO Letters (Letter, Points) VALUES (?,?);");
        pstmDic.setString(1, letter);
        pstmDic.setInt(2, point);
        pstmDic.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      /* to-do: connection to UI? --> display error and need to update and not insert. */
      System.out.println("Letter already exists. Please update");
    }
  }


  /**
   * Updates the point for a particular letter if a change is needed.
   * 
   * @author lengist
   * @param letter
   * @param point
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
