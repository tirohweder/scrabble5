package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * class to get and set the data for the player statistics and leaderboards
 * 
 * @author lengist
 * 
 */
/* Charge all statistics */
public class PlayerProfileDatabase extends Database {
  private String name;
  private String picture;
  private int totalPoints;
  private int laidWords;
  private int totalPlayedGames;
  private int totalWins;
  private String favoriteDictionary;

  /* getter methods */

  public String getPicture(String name) {
    try {
      PreparedStatement pstm =
          connection.prepareStatement("SELECT Picture FROM Player WHERE Name = ?");
      pstm.setString(1, name);

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return this.picture;
  }

  public int getTotalPoints(String name) {
    return this.totalPoints;
  }

  public int getLaidWords(String name) {
    return this.laidWords;
  }

  public int getTotalPlayedGames(String name) {
    return this.totalPlayedGames;
  }

  public int getTotalWins(String name) {
    return this.totalWins;
  }

  public String getFavoriteDictionary(String name) {
    return this.favoriteDictionary;
  }

  /*
   * setter methods with statements for database for now default values --> waiting for fill methods
   * to complete setter methods
   */
  /* update current value with int value delivered by parameter */
  public void setName(String name) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.name = name;
  }

  public void setPicture(String picture) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.picture = picture;
  }

  public void setLaidWords(int words) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.laidWords += words;
  }

  /* update current value with int value delivered by parameter */
  public void setTotalPoints(int points) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.totalPoints += points;
  }

  /* update current value with int value delivered by parameter */
  public void setTotalPlayedGames(int games) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.totalPlayedGames += games;
  }

  /* update current value with int value delivered by parameter */
  public void setTotalWins(int wins) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.totalWins += wins;
  }

  /* replace current value with string value delivered by parameter */
  public void setFavoriteDictionary(String language) {
    FillDatabase.updatePlayer(null, this.name, null, 0);
    this.favoriteDictionary = language;
  }

}
