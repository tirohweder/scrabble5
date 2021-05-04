package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UseDatabase extends Database {

  /**
   * Displaying the content of current table Letters.
   * 
   * @author lengist
   * @return ResultSet including the content from the table letters
   */
  public static ResultSet viewLetters() {
    ResultSet rs = null;
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
  public static boolean tablePlayerIsEmpty() {
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
    return empty;
  }

  /**
   * Returns all player names from the table Player.
   * 
   * @author lengist
   * @return ResultSet returning the content of the table player
   */
  public static ResultSet getAllPlayerRS() {
    ResultSet rs = null;
    try {

      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Name FROM Player");

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }

  /**
   * Returns a ObservableList so all player profiles can be displayed in a ComboBox
   * 
   * @author mherre
   * @return ObservableList including the content of the table player
   */
  public static ObservableList<String> getAllPlayer() {
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
    return ol;
  }

  /**
   * Returns all the letters as array
   * 
   * @author lengist
   * @return String array containing all letters saved in the database
   */
  public static String[] getAllLetter() {
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
    return letters;
  }

  /**
   * Returns all the points of the letters as array
   * 
   * @author lengist
   * @return integer array containing all points saved in the database
   * 
   *         code line to convert list to array from:
   *         https://www.techiedelight.com/convert-list-integer-array-int/
   */
  public static int[] getAllPointsPerLetter() {
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
    return points;
  }

  /**
   * Returns all the Occurrences of the letters as array
   * 
   * @author lengist
   * @return integer array containing all occurrences saved in the database
   * 
   *         code line to convert list to array from:
   *         https://www.techiedelight.com/convert-list-integer-array-int/
   */
  public static int[] getAllOccurrences() {
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
    return occurrences;
  }

  /**
   * Returns a boolean if the player "name" already exists.
   * 
   * @author lengist
   * @param name String of the name of the user to use in the prepared Statement
   * @return boolean returning true if player with name "name" already exists
   */
  public static boolean playerExists(String name) {
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
    return exists;
  }

  /**
   * Returns a boolean if the server "name" already exists.
   * 
   * @author lengist
   * @param name String of the name of the user to use in the prepared Statement
   * @return boolean returning true if a server with name "name" already exists in the table server
   */
  public static boolean serverExists(String name) {
    boolean exists = false;
    try {
      Statement test = connection.createStatement();
      ResultSet rs = test.executeQuery("SELECT ServerListNames FROM Server");
      while (rs.next()) {
        if (rs.getString("ServerListNames").equals(name)) {
          exists = true;
        }
      }
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return exists;
  }
}
