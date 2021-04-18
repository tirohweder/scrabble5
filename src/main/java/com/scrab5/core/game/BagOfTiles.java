package com.scrab5.core.game;

import java.util.ArrayList;
import java.util.Random;

public class BagOfTiles {

  private ArrayList<Tile> bag = new ArrayList<>();

  /**
   * Empty Constructor
   *
   * @return
   * @author trohwede
   */
  public BagOfTiles() {
  }


  /**
   * adds tile to bag
   *
   * @param t
   * @author trohwede
   */
  public void add(Tile t) {
    this.bag.add(t);
  }


  /**
   * @return
   * @author trohwede
   */
  public Tile pick() {
    // picks last Tile in Bag of Tiles, returns it and removes it from bag
    if (bag.isEmpty()) {
      return null;
    }

    return bag.remove(new Random().nextInt(bag.size()));
  }


  /**
   * @return
   * @author trohwede
   */
  public int getSize() {
    // returns how many Tiles are left in the Bag
    return this.bag.size();
  }
}
