package com.scrab5.util.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Entity to create a new database and establish the connection to it.
 *
 * @author lengist
 */
public class Database {

  protected static Connection connection;
  protected static boolean data = false;
  protected static String databaseFileName = "myDatabase.db";

  /**
   * Constructor to create a new Database and call the method to establish a connection to the
   * database file. Disconnect makes sure, that when another method gets called and reconnects to
   * the database, it is possible to reconnect.
   *
   * @author lengist
   */
  public Database() {
    this.connect(databaseFileName);
  }

  /**
   * Establishes the connection to an existing database file.
   *
   * @author lengist
   */
  public static void reconnect() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);
    } catch (ClassNotFoundException e) {
      System.out.println("Connection not possible" + e.getMessage());
    } catch (SQLException e1) {
      System.out.println("reconnect: Connection not possible" + e1.getMessage());
      System.out.println("Sql Exception: " + e1.getMessage());
      System.out.println("Sql State: " + e1.getSQLState());
      System.out.println("Sql Error: " + e1.getErrorCode());
      e1.printStackTrace();
    }
  }

  /**
   * Returns true if the local database file exists to check if a database already exists.
   *
   * @author lengist
   * @return boolean returning true if a database file already exists
   */
  public static boolean databaseExistance() {
    return new File(databaseFileName).isFile();
  }

  /**
   * Method to disconnect the connection to the database.
   *
   * @author lengist
   */
  public static void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Problem with closing connection: " + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Method to establish the connection to the database file whos name is given in the parameter
   * file.
   *
   * @author lengist
   * @param file the name of the database file
   */
  protected void connect(String file) {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + file);
    } catch (ClassNotFoundException e) {
      System.out.println("Connection not possible" + e.getMessage());
    } catch (SQLException e1) {
      System.out.println("connect: Connection not possible" + e1.getMessage());
      System.out.println("Sql Exception: " + e1.getMessage());
      System.out.println("Sql State: " + e1.getSQLState());
      System.out.println("Sql Error: " + e1.getErrorCode());
      e1.printStackTrace();
    }
  }

  /**
   * Method to delete the database file. It is used after the tests so the game and the test
   * database are separate.
   *
   * @author lengist
   */
  public void deleteDatabaseFile() {
    disconnect();
    File file = new File(
        System.getProperty("user.dir") + System.getProperty("file.separator") + databaseFileName);
    System.out.println(file.exists());
    file.delete();
    System.out.println("danach: " + file.exists());
  }

  /**
   * Method to re-establish the connection to the database file who's name is given in the
   * parameter. It is used for testing because it returns a boolean value if the reconnection was
   * successful. file.
   *
   * @author lengist
   */
  protected boolean reconnectTest() {
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFileName);
      return true;
    } catch (ClassNotFoundException e) {
      System.out.println("Connection not possible" + e.getMessage());
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    return false;
  }
}
