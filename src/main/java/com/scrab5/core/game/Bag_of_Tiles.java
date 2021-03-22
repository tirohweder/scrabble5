package com.scrab5.core.game;

import java.util.ArrayList;

public class Bag_of_Tiles {
  private ArrayList<Tile> tiles = new ArrayList<>();

  // Constructor
  public Bag_of_Tiles() {}

  // Methods
  public void add(Tile t) {
    this.tiles.add(t);
  }

  public void remove(Tile t) {
    this.tiles.remove(t);
  }

  public ArrayList<Tile> getTiles() {
    return this.tiles;
  }

  public void setTiles(ArrayList<Tile> tiles) {
    this.tiles = tiles;
  }

  public int getSize() {
    return this.tiles.size();
  }

  public Tile get(int index) {
    return this.tiles.get(index);
  }
}
