package com.scrab5.core.player;

import com.scrab5.core.game.Rack;
import java.io.Serializable;

public class Player implements Serializable {

  private static final long serialVersionUID = 1L;
  private Rack rack;
  private String name;
  private boolean isHuman;
  private PlayerProfile playerProfile;
  private int points;

  /**
   * @param name When Player is created gives the player a name and a personal rack
   * @author trohwede
   */
  public Player(String name, boolean human) {
    this.name = name;
    this.rack = new Rack();
    this.points = 0;
    this.isHuman = human;
    this.playerProfile = new PlayerProfile();
  }

  /**
   * @param name When Player is created gives the player a name and a personal rack
   * @author trohwede
   */
  public Player(String name) {
    this.name = name;
    this.rack = new Rack();
    this.points = 0;
    this.playerProfile = new PlayerProfile();
  }

  public PlayerProfile getPlayerProfile() {
    return playerProfile;
  }

  // TODO remove

  public void setPlayerProfile(PlayerProfile playerProfile) {
    this.playerProfile = playerProfile;
  }

  /**
   * @author trohwede
   * @return Gets Rack
   */
  public Rack getRack() {
    return rack;
  }

  /**
   * @author trohwede
   * @param rack setsRack for person
   */
  public void setRack(Rack rack) {
    this.rack = rack;
  }

  /**
   * @author trohwede
   * @return Gets Name of Player
   */
  public String getName() {
    return name;
  }

  /**
   * @author trohwede
   * @param name Sets name of Player
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @author trohwede
   * @return Returns if human is or not
   */
  public boolean isHuman() {
    return isHuman;
  }

  /**
   * @author trohwede
   * @param human Set Player to be Human or AI
   */
  public void setHuman(boolean human) {
    isHuman = human;
  }

  /**
   * @author trohwede
   * @return
   */
  public int getPoints() {
    return points;
  }

  /**
   * Setter for the
   *
   * @author trohwede
   * @param points
   */
  public void setPoints(int points) {
    this.points = points;
  }
}
