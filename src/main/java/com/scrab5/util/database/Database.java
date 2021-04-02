package com.scrab5.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/* establish connection */

public class Database {

  protected Properties properties;
  /* the jdbc connection object */
  protected static Connection connection = null;

  /* connects to mysql database */
  private static void connect() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "Scrabble52021");
      System.out.print("connected");
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
  public static void main(String args[]) {
    System.out.println();
    Database.connect();
  }

}
