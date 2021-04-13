package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Random;

public class BagOfTiles {
  private ArrayList<Tile> bag = new ArrayList<>();

  /*
  Tiles need to be saved l
  */

  // Constructor
  public BagOfTiles() {}

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

  /**
   * @author trohwede
   * @return bag Size
   */
  public int getSize() {
    // returns how many Tiles are left in the Bag
    return this.bag.size();
  }
}
