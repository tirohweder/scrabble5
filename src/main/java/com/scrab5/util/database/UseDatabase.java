package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.scrab5.network.ServerStatistics;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UseDatabase extends Database {

  /**
   * Displaying the content of current table Letters.
   *
   * @return ResultSet including the content from the table letters
   * @author lengist
   */
  public synchronized static ResultSet viewLetters() {
    ResultSet rs = null;
    Database.reconnect();
    try {
      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT * FROM Letters");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //Database.disconnect();
    return rs;
  }

  /**
   * Checks whether the table Player is empty or with entries.
   *
   * @return boolean returning true if there is no entry in the table player
   * @author lengist
   */
  public synchronized static boolean tablePlayerIsEmpty() {
    Database.reconnect();
    boolean empty = false;
    int anzahl = 0;
    try {
      Statement stm = connection.createStatement();
      String sql = "SELECT COUNT(*) FROM Player";
      ResultSet rs = stm.executeQuery(sql);
      anzahl = rs.getInt(1);
      if (anzahl == 0) {
        empty = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return empty;
  }

  /**
   * Returns all player names from the table Player.
   *
   * @return ResultSet returning the content of the table player
   * @author lengist
   */
  public synchronized static ResultSet getAllPlayerRS() {
    Database.reconnect();
    ResultSet rs = null;
    try {

      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Name FROM Player");

    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return rs;
  }

  /**
   * Returns a ObservableList so all player profiles can be displayed in a ComboBox
   *
   * @return ObservableList including the content of the table player
   * @author mherre
   */
  public synchronized static ObservableList<String> getAllPlayer() {
    Database.reconnect();
    ResultSet rs = null;
    ObservableList<String> ol = FXCollections.observableArrayList();
    try {
      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Name FROM Player");

      while (rs.next()) {
        ol.add(rs.getString(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return ol;
  }

  /**
   * Returns all the letters as array
   *
   * @return String array containing all letters saved in the database
   * @author lengist
   */
  public synchronized static String[] getAllLetters() {
    Database.reconnect();
    ResultSet rs = null;
    Statement stm;
    ArrayList<String> letter = new ArrayList<String>();
    try {
      stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Letter FROM Letters");
      while (rs.next()) {
        letter.add(rs.getString(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    String[] letters = new String[letter.size()];
    letters = letter.toArray(letters);
    Database.disconnect();
    return letters;
  }

  /**
   * Returns all the points of the letters as array
   *
   * @return integer array containing all points saved in the database
   * 
   *         code line to convert list to array from:
   *         https://www.techiedelight.com/convert-list-integer-array-int/
   * @author lengist
   */
  public synchronized static int[] getAllPointsPerLetter() {
    Database.reconnect();
    ResultSet rs = null;
    Statement stm;
    ArrayList<Integer> point = new ArrayList<Integer>();
    try {
      stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Points FROM Letters");
      while (rs.next()) {
        point.add(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    int[] points = point.stream().mapToInt(Integer::intValue).toArray();
    Database.disconnect();
    return points;
  }

  /**
   * Returns all the Occurrences of the letters as array
   *
   * @return integer array containing all occurrences saved in the database
   * 
   *         code line to convert list to array from:
   *         https://www.techiedelight.com/convert-list-integer-array-int/
   * @author lengist
   */
  public synchronized static int[] getAllOccurrences() {
    Database.reconnect();
    ResultSet rs = null;
    Statement stm;
    ArrayList<Integer> occurrence = new ArrayList<Integer>();
    try {
      stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Occurrence FROM Letters");
      while (rs.next()) {
        occurrence.add(rs.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    int[] occurrences = occurrence.stream().mapToInt(Integer::intValue).toArray();
    Database.disconnect();
    return occurrences;
  }

  /**
   * Returns the server object to get the right server statistics when a user chooses to host his
   * server again.
   *
   * @param serverHostName String representation of the host of the server
   * @return Server object with the server statistic
   * @auhtor lengist
   * @author nitterhe
   */
  public synchronized static ServerStatistics getServerStatistics(String serverHostName) {
    Database.reconnect();
    try {
      Statement stm = connection.createStatement();
      ResultSet s = stm
          .executeQuery("SELECT * FROM Server WHERE (ServerHostName = '" + serverHostName + "');");
      ServerStatistics ss = new ServerStatistics();
      String client = "", IPAddress = "";
      int gamesPlayed, gamesWon;
      while (s.next()) {
        client = s.getString(2);
        gamesPlayed = s.getInt(3);
        gamesWon = s.getInt(4);
        IPAddress = s.getString(5);
        ss.loadClient(client, IPAddress, gamesPlayed, gamesWon);
      }
      Database.disconnect();
      return ss;
    } catch (SQLException e) {
      e.printStackTrace();
      Database.disconnect();
      return null;
    }
  }


  /**
   * Changes the points for the letter letter in the table letters when a user chooses to
   * individualize.
   *
   * @param letter String for the letter where a change needs to be fulfilled
   * @param point int for the new points
   * @author lengist
   */
  public synchronized static void setPointForLetter(String letter, int point) {
    FillDatabase.updatePointLetters(letter, point);
  }

  /**
   * Changes the occurrence for the letter letter in the table letters when a user chooses to
   * individualize.
   *
   * @param letter String for the letter where a change needs to be fulfilled
   * @param occurrence int for the new occurrence
   * @author lengist
   */
  public synchronized static void setOccurrenceLetters(String letter, int occurrence) {
    FillDatabase.updateOccurrenceLetters(letter, occurrence);
  }

  /**
   * Returns a boolean if the player "name" already exists.
   *
   * @param name String of the name of the user to use in the prepared Statement
   * @return boolean returning true if player with name "name" already exists
   * @author lengist
   */
  public synchronized static boolean playerExists(String name) {
    Database.reconnect();
    boolean exists = false;
    try {
      Statement test = connection.createStatement();
      ResultSet rs = test.executeQuery("SELECT Name FROM Player");
      while (rs.next()) {
        if (rs.getString("Name").equals(name)) {
          exists = true;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    Database.disconnect();
    return exists;
  }
}
