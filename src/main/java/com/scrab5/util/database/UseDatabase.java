package com.scrab5.util.database;

import com.scrab5.network.ServerStatistics;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Note: To save the database and make sure that not two clients at the same time are able to make a
 * request to the database file, the connection gets established and disconnected in every method
 * individual where it is necessary.
 *
 * @author lengist
 */
public class UseDatabase extends Database {

  /**
   * Displaying the content of current table Letters.
   *
   * @author lengist
   * @return ResultSet including the content from the table letters
   */
  public static synchronized ResultSet viewLetters() {
    ResultSet rs = null;
    Database.reconnect();
    try {
      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT * FROM Letters");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }

  /**
   * Checks whether the table Player is empty or with entries.
   *
   * @author lengist
   * @return boolean returning true if there is no entry in the table player
   */
  public static synchronized boolean tablePlayerIsEmpty() {
    Database.reconnect();
    boolean empty = false;
    int anzahl = 0;
    ResultSet rs = null;
    try {
      Statement stm = connection.createStatement();
      String sql = "SELECT COUNT(*) FROM Player";
      rs = stm.executeQuery(sql);
      anzahl = rs.getInt(1);
      if (anzahl == 0) {
        empty = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return empty;
  }

  /**
   * Returns all player names from the table Player.
   *
   * @author lengist
   * @return ResultSet returning the content of the table player
   */
  public static synchronized ResultSet getAllPlayerRs() {
    Database.reconnect();
    ResultSet rs = null;
    try {

      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Name FROM Player");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }

  /**
   * Returns a ObservableList so all player profiles can be displayed in a ComboBox.
   *
   * @author mherre
   * @author lengist
   * @return ObservableList including the content of the table player
   */
  public static synchronized ObservableList<String> getAllPlayer() {
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
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return ol;
  }

  /**
   * Returns all the letters as array.
   *
   * @author lengist
   * @return String array containing all letters saved in the database
   */
  public static synchronized String[] getAllLetters() {
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
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return letters;
  }

  /**
   * Returns all the points of the letters as array.
   *
   * @author lengist
   * @return integer array containing all points saved in the database
   *     <p>code line to convert list to array from:
   *     https://www.techiedelight.com/convert-list-integer-array-int/
   */
  public static synchronized int[] getAllPointsPerLetter() {
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
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return points;
  }

  /**
   * Returns all the Occurrences of the letters as array.
   *
   * @return integer array containing all occurrences saved in the database
   *     <p>code line to convert list to array from:
   *     https://www.techiedelight.com/convert-list-integer-array-int/
   * @author lengist
   */
  public static synchronized int[] getAllOccurrences() {
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
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return occurrences;
  }

  /**
   * Returns the points for a letter saved in the table Letters to calculate the points for a laid
   * word.
   *
   * @author lengist
   * @param letter representing the letter for which the points need to be known
   * @return int value of the points for the letter
   */
  public static synchronized int getPointForLetter(String letter) {
    Database.reconnect();
    ResultSet rs = null;
    Statement stm;
    ArrayList<Integer> point = new ArrayList<Integer>();
    try {
      stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Points FROM Letters WHERE (Letter = " + letter + ");");

      while (rs.next()) {
        point.add(rs.getInt(1));
        return rs.getInt(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
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
  public static synchronized ServerStatistics getServerStatistics(String serverHostName) {
    Database.reconnect();
    ResultSet s = null;
    try {
      Statement stm = connection.createStatement();
      s =
          stm.executeQuery(
              "SELECT * FROM Server WHERE (ServerHostName = '" + serverHostName + "');");
      ServerStatistics ss = new ServerStatistics();
      String client = "";
      String ipAddress = "";
      int gamesPlayed;
      int gamesWon;
      while (s.next()) {
        client = s.getString(2);
        gamesPlayed = s.getInt(3);
        gamesWon = s.getInt(4);
        ipAddress = s.getString(5);
        ss.loadClient(client, ipAddress, gamesPlayed, gamesWon);
      }
      Database.disconnect();
      s.close();
      return ss;
    } catch (SQLException e) {
      e.printStackTrace();
      try {
        s.close();
      } catch (SQLException e1) {
        e1.printStackTrace();
      }
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
  public static synchronized void setPointForLetter(String letter, int point) {
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
  public static synchronized void setOccurrenceLetters(String letter, int occurrence) {
    FillDatabase.updateOccurrenceLetters(letter, occurrence);
  }

  /**
   * Updates the values in the table Letters after a user customized it.
   *
   * @author lengist
   * @param points a ArrayList for all the points that need to be saved.
   * @param occurrences a ArrayList for al the occurrences that need to be saved.
   */
  public static synchronized void updateLetterCustomization(
      ArrayList<Integer> points, ArrayList<Integer> occurrences) {
    Database.reconnect();
    FillDatabase.deleteTable("Letters");
    String[] letter = {
      "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
      "T", "U", "V", "W", "X", "Y", "Z", "*"
    };
    int[] point = new int[points.size()];
    int count = 0;
    for (int n : points) {
      point[count] = n;
      count++;
    }

    int count2 = 0;
    int[] occurrence = new int[occurrences.size()];
    for (int n : occurrences) {
      occurrence[count2] = n;
      count2++;
    }

    for (int i = 0; i < letter.length; i++) {
      FillDatabase.insertLetters(letter[i], point[i], occurrence[i]);
    }
  }

  /**
   * Returns a boolean if the player "name" already exists.
   *
   * @param name String of the name of the user to use in the prepared Statement
   * @return boolean returning true if player with name "name" already exists
   * @author lengist
   */
  public static synchronized boolean playerExists(String name) {
    Database.reconnect();
    boolean exists = false;
    ResultSet rs = null;
    try {
      Statement test = connection.createStatement();
      rs = test.executeQuery("SELECT Name FROM Player");
      while (rs.next()) {
        if (rs.getString("Name").equals(name)) {
          exists = true;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    try {
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return exists;
  }
}
