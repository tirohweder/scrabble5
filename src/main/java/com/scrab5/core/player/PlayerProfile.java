package com.scrab5.core.player;

import java.io.IOException;
import com.scrab5.ui.Data;
import com.scrab5.util.database.PlayerProfileDatabase;

/**
 * @author trohwede
 */
public class PlayerProfile {

  private static String name = Data.getCurrentUser();
  private static String picture;
  /*
   * private static int totalPoints; private static int laidWords; private static int
   * totalPlayedGames; private static int totalWins; private static String favoriteDictionary;
   */

  /**
   * Returns he name of the current user.
   * 
   * @author lengist
   * @return String
   */
  public static String getName() {
    return name;
  }

  /**
   * Return the picture of the current user if added.
   * 
   * @author lengist
   * @return String
   */
  public static String getPicture() {
    picture = PlayerProfileDatabase.getPicture(name);
    return picture;
  }

  /**
   * Adds the achieved points to the current points of the user "name" in the database.
   * 
   * @author lengist
   * @param points
   */
  public static void addPoints(int points) throws IOException {
    int currentPoints = PlayerProfileDatabase.getTotalPoints(name);
    int newPoints = currentPoints + points;
    PlayerProfileDatabase.setTotalPoints(name, newPoints);
    // totalPoints = newPoints;
  }

  /**
   * Adds the laid words to the column laidWords of the user "name" in the database.
   * 
   * @author lengist
   * @param words
   */
  public static void addWords(int words) throws IOException {
    int currentWords = PlayerProfileDatabase.getLaidWords(name);
    int newWords = currentWords + words;
    PlayerProfileDatabase.setLaidWords(name, newWords);
    // laidWords = newWords;
  }

  /**
   * Adds the quantity of played games of the user "name" to the database.
   * 
   * @author lengist
   * @param games
   */
  public static void addGames(int games) throws IOException {
    int currentGames = PlayerProfileDatabase.getTotalPlayedGames(name);
    int newGames = currentGames + games;
    PlayerProfileDatabase.setTotalPlayedGames(name, newGames);
    // totalPlayedGames = newGames;
  }

  /**
   * Adds the achieved wins of the user "name" to the database.
   * 
   * @author lengist
   * @param wins
   */
  public static void addWins(int wins) throws IOException {
    int currentWins = PlayerProfileDatabase.getTotalWins(name);
    int newWins = currentWins + wins;
    PlayerProfileDatabase.setTotalWins(name, newWins);
    // totalWins = newWins;
  }

  /**
   * Changes the favorite dictionary of the user "name" in the database.
   * 
   * @author lengist
   * @param dictionary
   */
  public static void changeDictionary(String dictionary) throws IOException {
    PlayerProfileDatabase.setFavoriteDictionary(name, dictionary);
    // favoriteDictionary = dictionary;
  }

  /**
   * Returns the total points saved in the database for player "name".
   * 
   * @author lengist
   * @return int
   */
  public static int getTotalPoints() {
    return PlayerProfileDatabase.getTotalPoints(name);
  }

  /**
   * Returns the number of laid words saved in the database for player "name".
   * 
   * @author lengist
   * @return int
   */
  public static int getLaidWords() {
    return PlayerProfileDatabase.getLaidWords(name);
  }

  /**
   * Returns the total played games saved in the database for player "name".
   * 
   * @author lengist
   * @return int
   */
  public static int getTotalPlayedGames() {
    return PlayerProfileDatabase.getTotalPlayedGames(name);
  }

  /**
   * Returns the number of total wins saved in the database for player "name".
   * 
   * @author lengist
   * @return int
   */
  public static int getTotalWins() {
    return PlayerProfileDatabase.getTotalWins(name);
  }

  /**
   * Returns the favorite dictionary saved in the database for player "name".
   * 
   * @author lengist
   * @return String
   */
  public static String getFavoriteDictionary() {
    return PlayerProfileDatabase.getFavoriteDictionary(name);
  }
}
