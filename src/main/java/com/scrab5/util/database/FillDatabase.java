package com.scrab5.util.database;

import com.scrab5.network.Server;
import com.scrab5.network.ServerStatistics.ClientStatistic;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

/**
 * class with methods to fill all tables, edit certain entries or delete a table or certain entries
 * in the database. Note: To save the database and make sure that not two clients at the same time
 * are able to make a request/change to the database file, the connection gets established and
 * disconnected in every method individual where it is necessary.
 *
 * @author lengist
 * @author hraza
 */
public class FillDatabase extends Database {

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
  private static synchronized void closeStatement(String name) {
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
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Deleting all entries from the table "name".
   *
   * @author lengist
   * @author hraza
   * @param name String with name of the the table
   */
  protected static synchronized void deleteTable(String name) {
    Database.reconnect();
    Statement statement;
    try {
      statement = connection.createStatement();
      String sql = "DELETE FROM " + name;
      statement.execute(sql);
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }

  /**
   * Deletes a certain player with name "name" in the table Player.
   *
   * @author lengist
   * @param name String with name of the user
   */
  public static synchronized void deletePlayer(String name) {
    Database.reconnect();
    try {
      String sql = "DELETE FROM Player WHERE Name = ?";
      pstmDelete = connection.prepareStatement(sql);
      pstmDelete.setString(1, name);
      pstmDelete.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    closeStatement("delete");
    Database.disconnect();
  }

  /**
   * Method to fill table player completely. Used when a new player profile is created. Variables
   * for statistics get default values.
   *
   * @author lengist
   * @author hraza
   * @param name String with the name of the user
   */
  public static synchronized void createPlayer(String name) {
    Database.reconnect();
    try {
      pstmPlayer = connection.prepareStatement(
          "INSERT INTO Player " + "(Name, TotalPoints, PersonalHighscore, LaidWords, "
              + "PointsPerWordRate, TotalPlayedGames, TotalWins, "
              + "WinRate, FaveDic, Music, SoundEffect) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
      pstmPlayer.setString(1, name);
      pstmPlayer.setInt(2, 0);
      pstmPlayer.setInt(3, 0);
      pstmPlayer.setInt(4, 0);
      pstmPlayer.setInt(5, 0);
      pstmPlayer.setInt(6, 0);
      pstmPlayer.setInt(7, 0);
      pstmPlayer.setDouble(8, 0.0);
      pstmPlayer.setString(9, "");
      pstmPlayer.setDouble(10, 50.0);
      pstmPlayer.setDouble(11, 25.0);
      pstmPlayer.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      pstmPlayer.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }

  /**
   * Filling the table player at a specific index/column. If the variable for column name is from
   * type integer, the variable contentString is default.
   *
   * @author hraza
   * @author lengist
   * @param column String with the name of the column in the table where a change needs to be done
   * @param name String with name of the user
   * @param contentString String that contains the new information that needs to be stored in the
   *        database
   * @param contentInt Integer that contains the new information that needs to be stored in the
   *        database
   * @param doubleValues the values for the rate, given as double
   */
  protected static synchronized void updatePlayer(String column, String name, String contentString,
      int contentInt, double doubleValues) {
    Database.disconnect();
    Database.reconnect();

    PreparedStatement pstmt = null;

    try {
      if (column.equals("Name")) {
        String sql = "UPDATE Player SET Name = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, contentString);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("TotalPoints")) {
        String sql = "UPDATE Player SET TotalPoints = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("PersonalHighscore")) {
        String sql = "UPDATE Player SET PersonalHighscore = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("LaidWords")) {
        String sql = "UPDATE Player SET Laidwords = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("PointsPerWordRate")) {
        String sql = "UPDATE Player SET PointsPerWordRate = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("TotalPlayedGames")) {
        String sql = "UPDATE Player SET TotalPlayedGames = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("TotalWins")) {
        String sql = "UPDATE Player SET TotalWins = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, contentInt);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("WinRate")) {
        String sql = "UPDATE Player SET WinRate = ? WHERE Name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setDouble(1, doubleValues);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("FavDic")) {
        String sql = "UPDATE Player SET FaveDic = ? WHERE name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, contentString);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("Music")) {
        String sql = "UPDATE Player SET Music = ? WHERE name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setDouble(1, doubleValues);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      } else if (column.equals("SoundEffect")) {
        String sql = "UPDATE Player SET SoundEffect = ? WHERE name = ?";
        pstmt = connection.prepareStatement(sql);
        pstmt.setDouble(1, doubleValues);
        pstmt.setString(2, name);
        pstmt.executeUpdate();
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    Database.disconnect();
  }

  /**
   * Method to fill table server completely. Used when a new server is created.
   *
   * @author lengist
   * @author nitterhe
   * @param serverHost the name of the host of the server
   * @param clientUsername with name of the user
   * @param ipAddress the ip adress of the server
   */
  public static synchronized void createServerRow(String serverHost, String clientUsername,
      String ipAddress) {
    Database.reconnect();
    try {
      pstmServer = connection
          .prepareStatement("INSERT INTO Server (ServerHostName, ClientUsername, GamesPlayed, "
              + "GamesWon, IPAddress) VALUES (?,?,?,?,?);");
      pstmServer.setString(1, serverHost);
      pstmServer.setString(2, clientUsername);
      pstmServer.setInt(3, 0);
      pstmServer.setInt(4, 0);
      pstmServer.setString(5, ipAddress);
      pstmServer.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      pstmServer.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }

  /**
   * Updates the entries from the table server for a specific serverHostName.
   *
   * @author lengist
   * @author nitterhe
   * @param serverObject an object received from the server with all information needed for the
   *        statistics in a hosted game
   */
  public static synchronized void updateServer(Server serverObject) {
    Database.reconnect();
    String sql = "UPDATE Server SET gamesPlayed = ?, gamesWon = ? "
        + "WHERE ServerHostName = ? AND ClientUsername = ?;";
    PreparedStatement pstm;
    try {
      Iterator<ClientStatistic> iterator =
          serverObject.getServerStatistics().getServerStatistics().values().iterator();
      ClientStatistic cs;
      while (iterator.hasNext()) {
        cs = iterator.next();
        pstm = connection.prepareStatement(sql);
        pstm.setInt(1, cs.getGamesPlayed());
        pstm.setInt(2, cs.getGamesWon());
        pstm.setString(3, serverObject.getHost());
        pstm.setString(4, cs.getClientName());
        pstm.executeUpdate();
        pstm.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }

  /**
   * Inserts letters with corresponding points.
   *
   * @author lengist
   * @author hraza
   * @param letter String with the letter that needs to be inserted in the database
   * @param point int with the corresponding points for the given letter
   * @param occurrence int with the occurrence of the letter in the bag
   */
  public static synchronized void insertLetters(String letter, int point, int occurrence) {
    Database.reconnect();
    try {
      pstmDic = connection
          .prepareStatement("INSERT INTO Letters (Letter, Points, Occurrence) VALUES (?,?,?);");
      pstmDic.setString(1, letter);
      pstmDic.setInt(2, point);
      pstmDic.setInt(3, occurrence);
      pstmDic.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      pstmDic.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
  }

  /**
   * Fills table letters initially.
   *
   * @author lengist
   */
  public static synchronized void fillLetters() {
    deleteTable("Letters");
    Database.reconnect();
    String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "*"};
    int[] points =
      {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10, 0};
    int[] occurrence =
      {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1, 2};
    for (int i = 0; i < 27; i++) {
      insertLetters(letter[i], points[i], occurrence[i]);
    }
    Database.disconnect();
  }

  /**
   * Updates the occurrence for a particular letter if a change is needed.
   *
   * @author lengist
   * @param letter String with the letter where the occurrence needs to be updated in the database
   * @param occurrence int with the new occurrence for the given letter
   */
  protected static synchronized void updateOccurrenceLetters(String letter, int occurrence) {
    Database.reconnect();
    try {
      pstmDic = connection.prepareStatement("UPDATE Letters SET Occurrence = ? WHERE Letter = ?");
      pstmDic.setInt(1, occurrence);
      pstmDic.setString(2, letter);
      pstmDic.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    closeStatement("dic");
    Database.disconnect();
  }

  /**
   * Updates the points for a particular letter if a change is needed.
   *
   * @author lengist
   * @param letter String with the letter where the points need to be updated in the database
   * @param point int with the new points for the given letter
   */
  protected static synchronized void updatePointLetters(String letter, int point) {
    Database.reconnect();
    try {
      pstmDic = connection.prepareStatement("UPDATE Letters SET Points = ? WHERE Letter = ?");
      pstmDic.setInt(1, point);
      pstmDic.setString(2, letter);
      pstmDic.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    closeStatement("dic");
    Database.disconnect();
  }
}
