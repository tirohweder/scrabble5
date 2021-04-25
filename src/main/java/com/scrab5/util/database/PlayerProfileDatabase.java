package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to get and set the data for the player statistics and leaderboards.
 * 
 * @author lengist
 */
public class PlayerProfileDatabase extends Database {

  /**
   * Returns the content in column Picture at Player name.
   * 
   * @author lengist
   * @param name
   * @return String
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
   * Returns the content in column TotalPoints at Player name.
   * 
   * @author lengist
   * @param name
   * @return int
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
   * Returns the content in column PersonalHighscore at Player name.
   * 
   * @author lengist
   * @param name
   * @return int
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
   * Returns the content in column LaidWords at Player name.
   * 
   * @author lengist
   * @param name
   * @return int
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
   * Returns the content in column PointsPerWordRate at Player name.
   * 
   * @author lengist
   * @param name
   * @return int
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
   * Returns the content in column LongestWord at Player name.
   * 
   * @author lengist
   * @param name
   * @return String
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
   * Returns the content in column TotalPlayedGames at Player name.
   * 
   * @author lengist
   * @param name
   * @return int
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
   * Returns the content in column TotalWins at Player name.
   * 
   * @author lengist
   * @param name
   * @return int
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
   * Returns the content in column WinRate at Player name.
   * 
   * @author lengist
   * @param name
   * @return Double
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
   * Returns the content in column FavoriteDictionary at Player name.
   * 
   * @author lengist
   * @param name
   * @return String
   */
  public static String getFavoriteDictionary(String name) {
    String dic = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT FaveDic FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      dic = rs.getString(1);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return dic;
  }


  /**
   * Updates current value of Name with String value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param newName
   */
  public static void setName(String name, String newName) {
    FillDatabase.updatePlayer("Name", name, newName, 0, 0.0);
  }

  /**
   * Updates current value of Picture with String value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param picture
   */
  public static void setPicture(String name, String picture) {
    FillDatabase.updatePlayer("Picture", name, picture, 0, 0.0);
  }

  /**
   * Updates current value of TotalPoints with int value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param points
   */
  public static void setTotalPoints(String name, int points) {
    FillDatabase.updatePlayer("TotalPoints", name, null, points, 0.0);
  }

  /**
   * Updates current value of PersonalHighscore with int value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param highscore
   */
  public static void setPersonalHighscore(String name, int highscore) {
    FillDatabase.updatePlayer("PersonalHighscore", name, null, highscore, 0.0);
  }

  /**
   * Updates current value of LaidWords with int value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param words
   */
  public static void setLaidWords(String name, int words) {
    FillDatabase.updatePlayer("LaidWords", name, null, words, 0.0);
  }

  /**
   * Updates current value of PointsPerWordRate with int value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param pPWord
   */
  public static void setPointsPerWordRate(String name, int pPWord) {
    FillDatabase.updatePlayer("PointsPerWordRate", name, null, pPWord, 0.0);
  }

  /**
   * Updates current value of LongestWord with String value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param longestWord
   */
  public static void setLongestWord(String name, String longestWord) {
    FillDatabase.updatePlayer("LongestWord", name, longestWord, 0, 0.0);
  }

  /**
   * Updates current value of TotalPlayedGames with int value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param games
   */
  public static void setTotalPlayedGames(String name, int games) {
    FillDatabase.updatePlayer("TotalPlayedGames", name, null, games, 0.0);
  }

  /**
   * Updates current value of TotalWins with int value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param wins
   */
  public static void setTotalWins(String name, int wins) {
    FillDatabase.updatePlayer("TotalWins", name, null, wins, 0.0);
  }

  /**
   * Updates current value of WinRate with double value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param rate
   */
  public static void setWinRate(String name, double rate) {
    FillDatabase.updatePlayer("WinRate", name, null, 0, rate);
  }

  /**
   * Updates current value of FavoriteDictionary with String value delivered by parameter.
   * 
   * @author lengist
   * @param name
   * @param language
   */
  public static void setFavoriteDictionary(String name, String language) {
    FillDatabase.updatePlayer("FaveDic", name, language, 0, 0.0);
  }



}
