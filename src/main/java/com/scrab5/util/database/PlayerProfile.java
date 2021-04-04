package com.scrab5.util.database;

/* Charge all statistics */
public class PlayerProfile extends Database {
  private int totalPoints;
  private int laidWords;
  private int totalPlayedGames;
  private int totalWins;
  private String favoriteDictionary;

  public PlayerProfile(int totalPoints, int laidWords, int totalPlayedGames, int totalWins,
      String favoriteDictionary) {
    this.totalPoints = totalPoints;
    this.laidWords = laidWords;
    this.totalPlayedGames = totalPlayedGames;
    this.totalWins = totalWins;
    this.favoriteDictionary = favoriteDictionary;
  }

  /* getter methods */
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
  public void setTotalPoints(int points) {
    FillDatabase.fillPlayer(0, null, null, 0);
    this.totalPoints += points;
  }

  /* update current value with int value delivered by parameter */
  public void setLaidWords(int words) {
    FillDatabase.fillPlayer(0, null, null, 0);
    this.laidWords += words;
  }

  /* update current value with int value delivered by parameter */
  public void setTotalPlayedGames(int games) {
    FillDatabase.fillPlayer(0, null, null, 0);
    this.totalPlayedGames += games;
  }

  /* update current value with int value delivered by parameter */
  public void setTotalWins(int wins) {
    FillDatabase.fillPlayer(0, null, null, 0);
    this.totalWins += wins;
  }

  /* replace current value with string value delivered by parameter */
  public void setFavoriteDictionary(String language) {
    FillDatabase.fillPlayer(0, null, null, 0);
    this.favoriteDictionary = language;
  }

}
