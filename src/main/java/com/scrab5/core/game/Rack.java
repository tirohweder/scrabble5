package com.scrab5.core.game;

public class Rack {

  private Tile[] rack = new Tile[7];

  public Rack() {

  }


  /**
   * @param bag
   * @author trohwede
   */
  public void fill(BagOfTiles bag) {
    for (int i = 0; i < 7; i++) {
      if (this.rack[i] == null && bag.getSize() >= 1) {

        rack[i] = bag.pick();
      }
    }
  }

  /**
   * @return
   * @author trohwede
   */
  public boolean rackFull() {
    for (int i = 0; i < 7; i++) {
      if (this.rack[i] == null) {
        return false;
      }
    }
    return true;
  }


  /**
   * @param t
   * @param pos
   * @author trohwede
   */
  public void addToRack(Tile t, int pos) {
    this.rack[pos] = t;
  }


  /**
   * @param pos
   * @author trohwede
   */
  public boolean removeTileFromRack(int pos) {
    if (this.rack[pos] != null) {
      this.rack[pos] = null;
      return true;
    } else {
      return false;
    }

  }

  /**
   * @author trohwede
   */
  public void resetRack() {
    for (int i = 6; i >= 0; i--) {
      this.rack[i] = null;
    }
  }
}
