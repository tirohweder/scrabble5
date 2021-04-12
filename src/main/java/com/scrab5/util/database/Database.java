package com.scrab5.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * entity to create a new database and establish the connection to it
 * 
 * @author lengist
 * 
 */

public class Database {

  private static final Database db = new Database();
  protected static Connection connection;


  /**
   * @author lengist
   * 
   *         Constructor to create a new Database and call the method to establish a connection to
   *         the database file.
   */
  public Database() {
    this.connect(
        System.getProperty("user.dir") + "/src/main/resources/com/scrab5/database/myDatabase.db");
  }

  /**
   * @author lengist
   * @param file
   * 
   *        Method to establish the connection to the database file given in the parameter file.
   */
  protected void connect(String file) {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + file);
      System.out.println("connected");
    } catch (ClassNotFoundException e) {
      System.out.println("Connection not possible" + e.getMessage());
    } catch (SQLException e1) {
      System.out.println("Sql Exception: " + e1.getMessage());
      System.out.println("Sql State: " + e1.getSQLState());
      System.out.println("Sql Error: " + e1.getErrorCode());
      e1.printStackTrace();
    }
  }

  /**
   * @author lengist
   * 
   *         Method to disconnect from the database.
   */
  protected void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Problem with closing connection: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /*
   * for testing: public static void main(String[] args) { Database newDb = new Database(); }
   */

}
