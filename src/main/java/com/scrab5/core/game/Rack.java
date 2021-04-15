package com.scrab5.core.game;

import java.util.ArrayList;

public class Rack {

  private ArrayList<Tile> rack = new ArrayList<>();

  public Rack() {

  }



  /* if Rack is not full add more tiles from bag till full
   but only add if the bag is not empty.
  */
  public void fill(BagOfTiles bag) {
    for (int i = 0; i < 7; i++) {
      if (rack.size() < 7 && bag.getSize() >= 1) {
        rack.add(bag.pick());
      }
    }
  }

  // add tile to rack
  public void add_to_rack(Tile t) {
    this.rack.add(t);
  }

  // removes tile from rack
  public void remove_tile_from_rack(Tile t) {
    this.rack.remove(t);
  }

  // overload remove_tile_from_rack if you want to remove by index
  public void remove_tile_from_rack(int index) {
    this.rack.remove(index);
  }

  public int get_rack_size() {
    return this.rack.size();
  }

  // removes all Tiles from the Rack
  public void reset_rack() {
    for (int i = 6; i >= 0; i--) {
      rack.remove(i);
    }
  }
}
