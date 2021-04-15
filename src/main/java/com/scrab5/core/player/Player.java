package com.scrab5.core.player;

import com.scrab5.core.game.Rack;

public class Player {

  public Rack getRack() {
    return rack;
  }

  public void setRack(Rack rack) {
    this.rack = rack;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isHuman() {
    return isHuman;
  }

  public void setHuman(boolean human) {
    isHuman = human;
  }

  private Rack rack;
  private String name;
  private boolean isHuman;
  private PlayerProfile playerProfile;

  public Player(String name){
    this.name= name;
    this.rack= new Rack();
  }



}
