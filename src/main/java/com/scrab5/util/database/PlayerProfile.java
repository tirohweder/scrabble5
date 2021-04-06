package com.scrab5.util.database;

/*
 * class to get and set the data for the player statistics and leaderboards
 * 
 * @author lengist
 * 
 */
/* Charge all statistics */
public class PlayerProfile extends Database {
  private String name;
  private String picture;
  private int totalPoints;
  private int laidWords;
  private int totalPlayedGames;
  private int totalWins;
  private String favoriteDictionary;

  public PlayerProfile(String name, String picture, int totalPoints, int laidWords,
      int totalPlayedGames, int totalWins, String favoriteDictionary) {
    this.name = name;
    this.picture = picture;
    this.totalPoints = totalPoints;
    this.laidWords = laidWords;
    this.laidWords = laidWords;
    this.totalPlayedGames = totalPlayedGames;
    this.totalWins = totalWins;
    this.favoriteDictionary = favoriteDictionary;
  }

  /* getter methods */
  public String getName() {
    return this.name;
  }

  public String getPicture() {
    return this.picture;
  }

  public int getTotalPoints() {
    return this.totalPoints;
  }

  public int getLaidWords() {
    return this.laidWords;
  }

  public int getTotalPlayedGames() {
    return this.totalPlayedGames;
  }

  public int getTotalWins() {
    return this.totalWins;
  }

  public String getFavoriteDictionary() {
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
