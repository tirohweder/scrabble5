package com.scrab5.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/*
 * entity to establish a connection to the database
 * 
 * @author lengist
 * 
 */

public class Database {

  protected Properties properties;
  /* the jdbc connection object */
  protected static Connection connection;
  protected static Statement stm;

  /* Constructor to call from other classes */
  public Database() {
    connect();
  }

  /*
   * connects to mysql database localhost because for development, the server is local. scrabble5 is
   * the name of the local created database. root is the username and Scrabble52021 is the password
   * to connect to the mysql database.
   */
  protected void connect() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrabble5", "root",
          "Scrabble52021");
      System.out.print("connected");
      System.out.println();
    } catch (ClassNotFoundException e) {
      System.out.print("Connection not possible" + e.getMessage());
    } catch (SQLException se) {
      System.out.println("Sql Exception:" + se.getMessage());
      System.out.println("Sql State:" + se.getSQLState());
      System.out.println("Sql Error:" + se.getErrorCode());
      se.printStackTrace();
    }
  }

  /* disconnects database */
  protected void disconnect() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.out.println("Problem with closing connection" + e.getMessage());
    }
  }

  /* main method to test connection */
  /*
   * public static void main(String args[]) { // System.out.println(); }
   */

}
