package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to get and set the data for the player statistics and leader boards.
 * 
 * @author lengist
 */
public class PlayerProfileDatabase extends Database {

  /**
   * Returns the content in column Picture at Player name.
   * 
   * @author lengist.
   * @param name String name of the user to insert into preparedStatement
   * @return String with path to picture
   */
  public static synchronized String getPicture(String name) {
    Database.disconnect();
    Database.reconnect();
    String picture = null;
    ResultSet rs = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT Picture FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      rs = pstm.executeQuery();
      while (rs.next()) {
        picture = rs.getString(1);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    //Database.disconnect();
    return picture;
  }

  /**
   * Returns the content in column TotalPoints at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return int Integer value of the total points from user "name" in the database
   */
  public static synchronized int getTotalPoints(String name) {
    Database.reconnect();
    int points = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT TotalPoints FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        points = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return points;
  }

  /**
   * Returns the content in column PersonalHighscore at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return int Integer value of the personal highscore stored in the database
   */
  public static synchronized int getPersonalHighscore(String name) {
    Database.reconnect();
    int highscore = 0;
    ResultSet rs = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT PersonalHighscore FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      rs = pstm.executeQuery();
      while (rs.next()) {
        highscore = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        rs.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    Database.disconnect();
    return highscore;
  }


  /**
   * Returns the content in column LaidWords at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return int Integer value of the count of laid words stored in the database
   */
  public static synchronized int getLaidWords(String name) {
    Database.reconnect();
    int words = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT LaidWords FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        words = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return words;
  }

  /**
   * Returns the content in column PointsPerWordRate at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return pperWord Integer value of the points per word rate stored in the database
   */
  public static synchronized int getPointsPerWordRate(String name) {
    Database.reconnect();
    int pperWord = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT PointsPerWordRate FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        pperWord = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return pperWord;
  }


  /**
   * Returns the content in column LongestWord at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return String with the longest word stored in the database
   */
  public static synchronized String getLongestWord(String name) {
    Database.reconnect();
    String word = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT LongestWord FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        word = rs.getString(1);
      }
      
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return word;
  }


  /**
   * Returns the content in column TotalPlayedGames at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return int Integer value of the total played games stored in the database
   */
  public static synchronized int getTotalPlayedGames(String name) {
    Database.reconnect();
    int games = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT TotalPlayedGames FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        games = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return games;
  }

  /**
   * Returns the content in column TotalWins at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return int Integer value of the total wins stored in the database
   */
  public static synchronized int getTotalWins(String name) {
    Database.reconnect();
    int wins = 0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT TotalWins FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        wins = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return wins;
  }


  /**
   * Returns the content in column WinRate at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return Double value of the win rate stored in the database
   */
  public static synchronized double getWinRate(String name) {
    Database.reconnect();
    double rate = 0.0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT WinRate FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        rate = rs.getDouble(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return rate;
  }

  /**
   * Returns the content in column FavoriteDictionary at Player name.
   * 
   * @author lengist
   * @param name String name of the user to insert into preparedStatement
   * @return String representing the favorite dictionary of the player name stored in the database
   */
  public static synchronized String getFavoriteDictionary(String name) {
    Database.reconnect();
    String dic = null;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT FaveDic FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        dic = rs.getString(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    Database.disconnect();
    return dic;
  }

  /**
   * Returns the current value of the music volume for player "name".
   * 
   * @author lengits
   * @param name String name of the user where the music volume needs to be returned
   * @return double value of the music volume
   */
  public static synchronized double getMusicVolume(String name) {
    Database.reconnect();
    double music = 0.0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT Music FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        music = rs.getDouble(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("music geholt");
    Database.disconnect();
    return music;
  }

  /**
   * Returns the current value of the sound effect volume for player "name".
   * 
   * @author lengist
   * @param name String name of the user where the sound effect volume needs to be returned
   * @return double value of the sound effect volume
   */
  public static synchronized double getSoundEffectVolume(String name) {
    Database.reconnect();
    double soundEffect = 0.0;
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT SoundEffect FROM Player WHERE Name = ?");
      pstm.setString(1, name);
      ResultSet rs = pstm.executeQuery();
      while (rs.next()) {
        soundEffect = rs.getDouble(1);
      }
      rs.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println("sound effect geholt");
    Database.disconnect();
    return soundEffect;
  }

  /**
   * Updates current value of Name with String value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the name needs to be set
   * @param newName String representing the new name to be stored in the database
   */
  public static void setName(String name, String newName) {
    FillDatabase.updatePlayer("Name", name, newName, 0, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of Picture with String value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the picture needs to be set
   * @param picture String representing the new picture path to be stored in the database
   */
  public static void setPicture(String name, String picture) {
    FillDatabase.updatePlayer("Picture", name, picture, 0, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of TotalPoints with int value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the points needs to be set
   * @param points Integer representing the new points to be stored in the database
   */
  public static void setTotalPoints(String name, int points) {
    FillDatabase.updatePlayer("TotalPoints", name, null, points, 0.0);
  }

  /**
   * Updates current value of PersonalHighscore with int value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the highscore needs to be set
   * @param highscore Integer representing the new highscore to be stored in the database
   */
  public static void setPersonalHighscore(String name, int highscore) {
    FillDatabase.updatePlayer("PersonalHighscore", name, null, highscore, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of LaidWords with int value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the laid words needs to be set
   * @param words Integer representing the new count for laid words to be stored in the database
   */
  public static void setLaidWords(String name, int words) {
    FillDatabase.updatePlayer("LaidWords", name, null, words, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of PointsPerWordRate with int value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the points per word rate needs to be set
   * @param pperWord Integer representing the new count for points per word rate to be stored in the
   *        database
   */
  public static void setPointsPerWordRate(String name, int pperWord) {
    FillDatabase.updatePlayer("PointsPerWordRate", name, null, pperWord, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of LongestWord with String value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the longest word needs to be set
   * @param longestWord String representing the new longest word to be stored in the database
   */
  public static void setLongestWord(String name, String longestWord) {
    FillDatabase.updatePlayer("LongestWord", name, longestWord, 0, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of TotalPlayedGames with int value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the total played games need to be set
   * @param games Integer representing the new count for played games to be stored in the database
   */
  public static void setTotalPlayedGames(String name, int games) {
    FillDatabase.updatePlayer("TotalPlayedGames", name, null, games, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of TotalWins with int value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the total wins need to be set
   * @param wins Integer representing the new count for total wins to be stored in the database
   */
  public static void setTotalWins(String name, int wins) {
    FillDatabase.updatePlayer("TotalWins", name, null, wins, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of WinRate with double value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the win rate needs to be set
   * @param rate Integer representing the new count for win rate to be stored in the database
   */
  public static void setWinRate(String name, double rate) {
    FillDatabase.updatePlayer("WinRate", name, null, 0, rate);
    Database.disconnect();
  }

  /**
   * Updates current value of FavoriteDictionary with String value delivered by parameter.
   * 
   * @author lengist
   * @param name String name of the user where the favorite dictionary needs to be set
   * @param language String representing the new favorite dictionary to be stored in the database
   */
  public static void setFavoriteDictionary(String name, String language) {
    FillDatabase.updatePlayer("FavDic", name, language, 0, 0.0);
    Database.disconnect();
  }

  /**
   * Updates current value of music volume for player "name".
   * 
   * @author lengist
   * @param name String name of the user where the music volume needs to be set
   * @param music double value of the music volume
   */
  public static void setMusicVolume(String name, Double music) {
    Database.reconnect();
    FillDatabase.updatePlayer("Music", name, null, 0, music);
    Database.disconnect();
    System.out.println("VolumeFertig");
  }

  /**
   * Updates current value of sound effect volume for player "name".
   * 
   * @author lengist
   * @param name String name of the user where the sound effect volume needs to be set
   * @param soundEffect double value of the sound effect volume
   */
  public static void setSoundEffectVolume(String name, Double soundEffect) {
    FillDatabase.updatePlayer("SoundEffect", name, null, 0, soundEffect);
    Database.disconnect();
    System.out.println("EffectFertig");
  }
}
