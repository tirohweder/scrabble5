package com.scrab5.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
  private static void connect() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrabble5", "root",
          "Scrabble52021");
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
    // System.out.println();
    Database.connect();
    CreateDatabase db = new CreateDatabase();
    db.createTable();
    FillDatabase fdb = new FillDatabase();
    fdb.prepareStatements();
    fdb.createPlayer("Laura", "hallo");
    Statement stm;
    try {
      stm = connection.createStatement();
      ResultSet rs = stm.executeQuery("SELECT * FROM Player");
      /* empty ResultSet */
      while (rs.next()) {

        System.out.println("Name: " + rs.getString("Name") + ", ");
        System.out.println("Picture: " + rs.getString("Picture") + ", ");
        System.out.println("Total points: " + rs.getString("TotalPoints") + ", ");
        System.out.println("Personal highscore: " + rs.getString("PersonalHighscore") + ", ");
        System.out.println("Laid words: " + rs.getString("LaidWords") + ", ");
        System.out.println("Points per word rate: " + rs.getString("PointsPerWordRate") + ", ");
        System.out.println("Longest word: " + rs.getString("LongestWord") + ", ");
        System.out.println("Total played games: " + rs.getString("TotalPlayedGames") + ", ");
        System.out.println("Total wins: " + rs.getString("TotalWins") + ", ");
        System.out.println("win rate: " + rs.getString("WinRate") + ", ");
        System.out.println("Favorite dictionary: " + rs.getString("FaveDic") + ", ");
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
