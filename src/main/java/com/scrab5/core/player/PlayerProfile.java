package com.scrab5.core.player;

import com.scrab5.ui.Data;
import com.scrab5.util.database.PlayerProfileDatabase;
import java.io.Serializable;

/**
 * In the PlayerProfile the information for the statistics is saved and made accessible.
 *
 * @author lengist
 * @author trohwede
 */
public class PlayerProfile implements Serializable {

  private static final long serialVersionUID = 1L;

  private static final String name = Data.getCurrentUser();

  /**
   * Returns the name of the current user.
   *
   * @author lengist
   * @return name String representation of the name of the current user
   */
  public static String getName() {
    return name;
  }

  /**
   * Adds the achieved points to the current points of the user "name" in the database.
   *
   * @author lengist
   * @param points the user achieved in the played game
   */
  public void addPoints(String userName, int points) {
    int current = PlayerProfileDatabase.getTotalPoints(name);
    PlayerProfileDatabase.setTotalPoints(userName, points + current);
  }

  /**
   * Changes the personal high score of the player, if the new high score is greater than the saved
   * one.
   *
   * @author lengist
   * @param lastPersonalHighscore the user achieved in the previous played game
   */
  public void adjustPersonalHighscore(int lastPersonalHighscore) {
    PlayerProfileDatabase.setPersonalHighscore(name, lastPersonalHighscore);
  }

  /**
   * Adds the laid words to the column laidWords of the user "name" in the database.
   *
   * @author lengist
   * @param words the amount of words the user laid in the played game
   */
  public void addWords(int words) {
    int currentWords = PlayerProfileDatabase.getLaidWords(name);
    PlayerProfileDatabase.setLaidWords(name, currentWords + words);
  }

  /**
   * Changes the points per word rate. If the new rate is greater than the one saved in the
   * database.
   *
   * @author lengist
   * @param newPointsPerWordRate the user achieved in the played game
   */
  public void adjustPointsPerWordRate(int newPointsPerWordRate) {
    int currentPointsPerWordRate = PlayerProfileDatabase.getPointsPerWordRate(name);
    if (newPointsPerWordRate > currentPointsPerWordRate) {
      PlayerProfileDatabase.setPointsPerWordRate(name, newPointsPerWordRate);
    }
  }

  /**
   * Adds the quantity of played games of the user "name" to the database.
   *
   * @author lengist
   * @param games the amount of games the user played since the last lock
   */
  public void addGames(int games) {
    int currentGames = PlayerProfileDatabase.getTotalPlayedGames(name);
    int newGames = currentGames + games;
    PlayerProfileDatabase.setTotalPlayedGames(name, newGames);
  }

  /**
   * Adds the achieved wins of the user "name" to the database.
   *
   * @author lengist
   * @param wins the amount of wins the user achieved since the last lock
   */
  public void addWins(int wins) {
    int currentWins = PlayerProfileDatabase.getTotalWins(name);
    int newWins = currentWins + wins;
    PlayerProfileDatabase.setTotalWins(name, newWins);
  }

  /**
   * Adjusts the win rate stored for the player.
   *
   * @author lengist
   * @param winRate the user achieved after the played game
   */
  public void adjustWinRate(double winRate) {
    PlayerProfileDatabase.setWinRate(name, winRate);
  }

  /**
   * Returns the total points saved in the database for player "name".
   *
   * @author lengist
   * @return int the total points saved in the database for the user name
   */
  public int getTotalPoints() {
    return PlayerProfileDatabase.getTotalPoints(name);
  }

  /**
   * Returns the personal high score in the database for player name.
   *
   * @author lengist
   * @return int the saved personal high score from table Player
   */
  public int getPersonalHighscore() {
    return PlayerProfileDatabase.getPersonalHighscore(name);
  }

  /**
   * Returns the number of laid words saved in the database for the current player profile.
   *
   * @author lengist
   * @return int the laid words saved in the database for the user name
   */
  public int getLaidWords() {
    return PlayerProfileDatabase.getLaidWords(name);
  }

  /**
   * Returns the points per word rate in the database for player name.
   *
   * @author lengist
   * @return int the saved points per word rate from table Player
   */
  public int getPointsPerWordRate() {
    return PlayerProfileDatabase.getPointsPerWordRate(name);
  }

  /**
   * Returns the total played games saved in the database for player "name".
   *
   * @author lengist
   * @return int the total played games saved in the database for the user name
   */
  public int getTotalPlayedGames() {
    return PlayerProfileDatabase.getTotalPlayedGames(name);
  }

  /**
   * Returns the number of total wins saved in the database for player "name".
   *
   * @author lengist
   * @return int the total wins saved in the database for the user name
   */
  public int getTotalWins() {
    return PlayerProfileDatabase.getTotalWins(name);
  }

  /**
   * Returns the win rate in the database for the current player name.
   *
   * @author lengist
   * @return double the saved win rate from table Player
   */
  public double getWinRate() {
    return PlayerProfileDatabase.getWinRate(name);
  }
}
