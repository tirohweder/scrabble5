package com.scrab5.core.game;

public class Bag_of_Tiles {
  private ArrayList<Tile> tiles = new ArrayList<Tile>();
  
  //Constructor
  public Bag_of_Tiles() {
    
  }
  
  //Methods
  public void add(Tile t) {
    this.tiles.add(t);
  }
  
  public void remove(Tile t) {
    this.tiles.remove(t);
  }
  
  public void setTiles(ArrayList<Tile> tiles) {
    this.tiles = tiles;
  }
  
  public ArrayList<Tile> getTiles(){
    return this.tiles;
  }
  
  public int getSize() {
    return this.tiles.size();
  }
  
  public Card get(int index) {
    return this.tiles.get(index);
  }
  
}