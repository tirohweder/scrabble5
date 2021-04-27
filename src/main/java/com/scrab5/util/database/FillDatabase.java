package com.scrab5.util.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.scrab5.network.Server;


/**
 * class with methods to fill all tables, edit certain entries or delete a table or certain entries
 * in the database.
 * 
 * @author lengist
 * @author hraza
 */
public class FillDatabase extends Database {
  /*
   * to-do for this class: close statements for all preparedStatements.
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
          }
          break;
        case "player":
          if ((pstmPlayer != null) && (!pstmPlayer.isClosed())) {
            pstmPlayer.close();
          }
          break;
        case "server":
          if ((pstmServer != null) && (!pstmServer.isClosed())) {
            pstmServer.close();
          }
          break;
        case "dic":
          if ((pstmDic != null) && (!pstmDic.isClosed())) {
            pstmDic.close();
          }
          break;
        default:
          /* not yet implemented */
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
   * @param name String with name of the user
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
   * @param name String with name of the user
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
   * @param name String with name of the user
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
   * @param name String with name of the user
   * @param picture String with the path to the picture
   */
  public static boolean createPlayer(String name, String picture) {
    boolean created = false;

    try {
      pstmPlayer = connection.prepareStatement(
          "INSERT INTO Player " + "(Name, Picture, TotalPoints, PersonalHighscore, LaidWords, "
              + "PointsPerWordRate, LongestWord, TotalPlayedGames, TotalWins, "
              + "WinRate, FaveDic, Music, SoundEffect) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
      pstmPlayer.setString(1, name);
      pstmPlayer.setString(2, picture);
      pstmPlayer.setInt(3, 0);
      pstmPlayer.setInt(4, 0);
      pstmPlayer.setInt(5, 0);
      pstmPlayer.setInt(6, 0);
      pstmPlayer.setInt(7, 0);
      pstmPlayer.setInt(8, 0);
      pstmPlayer.setInt(9, 0);
      pstmPlayer.setDouble(10, 0.0);
      pstmPlayer.setString(11, "");
      pstmPlayer.setDouble(12, 1.0);
      pstmPlayer.setDouble(13, 1.0);
      pstmPlayer.executeUpdate();
      created = true;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return created;
  }

  /**
   * Filling the table player at specific index/column. If variable for column name is from type
   * integer, variable contentString is default.
   * 
   * @author hraza
   * @param column String with the name of the column in the table where a change needs to be done
   * @param name String with name of the user
   * @param contentString String that contains the new information that needs to be stored in the
   *        database
   * @param contentInt Integer that contains the new information that needs to be stored in the
   *        database
   */
  public static void updatePlayer(String column, String name, String contentString, int contentInt,
      double doubleValues) {
    PreparedStatement pstm = null;

    if (column == "Name") {
      String sql = "UPDATE Player SET Name = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, contentString);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "Picture") {
      String sql = "UPDATE Player SET Picture = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(2, contentString);
        pstmt.setString(1, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "TotalPoints") {
      String sql = "UPDATE Player SET TotalPoints = ? WHERE Name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
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
        pstmt.setDouble(1, doubleValues);
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
    } else if (column == "Music") {
      String sql = "UPDATE Player SET Music = ? WHERE name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setDouble(1, doubleValues);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    } else if (column == "SoundEffect") {
      String sql = "UPDATE Player SET SoundEffect = ? WHERE name = ?";
      try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setDouble(1, doubleValues);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  /**
   * Method to fill table server completely. Used when a new server is created.
   * 
   * @author lengist
   * @param name String with name of the user
   */
  public static void createServer(Server serverObject) {
    try {
      pstmServer = connection
          .prepareStatement("INSERT INTO Server (ServerHostName, Information) VALUES (?,?);");
      pstmServer.setString(1, serverObject.getHost());
      pstmServer.setObject(2, serverObject);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Updates the entries from the table server at a specific serverHostName.
   * 
   * @author lengist
   * @param serverObject an object received from the server with all information needed for the
   *        statistics in a hosted game
   */
  public static void updateServer(Server serverObject) {
    String sql = "UPDATE Server SET Information = ? WHERE ServerHostName = ?";
    PreparedStatement pstm;
    try {
      pstm = connection.prepareStatement(sql);
      pstm.setObject(1, serverObject);
      pstm.setString(2, serverObject.getHost());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserts letters with corresponding points.
   * 
   * @author lengist
   * @author hraza
   * @param letter String with the letter that needs to be inserted in the database
   * @param point Integer with the correpsonding points for the given letter
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
   * Fills table letters initial.
   * 
   * @author lengist
   * @throws IOException Exception from insertLetters
   */
  public static void fillLetters() {
    String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    int[] points = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    for (int i = 0; i < 26; i++) {
      insertLetters(letter[i], points[i]);
    }

  }

  /**
   * Updates the point for a particular letter if a change is needed.
   * 
   * @author lengist
   * @param letter String with the letter which points need to be updated in the database
   * @param point Integer with the correpsonding points for the given letter
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

  public static void main(String[] args) throws IOException {
    CreateDatabase cb = new CreateDatabase();
    fillLetters();
  }



}


