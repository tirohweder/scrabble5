package com.scrab5.core.player;

import com.scrab5.ui.Data;
import com.scrab5.util.database.Database;
import com.scrab5.util.database.PlayerProfileDatabase;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author lengist
 * @author trohwede
 *
 */
public class PlayerProfile implements Serializable {

  private static final long serialVersionUID = 1L;

  private static String name = Data.getCurrentUser();
  private int currentPersonalHighscore;
  private int currentWordCount;
  // private int currentPointsPerWordRate;
  private String currentLongestWord;
  private int currentGamesCount;
  private int currentWinCount;
  private double currentWinRate;
  private String currentDictionary;
  private int currentPoints;

  /**
   * Returns the name of the current user.
   *
   * @return String representation of the name of the current user
   * @author lengist
   */
  public static String getName() {
    return name;
  }

  public void setCurrentPoints(int newPoints) {
    currentPoints = newPoints;
  }

  /**
   * Adds the achieved points to the current points of the user "name" in the database.
   *
   * @param points the user achieved in the played game
   * @author lengist
   */
  public void addPoints(String userName, int points) {
    Database.reconnect();
    int current = PlayerProfileDatabase.getTotalPoints(name);
    PlayerProfileDatabase.setTotalPoints(userName, points + current);
  }

  public void setCurrentPersonalHighscore(int newHighscore) {
    currentPersonalHighscore = newHighscore;
  }

  /**
   * Changes the personal highscore of the player, if the new highscore is greater than the saved
   * one.
   *
   * @param lastPersonalHighscore the user achieved in the played game
   * @author lengist
   */
  public void adjustPersonalHighscore(int lastPersonalHighscore) {
    PlayerProfileDatabase.setPersonalHighscore(name, lastPersonalHighscore);
  }

  public void setCurrentWordCount(int wordCount) {
    currentWordCount = wordCount;
  }

  /**
   * Adds the laid words to the column laidWords of the user "name" in the database.
   *
   * @param words the user laid in the played game
   * @author lengist
   */
  public void addWords(int words) {
    int currentWords = PlayerProfileDatabase.getLaidWords(name);
    int newWords = currentWords + words;
    PlayerProfileDatabase.setLaidWords(name, newWords);
    // setCurrentWordCount(newWords);
  }

  /*
   * public void setCurrentPointsPerWordRate(int wordRate) { currentPointsPerWordRate = wordRate; }
   */

  /**
   * Changes the points per word rate, if the new rate is greater than the one saved in the
   * database.
   *
   * @param newPointsPerWordRate the user achieved in the played game
   * @author lengist
   */
  public void adjustPointsPerWordRate(int newPointsPerWordRate) {
    int currentPointsPerWordRate = PlayerProfileDatabase.getPointsPerWordRate(name);
    if (newPointsPerWordRate > currentPointsPerWordRate) {
      PlayerProfileDatabase.setPointsPerWordRate(name, newPointsPerWordRate);
      // setCurrentPointsPerWordRate(newPointsPerWordRate);
    }
  }

  public void setCurrentLongestWord(String word) {
    currentLongestWord = word;
  }

  /**
   * Changes the longest word, if the player has laid a word in the current game, that is longer
   * than the one saved.
   *
   * @param longestWord the user laid in the played game
   * @author lengist
   */
  public void adjustLongestWord(String longestWord) {
    String currentLongestW = PlayerProfileDatabase.getLongestWord(name);
    if (longestWord.length() > currentLongestW.length()) {
      PlayerProfileDatabase.setLongestWord(name, longestWord);
      // setCurrentLongestWord(longestWord);
    }
  }

  public void setCurrentGamesCount(int count) {
    currentGamesCount = count;
  }

  /**
   * Adds the quantity of played games of the user "name" to the database.
   *
   * @param games the amount of games the user played since the last lock
   * @author lengist
   */
  public void addGames(int games) {
    int currentGames = PlayerProfileDatabase.getTotalPlayedGames(name);
    int newGames = currentGames + games;
    PlayerProfileDatabase.setTotalPlayedGames(name, newGames);
    // setCurrentGamesCount(newGames);
  }

  public void setCurrentWinCount(int count) {
    currentWinCount = count;
  }

  /**
   * Adds the achieved wins of the user "name" to the database.
   *
   * @param wins the amount of wins the user achieved since the last lock
   * @author lengist
   */
  public void addWins(int wins) {
    int currentWins = PlayerProfileDatabase.getTotalWins(name);
    int newWins = currentWins + wins;
    PlayerProfileDatabase.setTotalWins(name, newWins);
    // setCurrentWinCount(newWins);
  }

  public void setCurrentWinRate(double rate) {
    currentWinRate = rate;
  }

  /**
   * Adjusts the win rate stored for the player.
   *
   * @param winRate the user achieved in the played game
   * @author lengist
   */
  public void adjustWinRate(double winRate) {
    double currentWRate = PlayerProfileDatabase.getWinRate(name);
    if (winRate > currentWRate) {
      PlayerProfileDatabase.setWinRate(name, winRate);
      // setCurrentWinRate(winRate);
    }
  }

  public void setCurrentDictionary(String dictionary) {
    currentDictionary = dictionary;
  }

  /**
   * Changes the favorite dictionary of the user "name" in the database.
   *
   * @param dictionary the user selected in the played game
   * @author lengist
   */
  public void changeDictionary(String dictionary) throws IOException {
    PlayerProfileDatabase.setFavoriteDictionary(name, dictionary);
    setCurrentDictionary(dictionary);
  }

  /**
   * Returns the total points saved in the database for player "name".
   *
   * @return int the total points saved in the database for the user name
   * @author lengist
   */
  public int getTotalPoints() {
    return PlayerProfileDatabase.getTotalPoints(name);
  }

  /**
   * Returns the personal highscore in the database for player name.
   *
   * @return int the saved personal highscore from table Player
   * @author lengist
   */
  public int getPersonalHighscore() {
    return PlayerProfileDatabase.getPersonalHighscore(name);

  }

  /**
   * Returns the number of laid words saved in the database for the current player profile.
   *
   * @return int the laid words saved in the database for the user name
   * @author lengist
   */
  public int getLaidWords() {
    return PlayerProfileDatabase.getLaidWords(name);
  }

  /**
   * Returns the points per word rate in the database for player name.
   *
   * @return double the saved points per word rate from table Player
   * @author lengist
   */
  public double getPointsPerWordRate() {
    return PlayerProfileDatabase.getPointsPerWordRate(name);
  }

  /**
   * Returns the longest word in the database for the current player name.
   *
   * @return String the saved longest word from table Player
   * @author lengist
   */
  public String getLongestWord() {
    return PlayerProfileDatabase.getLongestWord(name);
  }

  /**
   * Returns the total played games saved in the database for player "name".
   *
   * @return int the total played games saved in the database for the user name
   * @author lengist
   */
  public int getTotalPlayedGames() {
    return PlayerProfileDatabase.getTotalPlayedGames(name);
  }

  /**
   * Returns the number of total wins saved in the database for player "name".
   *
   * @return int the total wins saved in the database for the user name
   * @author lengist
   */
  public int getTotalWins() {
    return PlayerProfileDatabase.getTotalWins(name);
  }

  /**
   * Returns the win rate in the database for the current player name
   *
   * @return double the saved win rate from table Player
   * @author lengist
   */
  public double getWinRate() {
    return PlayerProfileDatabase.getWinRate(name);
  }

  /**
   * Returns the favorite dictionary saved in the database for player "name".
   *
   * @return String the favorite dictionary saved in the database for the user name
   * @author lengist
   */
  public String getFavoriteDictionary() {
    return PlayerProfileDatabase.getFavoriteDictionary(name);
  }
}
