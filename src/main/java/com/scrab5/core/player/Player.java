package com.scrab5.core.player;

import com.scrab5.core.game.Rack;
import java.io.Serializable;

/**
 * Class Player, represents a player in this game.
 *
 * @author trohwede
 */
public class Player implements Serializable {

  private static final long serialVersionUID = 1L;
  private final String name;
  private Rack rack;
  private PlayerProfile playerProfile;
  private int points;
  private int correctWords = 0;
  private boolean givenUp = false;

  /**
   * Constructor for Player. Sets name, creates new Rack, sets points to 0, and creates a new
   * playerProfile.
   *
   * @author trohwede
   * @param name When Player is created gives the player a name and a personal rack
   */
  public Player(String name) {
    this.name = name;
    this.rack = new Rack();
    this.points = 0;
    this.playerProfile = new PlayerProfile();
  }

  /**
   * Getter for the playerProfile.
   *
   * @author trohwede
   * @return returns the playerProfile
   */
  public PlayerProfile getPlayerProfile() {
    return playerProfile;
  }

  /**
   * Returns the rack from the player.
   *
   * @author trohwede
   * @return returns rack of player.
   */
  public Rack getRack() {
    return rack;
  }

  /**
   * Getter for the name of the player.
   *
   * @author trohwede
   * @return returns name of player
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for points.
   *
   * @author trohwede
   * @return points the player has.
   */
  public int getPoints() {
    return points;
  }

  /**
   * Setter for points.
   *
   * @author trohwede
   * @param points amount of points you want to set.
   */
  public void setPoints(int points) {
    this.points = points;
  }

  /**
   * Getter for the correct placed words.
   *
   * @author apilgrim
   * @return correctWords - the player place during the game.
   */
  public int getCorrectWords() {
    return correctWords;
  }

  /**
   * Setter for the correct placed words.
   *
   * @author apilgrim
   * @param correctWords - the correct placed words to set
   */
  public void setCorrectWords(int correctWords) {
    this.correctWords = correctWords;
  }

  /**
   * Getter for the given up attribute to see if this player has given up.
   *
   * @author apilgrim
   * @return if player has given up
   */
  public boolean isGivenUp() {
    return givenUp;
  }

  /**
   * Setter for the given up attribute.
   *
   * @author apilgrim
   * @param givenUp - the correct placed words to set
   */
  public void setGivenUp(boolean givenUp) {
    this.givenUp = givenUp;
  }
}
