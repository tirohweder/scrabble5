package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lengist
 * 
 *         Class to get and set the data for the player statistics and leaderboards.
 *
 */
public class PlayerProfileDatabase extends Database {

  /**
   * @author lengist
   * @param name
   * @return String
   * 
   *         Returns the content in column Picture at Player name.
   */
  public static String getPicture(String name) {
    String picture = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT Picture FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      picture = rs.getString(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return picture;
  }

  /**
   * @author lengist
   * @param name
   * @return int
   * 
   *         Returns the content in column TotalPoints at Player name.
   */
  public static int getTotalPoints(String name) {
    int points = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT TotalPoints FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      points = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return points;
  }

  /**
   * @author lengist
   * @param name
   * @return int
   * 
   *         Returns the content in column PersonalHighscore at Player name.
   */
  public static int getPersonalHighscore(String name) {
    int highscore = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT PersonalHighscore FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      highscore = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return highscore;
  }


  /**
   * @author lengist
   * @param name
   * @return int
   * 
   *         Returns the content in column LaidWords at Player name.
   */
  public static int getLaidWords(String name) {
    int words = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT LaidWords FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      words = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return words;
  }

  /**
   * @author lengist
   * @param name
   * @return int
   * 
   *         Returns the content in column PointsPerWordRate at Player name.
   */
  public static int getPointsPerWordRate(String name) {
    int pPWord = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT PointsPerWordRate FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      pPWord = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return pPWord;
  }


  /**
   * @author lengist
   * @param name
   * @return String
   * 
   *         Returns the content in column LongestWord at Player name.
   */
  public static String getLongestWord(String name) {
    String word = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT LongestWord FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      word = rs.getString(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return word;
  }


  /**
   * @author lengist
   * @param name
   * @return int
   * 
   *         Returns the content in column TotalPlayedGames at Player name.
   */
  public static int getTotalPlayedGames(String name) {
    int games = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT TotalPlayedGames FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      games = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return games;
  }

  /**
   * @author lengist
   * @param name
   * @return int
   * 
   *         Returns the content in column TotalWins at Player name.
   */
  public static int getTotalWins(String name) {
    int wins = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT TotalWins FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      wins = rs.getInt(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return wins;
  }


  /**
   * @author lengist
   * @param name
   * @return Double
   * 
   *         Returns the content in column WinRate at Player name.
   */
  public static double getWinRate(String name) {
    double rate = 0.0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT WinRate FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      rate = rs.getDouble(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rate;
  }

  /**
   * @author lengist
   * @param name
   * @return String
   * 
   *         Returns the content in column FavoriteDictionary at Player name.
   */
  public static String getFavoriteDictionary(String name) {
    String dic = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT FavoriteDictionary FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      dic = rs.getString(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dic;
  }


  /**
   * @author lengist
   * @param name
   * @param newName
   * 
   *        Updates current value of Name with String value delivered by parameter.
   */
  public static void setName(String name, String newName) {
    FillDatabase.updatePlayer("Name", name, newName, 0, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param picture
   * 
   *        Updates current value of Picture with String value delivered by parameter.
   */
  public static void setPicture(String name, String picture) {
    FillDatabase.updatePlayer("Picture", name, picture, 0, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param points
   * 
   *        Updates current value of TotalPoints with int value delivered by parameter.
   */
  public static void setTotalPoints(String name, int points) {
    FillDatabase.updatePlayer("TotalPoints", name, null, points, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param highscore
   * 
   *        Updates current value of PersonalHighscore with int value delivered by parameter.
   */
  public static void setPersonalHighscore(String name, int highscore) {
    FillDatabase.updatePlayer("PersonalHighscore", name, null, highscore, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param words
   * 
   *        Updates current value of LaidWords with int value delivered by parameter.
   */
  public static void setLaidWords(String name, int words) {
    FillDatabase.updatePlayer("LaidWords", name, null, words, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param pPWord
   * 
   *        Updates current value of PointsPerWordRate with int value delivered by parameter.
   */
  public static void setPointsPerWordRate(String name, int pPWord) {
    FillDatabase.updatePlayer("PointsPerWordRate", name, null, pPWord, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param longestWord
   * 
   *        Updates current value of LongestWord with String value delivered by parameter.
   */
  public static void setLongestWord(String name, String longestWord) {
    FillDatabase.updatePlayer("LongestWord", name, longestWord, 0, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param games
   * 
   *        Updates current value of TotalPlayedGames with int value delivered by parameter.
   */
  public static void setTotalPlayedGames(String name, int games) {
    FillDatabase.updatePlayer("TotalPlayedGames", name, null, games, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param wins
   * 
   *        Updates current value of TotalWins with int value delivered by parameter.
   */
  public static void setTotalWins(String name, int wins) {
    FillDatabase.updatePlayer("TotalWins", name, null, wins, 0.0);
  }

  /**
   * @author lengist
   * @param name
   * @param rate
   * 
   *        Updates current value of WinRate with double value delivered by parameter.
   */
  public static void setWinRate(String name, double rate) {
    FillDatabase.updatePlayer("WinRate", name, null, 0, rate);
  }

  /**
   * @author lengist
   * @param name
   * @param language
   * 
   *        Updates current value of FavoriteDictionary with String value delivered by parameter.
   */
  public static void setFavoriteDictionary(String name, String language) {
    FillDatabase.updatePlayer("FavoriteDictionary", name, language, 0, 0.0);
  }



}
