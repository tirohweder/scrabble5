package com.scrab5.core.player;

import com.scrab5.core.game.Rack;
import java.io.Serializable;

public class Player implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * @return Gets Rack
   * @author trohwede
   */
  public Rack getRack() {
    return rack;
  }

  /**
   * @param rack setsRack for person
   * @author trohwede
   */
  public void setRack(Rack rack) {
    this.rack = rack;
  }

  /**
   * @return Gets Name of Player
   * @author trohwede
   */
  public String getName() {
    return name;
  }

  /**
   * @param name Sets name of Player
   * @author trohwede
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns if human is or not
   * @author trohwede
   */
  public boolean isHuman() {
    return isHuman;
  }

  /**
   * @param human Set Player to be Human or AI
   * @author trohwede
   */
  public void setHuman(boolean human) {
    isHuman = human;
  }

  private Rack rack;
  private String name;
  private boolean isHuman;
  private PlayerProfile playerProfile;


  /**
   * @param name When Player is created gives the player a name and a personal rack
   * @author trohwede
   */
  public Player(String name) {
    this.name = name;
    this.rack = new Rack();
  }


}
