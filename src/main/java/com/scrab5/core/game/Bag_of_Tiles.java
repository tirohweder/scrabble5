package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Random;

public class Bag_of_Tiles {
  private ArrayList<Tile> bag = new ArrayList<>();

  /*
  Tiles need to be saved l
  */

  // Constructor
  public Bag_of_Tiles() {}

  // Methods
  public void add(Tile t) {
    this.bag.add(t);
  }

  public Tile pick() {
    // picks last Tile in Bag of Tiles, returns it and removes it from bag
    if (bag.isEmpty()) {
      return null;
    }

    return bag.remove(new Random().nextInt(bag.size()));
  }

  public int get_Size() {
    // returns how many Tiles are left in the Bag
    return this.bag.size();
  }
}
