package com.scrab5.core.player;

import com.scrab5.ui.Data;
import com.scrab5.util.database.PlayerProfileDatabase;
import java.io.IOException;
import java.io.Serializable;

/** @author trohwede */
public class PlayerProfile implements Serializable {

  private static final long serialVersionUID = 1L;

  private static String name = Data.getCurrentUser();
  private static String picture;
  /*
   * private static int totalPoints; private static int laidWords; private static int
   * totalPlayedGames; private static int totalWins; private static String favoriteDictionary;
   */

  /**
   * Returns the name of the current user.
   *
   * @return String representation of the name of the current user
   * @author lengist
   */
  public static String getName() {
    return name;
  }

  /**
   * Return the picture of the current user if added.
   *
   * @return String representation of the picture of the current user
   * @author lengist
   */
  public static String getPicture() {
    picture = PlayerProfileDatabase.getPicture(name);
    return picture;
  }

  /**
   * Changes the personal highscore of the player, if the new highscore is greater than the saved
   * one.
   *
   * @param lastPersonalHighscore the user achieved in the played game
   * @author lengist
   */
  public static void adjustPersonalHighscore(int lastPersonalHighscore) {
    int savedPersonalHighscore = PlayerProfileDatabase.getPersonalHighscore(name);
    if (lastPersonalHighscore > savedPersonalHighscore) {
      PlayerProfileDatabase.setPersonalHighscore(name, lastPersonalHighscore);
    }
  }

  /**
   * Adds the laid words to the column laidWords of the user "name" in the database.
   *
   * @param words the user laid in the played game
   * @author lengist
   */
  public static void addWords(int words) throws IOException {
    int currentWords = PlayerProfileDatabase.getLaidWords(name);
    int newWords = currentWords + words;
    PlayerProfileDatabase.setLaidWords(name, newWords);
  }

  /**
   * Changes the points per word rate, if the new rate is greater than the one saved in the
   * database.
   *
   * @param newPointsPerWordRate the user achieved in the played game
   * @author lengist
   */
  public static void adjustPointsPerWordRate(int newPointsPerWordRate) {
    int currentPointsPerWordRate = PlayerProfileDatabase.getPointsPerWordRate(name);
    if (newPointsPerWordRate > currentPointsPerWordRate) {
      PlayerProfileDatabase.setPointsPerWordRate(name, newPointsPerWordRate);
    }
  }

  /**
   * Changes the longest word, if the player has laid a word in the current game, that is longer
   * than the one saved.
   *
   * @param longestWord the user laid in the played game
   * @author lengist
   */
  public static void adjustLongestWord(String longestWord) {
    String currentLongestWord = PlayerProfileDatabase.getLongestWord(name);
    if (longestWord.length() > currentLongestWord.length()) {
      PlayerProfileDatabase.setLongestWord(name, longestWord);
    }
  }

  /**
   * Adds the quantity of played games of the user "name" to the database.
   *
   * @param games the amount of games the user played since the last lock
   * @author lengist
   */
  public static void addGames(int games) throws IOException {
    int currentGames = PlayerProfileDatabase.getTotalPlayedGames(name);
    int newGames = currentGames + games;
    PlayerProfileDatabase.setTotalPlayedGames(name, newGames);
  }

  /**
   * Adds the achieved wins of the user "name" to the database.
   *
   * @param wins the amount of wins the user achieved since the last lock
   * @author lengist
   */
  public static void addWins(int wins) throws IOException {
    int currentWins = PlayerProfileDatabase.getTotalWins(name);
    int newWins = currentWins + wins;
    PlayerProfileDatabase.setTotalWins(name, newWins);
  }

  /**
   * Adjusts the win rate stored for the player.
   *
   * @param winRate the user achieved in the played game
   * @author lengist
   */
  public static void adjustWinRate(double winRate) {
    double currentWinRate = PlayerProfileDatabase.getWinRate(name);
    if (winRate > currentWinRate) {
      PlayerProfileDatabase.setWinRate(name, winRate);
    }
  }

  /**
   * Changes the favorite dictionary of the user "name" in the database.
   *
   * @param dictionary the user selected in the played game
   * @author lengist
   */
  public static void changeDictionary(String dictionary) throws IOException {
    PlayerProfileDatabase.setFavoriteDictionary(name, dictionary);
  }

  /**
   * Returns the total points saved in the database for player "name".
   *
   * @return int the total points saved in the database for the user name
   * @author lengist
   */
  public static int getTotalPoints() {
    return PlayerProfileDatabase.getTotalPoints(name);
  }

  /**
   * Returns the personal highscore in the database for player name
   *
   * @return int the saved personal highscore from table Player
   * @author lengist
   */
  public static int getPersonalHighscore() {
    return PlayerProfileDatabase.getPersonalHighscore(name);
  }

  /**
   * Returns the number of laid words saved in the database for player "name".
   *
   * @return int the laid words saved in the database for the user name
   * @author lengist
   */
  public static int getLaidWords() {
    return PlayerProfileDatabase.getLaidWords(name);
  }

  /**
   * Returns the points per word rate in the database for player name
   *
   * @return double the saved points per word rate from table Player
   * @author lengist
   */
  public static double getPointsPerWordRate() {
    return PlayerProfileDatabase.getPointsPerWordRate(name);
  }

  /**
   * Returns the longest word in the database for the current player name
   *
   * @return String the saved longest word from table Player
   * @author lengist
   */
  public static String getLongestWord() {
    return PlayerProfileDatabase.getLongestWord(name);
  }

  /**
   * Returns the total played games saved in the database for player "name".
   *
   * @return int the total played games saved in the database for the user name
   * @author lengist
   */
  public static int getTotalPlayedGames() {
    return PlayerProfileDatabase.getTotalPlayedGames(name);
  }

  /**
   * Returns the number of total wins saved in the database for player "name".
   *
   * @return int the total wins saved in the database for the user name
   * @author lengist
   */
  public static int getTotalWins() {
    return PlayerProfileDatabase.getTotalWins(name);
  }

  /**
   * Returns the win rate in the database for the current player name
   *
   * @return double the saved win rate from table Player
   * @author lengist
   */
  public static double getWinRate() {
    return PlayerProfileDatabase.getWinRate(name);
  }

  /**
   * Returns the favorite dictionary saved in the database for player "name".
   *
   * @return String the favorite dictionary saved in the database for the user name
   * @author lengist
   */
  public static String getFavoriteDictionary() {
    return PlayerProfileDatabase.getFavoriteDictionary(name);
  }

  /**
   * Adds the achieved points to the current points of the user "name" in the database.
   *
   * @param points the user achieved in the played game
   * @author lengist
   */
  public void addPoints(int points) {
    int currentPoints = PlayerProfileDatabase.getTotalPoints(name);
    int newPoints = currentPoints + points;
    // System.out.println(name + " " + newPoints);
    PlayerProfileDatabase.setTotalPoints(name, newPoints);
  }
}
