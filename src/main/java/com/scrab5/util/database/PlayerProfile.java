package com.scrab5.util.database;

import java.sql.SQLException;
import java.sql.Statement;

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

  /* setter methods with statements for database */
  public void setTotalPoints() {
    try (Statement stm = connection.createStatement()) {
      String sql = "";
      stm.executeUpdate(sql); /* is it execute update? */
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setLaidWords() {
    try (Statement stm = connection.createStatement()) {
      String sql = "";
      stm.executeUpdate(sql); /* is it execute update? */
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setTotalPlayedGames() {
    try (Statement stm = connection.createStatement()) {
      String sql = "";
      stm.executeUpdate(sql); /* is it execute update? */
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setTotalWins() {
    try (Statement stm = connection.createStatement()) {
      String sql = "";
      stm.executeUpdate(sql); /* is it execute update? */
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setFavoriteDictionary() {
    try (Statement stm = connection.createStatement()) {
      String sql = "";
      stm.executeUpdate(sql); /* is it execute update? */
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
