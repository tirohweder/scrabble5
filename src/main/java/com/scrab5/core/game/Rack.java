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
  public void add_to_rack(Tile t, int pos) {
    this.rack[pos] = t;
  }


  /**
   * @param t
   * @param pos
   * @author trohwede
   */
  public void remove_tile_from_rack(Tile t, int pos) {
    this.rack[pos] = null;
  }

  /**
   * @author trohwede
   */
  public void reset_rack() {
    for (int i = 6; i >= 0; i--) {
      this.rack[i] = null;
    }
  }
}
