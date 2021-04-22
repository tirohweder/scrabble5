package com.scrab5.util.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UseDatabase extends Database {

  /**
   * @author lengist
   * @return ResultSet
   * 
   *         Displaying the content of current table Letters.
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
   * @author lengist
   * @return boolean
   * 
   *         checks whether the table Player is empty or with entries.
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
   * @return ResultSet
   */
  public static ResultSet getAllPlayer() {
    ResultSet rs = null;
    try {
      Statement stm = connection.createStatement();
      rs = stm.executeQuery("SELECT Name FROM Player");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }

}
