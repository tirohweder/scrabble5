package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
