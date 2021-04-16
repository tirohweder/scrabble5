package com.scrab5.util.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author lengist
 * 
 *         entity to create a new database and establish the connection to it.
 *
 */
public class Database {

  protected static Connection connection;
  protected static boolean data = false;
  protected static String databaseFileName = "myDatabase.db";


  /**
   * @author lengist
   * 
   *         Constructor to create a new Database and call the method to establish a connection to
   *         the database file.
   */
  public Database() {
    this.connect(this.databaseFileName);
  }

  /**
   * @author lengist
   * 
   *         Establishes the connection to an existing database file.
   */
  public static void reconnect() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);
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
   * @return boolean
   * 
   *         Returns true if the local database file exists to check if a database already exists.
   */
  public static boolean databaseExistance() {
    return new File("myDatabase.db").isFile();
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
   * public static void main(String[] args) { Database newDb = new Database(); }
   */


}
