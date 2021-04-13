package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Random;

public class BagOfTiles {
  private ArrayList<Tile> bag = new ArrayList<>();

  /**
   * @author trohwede
   * @return
   */
  public BagOfTiles() {}


  /**
   * @author trohwede
   * @return
   */
  public void add(Tile t) {
    this.bag.add(t);
  }


  /**
   * @author trohwede
   * @return
   */
  public Tile pick() {
    // picks last Tile in Bag of Tiles, returns it and removes it from bag
    if (bag.isEmpty()) {
      return null;
    }

    return bag.remove(new Random().nextInt(bag.size()));
  }


  /**
   * @author trohwede
   * @return
   */
  public int getSize() {
    // returns how many Tiles are left in the Bag
    return this.bag.size();
  }
}
